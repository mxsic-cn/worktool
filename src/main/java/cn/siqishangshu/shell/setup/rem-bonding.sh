#!/bin/bash

bonding_dir=/usr/share/cornerstone_setup/script

function usage_exit()
{
    echo "Error: $1"
    echo "Usage:"
    echo "    `basename $0` master [force]/[[nic addr netmask] [gateway]]"
    echo "Arguments:"
    echo "    master    - The bonding device name"
    echo "    nic       - Configure this nic"
    echo "    addr      - The ipv4 address assigned to nic"
    echo "    netmask   - The netmask assigned to nic"
    echo "    gateway   - The default gateway"
    echo "Example:"
    echo "    `basename $0` bond0"
    echo "    `basename $0` bond0 force"
    echo "    `basename $0` bond0 agl0 192.168.110.119 255.255.255.0"
    echo "    `basename $0` bond0 agl0 192.168.110.118 255.255.255.0 192.168.110.254"
    exit -1
}

. /usr/share/cornerstone_setup/script/bonding-functions.sh

function verify_bonding()
{
    local stat=1
    local msg=""
    local prefix="    Checking bonding \`$1' "
    local err=""

    if [ -z "$1" ];then
        msg="Null"
    else
        if master_exists "$1";then
            msg="Ok"
            stat=0 
        else
            msg="Failed"
            err="Error: no such device or is not a bonding device."
        fi
    fi

    format_output "$prefix" "$msg"
    if [ -n "$err" ];then
        echo "$err"
    fi
    return $stat
}

function open_remove_script()
{
    open_file "/tmp/bonding/_remove.sh"
}

function write_remove_script()
{
    FILE="/tmp/bonding/_remove.sh"
    write_file "$1" $FILE
}

function close_remove_script()
{
    chmod +x "/tmp/bonding/_remove.sh"
}

function gen_remove_script()
{
    MASTER="$1"
    if [ $# -eq 2 ];then
        FORCE="$2"
    else
        NIC="$2"
    fi
    IPADDR="$3"
    NETMASK="$4"
    GATEWAY="$5"
    SLAVES=($(get_slaves $MASTER))

    open_remove_script 
    write_remove_script "##"
    write_remove_script "## -- `date` --"
    write_remove_script "## This script is automatically generated, please do not modify it."
    write_remove_script "#"
    write_remove_script ". ${bonding_dir}/bonding-functions.sh"
    write_remove_script "led_write \"Removing\ \<$MASTER\>\""
    write_remove_script "remove_master $MASTER"

    if [ -n $SLAVES ];then
        for slave in ${SLAVES[@]};do
            write_remove_script "if_up_sync $slave"
        done
    fi

    if [ -n "$NIC" ];then
        write_remove_script "# Configure \`$NIC'"
        write_remove_script "if_up_sync $NIC $IPADDR $NETMASK 10"
    else
        if [ -z $FORCE ];then
            write_remove_script "# No target nic be specified, select active/primary slave"
            NIC=$(select_nic_available $MASTER)
            if [ -n "$NIC" ];then
                IPADDR=`get_ipv4_address $MASTER`
                NETMASK=`get_ipv4_netmask $MASTER`
                GATEWAY=`get_gateway $MASTER`
                # SLAVES=`get_slaves $MASTER`
                if [ -n "$IPADDR" ];then
                    write_remove_script "if_up_sync $NIC $IPADDR $NETMASK 10"
                else
                    write_remove_script "if_up_sync $NIC 0 0 10"
                fi
            else
                write_remove_script "# No slave nic available, ignore"
            fi
        fi
    fi

    if [ -n "$GATEWAY" ];then
        write_remove_script "## Build route table"
        write_remove_script "led_write \"Update\ gateway\""
        write_remove_script "$IPUTILS route delete default > /dev/null 2>&1"
        write_remove_script "$IPUTILS route add default via $GATEWAY dev $NIC"
        write_remove_script "check_peer $GATEWAY 60"
    fi

    write_remove_script "led_write \"\<$MASTER\>\ removed\""
    write_remove_script "sleep 3 && led_write \"$(mgnt_dev_addr)\""

    local bond_nr=`cat $SYSDIR/bonding_masters`
    if [ -z $bond_nr ];then
        write_remove_script "# All bond dev has been removed, rmmod bonding."
        write_remove_script "rmmod bonding"
    fi

    close_remove_script
    return $?
}

LOGFILE="$LOGDIR/rem-bonding.log"
TIMESTAMP=`date`

# -- Script start --
if [ $# -ne 1 -a $# -ne 2 -a $# -ne 4 -a $# -ne 5 ];then
    usage_exit "Invalid parameters"
fi

exec &>${LOGFILE}

MASTER="$1"
if [ $# -eq 2 ];then
    FORCE="$2"
else
    NIC="$2"
fi

echo "CAPABILITIES:"
verify_capabilities || exit $?

echo "BONDING DEVICE:"
verify_bonding $MASTER || exit $?

echo "ENVIRONMENT TOOLS:"
verify_tools $NETTOOL  || exit $?
verify_tools $DHCLIENT || exit $?
verify_tools $IPUTILS  || exit $?
verify_tools $PING     || exit $?

if [ -n "$NIC" ];then
    echo "Specified target NIC: \'$NIC'"
    echo "NET DEVICES:"
    verify_nic $NIC || exit $?
    verify_slave $NIC $MASTER || exit $?

    echo "NETWORK PARAMETERS:"
    IPADDR=$3
    NETMASK=$4
    verify_ip $IPADDR  "address" || exit $?
    verify_ip $NETMASK "netmask" || exit $?

    if [ -n "$5" ];then
        GATEWAY=$5
        verify_ip $GATEWAY "gateway" || exit $?
    fi
fi

echo "Generate \`$RESCRIPT' at Fri Jul 22 06:19:26 UTC 2016"
gen_remove_script "$@"
exit $?

###echo "================== REM START ==================="
###echo "date    : $TIMESTAMP"
###echo -n "master  : $MASTER"
###if [ -n $GATEWAY ];then
###    echo " [$IPADDR/$NETMASK via $GATEWAY]"
###else
###    echo " [$IPADDR/$NETMASK, Direct link]"
###fi
###echo "slaves  : $SLAVES"
###echo "primary : $PRIMARY"
###echo "active  : $ACTIVE"
###echo "------------------------------------------------"
###
###led_write "Removing\ [$MASTER]..."
#### Remove bonding
###unset_primary $MASTER $PRIMARY
###remove_slaves $MASTER $SLAVES
###remove_master $MASTER
###
###if [ $# = 4 -o $# = 5 ];then
###    ACTIVE=$NIC
###fi
###
#### Add default route entry
###if [ -n "$ACTIVE" -a -n "$IPADDR" -a -n "$NETMASK" ];then
###    ${NETTOOL} $ACTIVE $IPADDR netmask $NETMASK up || exit $?
###    if [ -n "$GATEWAY" ];then
###	$IPUTILS route add default via $GATEWAY dev $ACTIVE || exit $?
###    fi
###fi
###
###echo "------------------------------------------------"
###/sbin/route -n
###echo "------------------------------------------------"
###
###msg="Recovering..."
###count=1
###if [ -n "$GATEWAY" ];then
###	while ! $PING $GATEWAY -c 1 -W 1 > /dev/null;do
###		led_write "${msg}\ ${count}s"
###                let count++
###        done	
###	led_write "[${MASTER}]\ removed"
###        sleep 1
###	led_write `mgnt_dev_addr`
###fi
###
###echo "Elapsed: ${count}s"
###echo "================== REM END ===================="
#### -- Script finished --
