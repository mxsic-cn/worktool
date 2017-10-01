#!/bin/bash

SYSDIR="/sys/class/net"
PROCDIR="/proc/net/bonding"
LOGDIR="/var/log/"
NETTOOL="/sbin/ifconfig"
DHCLIENT="/sbin/dhclient"
IPUTILS="/bin/ip"
PING="/bin/ping"
ETHTOOL="/sbin/ethtool"
LEDTOOL1="/usr/share/server/led_status.sh"
LEDTOOL2="/usr/share/server/led_write.sh"
LEDDEV="ttyS0"
MGNT_DEV="agl0"
ROLLDIR="/var/run/bonding"
ROLLFILE="$ROLLDIR/rollback.sh"
INSCRIPT="/tmp/bonding/_install.sh"
RESCRIPT="/tmp/bonding/_remove.sh"

function padding_dot()  # _hidden
{
     local delta=$1

     if [ -z "$delta" ];then
         return
     fi
 
     while [ $delta -ne 0 ];do
	echo -n "."
	let delta--
     done
}

function port_type()  # Get nic phy type, TP or FIBER
{
    if [ ! -z "$1" ];then
        echo -n `$ETHTOOL $1|grep "Supported ports"|cut -d' ' -f4`
    fi
}

function valid_ip()  # Check ipv4 address whether is dot-notation
{
    local  ip=$1
    local  stat=1

    if [[ $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
        OIFS=$IFS
        IFS='.'
        ip=($ip)
        IFS=$OIFS
        [[ ${ip[0]} -le 255 && ${ip[1]} -le 255 \
            && ${ip[2]} -le 255 && ${ip[3]} -le 255 ]]
        stat=$?
    fi
    return $stat
}

function loopback_dev()  # The nic is loopback interface ?
{
    if [ -z "$1" ];then
        return 1 
    fi

    $NETTOOL $1 2>/dev/null|grep LOOPBACK >/dev/null 2>&1
    return $?
}

function format_output()  # _hidden
{
    local prefix="$1"
    local msg="$2"
    local len=`echo -n $prefix|wc -L`
    let local delta=70-len
    local msglen=`echo -n $msg|wc -L`
    let delta=delta-$msglen

    printf "%s" "$prefix"
    padding_dot $delta
    echo " $msg"
}

function verify_ip()  # _hidden
{
	local stat=1
        local msg=""
        local fmt="$2"
        if [ ! -z $fmt ];then
            fmt="$fmt "
        fi
        local prefix="    Checking $fmt\`$1' "

	if valid_ip $1; then
                msg="Ok"
		stat=0
        else
                msg="Failed"
		local err="Error: Invalid format."
	fi

        format_output "$prefix" "$msg"

        if [ -n "$err" ];then
            echo "$err"
        fi
        return $stat
}

function verify_tools()  # _hidden
{
    local stat=1
    local msg="Ok"
    local prefix="    Checking tool \`$(basename $1)' "

    if [ -z "$1" ];then
        msg="Null"
    elif [ ! -f "$1" ];then
	msg="Failed"
	local err="Error: No such command."
    else
        stat=0
    fi 

    format_output "$prefix" "$msg"
    if [ -n "$err" ];then
        echo "$err"
    fi
    return $stat
}

function master_exists()  # The master device(bonding) exist
{
    local stat=1
    if [ -f "$PROCDIR/$1" ];then
	stat=0
    fi
    return $stat
}

function if_down_sync()  # Execute ifconfig down and waiting until timeout
{
    local interval="1"
    if [ $# = 1 ];then
        timeout=9999
    elif [ $# = 2 ];then
        timeout=$2
    else
        return 1
    fi

    $NETTOOL $1 0.0.0.0 down >/dev/null 2>&1
    while $NETTOOL $1 2>&1|grep "UP" > /dev/null;do
	echo "Waiting for $1 DOWN..."
	sleep $interval
        let timeout--
        if [ $timeout -eq "0" ];then
            break
        fi 
    done

    # echo "$1 is down"
    return 0
}

function if_up_sync()  # Execute ifconfig down and waiting until timeout
{
    local interval="1"
    timeout=9999
    if [ $# = 1 ];then
        $NETTOOL $1 up >/dev/null 2>&1
    elif [ $# = 2 ];then
        $NETTOOL $1 up >/dev/null 2>&1
        timeout=$2
    elif [ $# = 3 ];then
        $NETTOOL $1 $2 netmask $3 up >/dev/null 2>&1
    elif [ $# = 4 ];then
        $NETTOOL $1 $2 netmask $3 up >/dev/null 2>&1
        timeout=$4
    else
        return 1
    fi

    while ! $NETTOOL $1 2>&1|grep "UP" > /dev/null 2>&1;do
        echo "Waiting for $1 up - $timeout"
        sleep $interval
        let timeout--
        if [ $timeout -eq "0" ];then
            break
        fi
    done

    # echo "$1 is up"
    return 0
}

function add_master()  # Add master device(bonding)
{
    if [ -z "$1" ];then
        echo "add_master: No parameters" 
        exit -1
    fi  
 
    echo +$1 > $SYSDIR/bonding_masters || exit $?

    if_down_sync $1 "5"

	# set default value
    echo 1 > ${SYSDIR}/$1/bonding/mode
    echo 100 > ${SYSDIR}/$1/bonding/miimon       # link monitor interval: 100ms
	echo 10000 > ${SYSDIR}/$1/bonding/updelay    # delay 1s before active

    if_up_sync $1 $2 $3 "5"
}

function enslave()  # Enslave a nic to bonding
{
    if_down_sync $2 "5"
    echo +$2 > ${SYSDIR}/$1/bonding/slaves || exit $?
    if_up_sync $2 "5"
}

function set_primary()  # Set nic to primary nic of bonding
{
    echo $2 > ${SYSDIR}/$1/bonding/primary || exit $?
}

function bonding_get_option()  # Get bonding options by name
{
	if [ $# -ne "2" ];then
		return
	fi
	
	echo -n `cat ${SYSDIR}/$1/bonding/$2`
}

function get_slaves()  # Get slaves of the bonding device
{
	echo -n `bonding_get_option $1 "slaves"`
}

function get_primary()  # Get primary nic of the bonding device
{
	echo -n `bonding_get_option $1 "primary"`
}

function get_active_slave()  # Get current active nic of the bonding device
{
	echo -n `bonding_get_option $1 "active_slave"`
}

function bonding_mode()  # Get bonding mode
{
	echo -n `bonding_get_option $1 "mode"|cut -d' ' -f1`
}

function unset_primary()  # Unset primary nic for the bonding device
{
	echo "-$2" > "$SYSDIR/$1/bonding/primary"
}

function remove_slaves()  # Remove nic from the bonding device
{
	local slaves=($2)
	for slave in ${slaves[@]};do
		# ${NETTOOL} $slave down
		echo -$slave > ${SYSDIR}/$1/bonding/slaves
	done
}

function remove_master()  # Remove the bonding device
{
	${NETTOOL} $1 down
	echo -$1 > ${SYSDIR}/bonding_masters
}

function get_ipv4_address()  # Get ipv4 address in device
{
	echo -n $($NETTOOL $1 2>/dev/null|awk '/inet addr:/ {print $2}'|sed 's/addr://')
}

function get_hwaddr()  # Get hardware address in device
{
	echo -n $(cat /sys/class/net/$1/address | sed 's/://g' | tr '[:lower:]' '[:upper:]' )
}

function get_ipv4_netmask()  # Get ipv4 netmask in device
{
	if ! loopback_dev "$1";then
	     echo -n `$NETTOOL $1 | awk -F: '/Mask/{print $4}'`
        else
	     echo -n `$NETTOOL $1 | awk -F: '/Mask/{print $3}'`
        fi
}

function get_system_gateway()  # Get system gateway
{
	echo -n `$IPUTILS route list|grep default|cut -d' ' -f3`
}

function get_gateway()  # Get gateway associated with the nic
{
	echo -n `$IPUTILS route list|grep default|grep $1|cut -d' ' -f3`
}

function get_default_gateway()  # Get gateway associated with the nic
{
	echo -n `$IPUTILS route list|grep default|awk '{print $3}{print $5}'`
}

function get_gateway_nic()  # Get nic associated with the gateway
{
	echo -n `$IPUTILS route list|grep default|cut -d' ' -f5`
}

function nic_is_bonding()  # The nic is a slave of a bonding device
{
    if [ -n "$1" -a -f "$PROCDIR/$1" ];then
        return 0
    else
        return 1
    fi     
}

function nic_is_loopback()  # The nic is a loopback device
{
    if [ -n "$1" ] && $NETTOOL $1 2>/dev/null|grep "LOOPBACK" >/dev/null 2>&1;then
         return 0
    else
         return 1
    fi
}

function null_bonding()  # If the bonding has no slave nic
{
     local str=`cat $SYSDIR/$1/bonding/slaves`
     local stat=1
     if [ -z "$str" ];then
	stat=0
     fi
     return $stat
}

function nic_in_bonding()  # If the nic has in the bonding device
{
    if [ -z "$1" -o $# != 2 ];then
        return 1
    fi

    cat $SYSDIR/$1/bonding/slaves 2>/dev/null|grep $2 >/dev/null
    return $?
}

function nic_enslaved()  # If the nic has been enslaved to a bonding device
{
    if [ -z "$1" ];then
        return 1
    fi

    $NETTOOL $1|grep "SLAVE" > /dev/null
    return $?
}

function nic_master()  # Get the master device of the nic
{
    if [ -z "$1" ];then
        return 
    fi
    
    if nic_enslaved $1;then
        echo -n $(basename `readlink $SYSDIR/$1/master`)
    fi
}

function bonding_dev()  # If the nic is a bonding device
{
    if [ -z "$1" ];then
        return 1
    fi

    $NETTOOL $1 2>&1|grep "MASTER" > /dev/null
    return $?
}

function mgnt_dev_name()  # Get managent nic name
{
    local ORIG_NIC=$MGNT_DEV
    local REAL_NIC=$ORIG_NIC
    if nic_enslaved $REAL_NIC;then
        REAL_NIC=$(basename `readlink $SYSDIR/$ORIG_NIC/master`)
    fi       
    echo -n $REAL_NIC
}

function mgnt_dev_addr()  # Get ipv4 address associated to managent nic
{
    local REAL_NIC=$(mgnt_dev_name)
    echo -n `get_ipv4_address $REAL_NIC` 
}

function mgnt_dev_netmask()  # Get ipv4 netmask associated to managent nic
{
    local ORIG_NIC=$MGNT_DEV
    local REAL_NIC=$ORIG_NIC

    if nic_enslaved $REAL_NIC;then
        REAL_NIC=$(basename `readlink $SYSDIR/$ORIG_NIC/master`)
    fi       
    echo -n `get_ipv4_netmask $REAL_NIC` 
}

function led_update()  # Write ipv4 address of mangent nic to the LED
{
    if [ -n $1];then
        $LEDTOOL1 $1 $LEDDEV        
    fi
}

function led_write()  # Write message to the LED
{
    if [ -n "$1" ];then
        $LEDTOOL2 $LEDDEV 1 "$1" 5 >/dev/null 2>&1
    else
        return 1
    fi
}

function nic_status()  # Get the nic status
{
    if [ -n "$1" ];then
        if [ ! -d $SYSDIR/$1 ];then
            echo -n "none"
	else
            if $NETTOOL $1 2>/dev/null|grep UP >/dev/null;then
                echo -n "up"
            else
                echo -n "down"
            fi
        fi
    fi
}

function list_all_nic_name()  # List all nic by name, include bonding
{
    echo -n `ls $SYSDIR|grep -v bonding_masters|xargs`
}

function list_all_nic() # List all nic verbose
{
    local vid="0"
    local bid="0"
    local lid="0"
    local nics=$(list_all_nic_name)
    for nic in ${nics[@]};do
        if [ -d "$SYSDIR/$nic/device" ];then
	    local busid=$(readlink $SYSDIR/$nic/device|cut -d'/' -f4)
            local slot=$(echo -n "$busid"|cut -d'.' -f1)
            if [ -f "$SYSDIR/$nic/device/label" ];then
                # local bus="$(cat $SYSDIR/$nic/device/label|cut -d'#' -f1)"
                local bus="Onboard Intel Ethernet controller"
                local id=$(cat $SYSDIR/$nic/device/label|cut -d'#' -f2)
            else
                local bus="PCI-e Intel Ethernet controller($slot)"
                local id=$(echo -n "$busid"|cut -d'.' -f2)
            fi
        else
            local slot=""
            if nic_is_bonding $nic;then
                local bus="Bonding Interface"
                local id=$bid
                let bid++
            elif nic_is_loopback $nic;then
                local bus="Loopback Interface"
                local id=$lid
                let lid++
            else
                local bus="Virtual"
                local id=$vid
                let vid++
            fi
        fi
        local addr=$(get_ipv4_address $nic)
        local mask=$(get_ipv4_netmask $nic)
        local stat=$(nic_status $nic)
        local link=0
        if [ $stat = "up" ];then
            local link=$(echo -n `cat $SYSDIR/$nic/carrier`)
        fi
        local type=$(port_type $nic)
        printf "%s|%s|%s|%s|%s|%s|%s|%s|%s\n" "$nic" "$addr" "$mask" "$stat" "$bus" "$slot" "$id" "$link" "$type"
    done    
}

function classified_nic()  # List all nic by classification
{
    echo "`list_all_nic|cat|awk -F'|' '{m[$5]=m[$5]""$1" "}END{for(i in m){print i"|"m[i]}}'`"
}

function list_all_bonding()  # List all bonding device
{
    echo -n $(cat $SYSDIR/bonding_masters|xargs -l)
}

function verify_nic()  # _hidden
{
    local stat=1
    local msg=""

    if [ -z "$1" ];then
        msg="Null"
    else
        local res=$(nic_status "$1")
        if [ $res = "none" ];then
            msg="Failed"
            local err="Error: no such device."
        else
            msg="Ok"
            stat=0
        fi
    fi

    local prefix="    Checking nic \`$1' "
    format_output "$prefix" "$msg"

    if [ -n "$err" ];then
        echo $err
    fi
    return $stat
}

function verify_slave()  # _hidden
{
    local stat=1
    local msg=""
    local mast=$(nic_master $1)

    if [ -n "$mast" -a "$mast" != "$2" ];then
        msg="Failed"
        local err="Error: the nic is in another bonding - \`$mast'."
    else
        msg="Ok"
        stat=0
    fi

    local prefix="    Checking slave \`$1' "
    format_output "$prefix" "$msg"

    if [ -n "$err" ];then
        echo $err
    fi
    return $stat
}

function verify_capabilities()  # _hidden
{
    local stat=1
    local msg=""
    local prefix="    Checking privilege "
    local user=$(whoami)

    if [ "$user" != "root" ];then
        msg="Failed"
        err="Error: Operation not permitted."
    else
        msg="Ok"
        stat=0
    fi

    format_output "$prefix" "$msg"

    if [ -n "$err" ];then
        echo "$err"
    fi
    return $stat
}

function check_peer()  # _hidden
{
    local peer="$1"
    local timeout=120
    local msg="Waiting\ link"
    local result=0
    local count=1

    if [ -n "$2" ];then
        timeout="$2"
    fi

    if [ -z "$peer" ];then
        result=1
    else
	    while ! $PING $peer -c 1 -W 1 > /dev/null;do
		led_write "${msg}\ ${count}s"
                let count++

		if [ "$count" -gt "$timeout" ];then
		    let result++
                    break
		fi
	    done
    fi
    return $result
}

function open_file() # _hidden
{
    if [ ! -z "$1" ];then
        mkdir -p `dirname $1`
        echo > "$1"
    fi
}

function write_file()  # _hidden
{
    if [ ! -z "$1" ];then
        if [ -n "$2" ];then
            echo -e "$1" >> "$2"
        else
            echo -e "$1" >> "/dev/tty"
        fi
    fi
}

function select_nic_available()  # _hidden
{
    local primary=`get_primary $1`
    local nic=""

    if [ -n "$primary" ];then
	nic="$primary"
    else
	local active=`get_active_slave $1`
	if [ -n "$active" ];then
	    nic="$active"
	fi
    fi
    echo -n "$nic"
}

function get_sn()
{
    nSN=''
    SN_HEX=`ethtool -e agl1 |grep 0x0ff0 |cut -d':' -f2`
    #SN_HEX=`ethtool -e agl1 offset 0x0ff0 | grep 0x0ff0 | awk '{for(i=2;i<=NF;++i)printf $i" "}'`
    for sn_hex in ${SN_HEX}
    do
       echo -n `echo -e "\x${sn_hex}"`
    done
}

if cat "$0" >/dev/null 2>&1;then
    cat "$0" | grep function |grep -v _hidden |grep -v cat
fi
