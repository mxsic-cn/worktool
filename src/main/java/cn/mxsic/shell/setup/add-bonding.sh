#!/bin/bash

bonding_dir=/usr/share/cornerstone_setup/script

function usage_exit()
{
    if [ -n "$1" ];then
        echo "Error: $1"
    fi

    echo "Usage:"
    echo "    `basename $0` mode master primary slave ip mask [gw]"
    echo "Arguments:"
    echo "    mode      - The bonding device mode: [balance-rr/active-backup]"
    echo "    master    - The bonding device name"
    echo "    primary   - The primary slave for master"
    echo "    slave     - The backup slave for master"
    echo "    ip        - The net address of numbers-and-dots notation"
    echo "    mask      - The netmask"
    echo "    gw        - The gateway"
    echo "Example:"
    echo "    `basename $0` bond0 balance-rr agl0 agl1 192.168.110.118 255.255.255.0 192.168.110.254"
    echo "    `basename $0` bond1 active-backup agl0 agl1 192.168.110.118 255.255.255.0"
    exit 1
}

. ${bonding_dir}/bonding-functions.sh

MODES=(
    balance-rr 
    active-backup
)

function verify_mode()
{
    local mode=$1
    local stat=1
    local msg=""
    local prefix="    Checking mode supported "

    for m in ${MODES[@]};do
        if [ $mode = $m ];then
            msg="Ok"
            stat=0
            break;
        fi
    done

    if [ $stat -eq 1 ];then
        msg="Failed"
        local err="Error: Don't support mode - \`$mode'"
    fi

    format_output "$prefix" "$msg"

    if [ -n "$err" ];then
        echo "$err"
    fi
    return $stat
}

function verify_module()
{
    local stat=1
    local msg=""
    local prefix="    Checking module "

    lsmod | grep bonding > /dev/null 2>&1

    local loaded=$?
    local init_bond="bond0"

    if modprobe bonding > /dev/null 2>&1;then
        msg="Ok"
        stat=0
        if [ $loaded -eq 1 ] && ifconfig $init_bond >/dev/null 2>&1;then
            echo -$init_bond > $SYSDIR/bonding_masters
        fi
    else
        msg="Failed"
        local err="Error: The bonding is not supported by the kernel."
    fi

    format_output "$prefix" "$msg"

    if [ -n "$err" ];then
        echo "$err"
    fi
    return $stat
}

function verify_bonding()
{
    local stat=1
    local msg=""
    local prefix="    Checking bonding \`$1' "

    if [ -z "$1" ];then
        msg="Null"
    else
        local res=$(nic_status "$1")
        if [ $res = "none" ] || null_bonding "$1";then
            msg="Ok"
            stat=0
        else
            msg="Failed"
            local err="Error: The existing bonding device is not empty."
        fi
    fi

    format_output "$prefix" "$msg"

    if [ -n "$err" ];then
        echo "$err"
    fi
    return $stat
}

function open_install_script()
{
    open_file "/tmp/bonding/_install.sh"
}

function write_install_script()
{
    FILE="/tmp/bonding/_install.sh"
    write_file "$1" $FILE
}

function close_install_script()
{
    chmod +x "/tmp/bonding/_install.sh"
}

function gen_install_script()
{
    MASTER=$1
    MODE=$2
    SLAVE1=$3
    SLAVE2=$4
    IPADDR=$5
    NETMASK=$6
    GATEWAY=$7

    open_install_script 
    write_install_script "##"
    write_install_script "## -- `date` --"
    write_install_script "## This script is automatically generated, please do not modify it."
    write_install_script "#"
    write_install_script ". ${bonding_dir}/bonding-functions.sh"

    write_install_script "## Build bonding device"
    if ! master_exists $MASTER;then
        write_install_script "led_write \"Creating\ \<$MASTER\>\""
        write_install_script "add_master $MASTER $IPADDR $NETMASK"
    else
        write_install_script "# Empty \`$MASTER' already exists, use it directly."
        if_up_sync $MASTER $IPADDR $NETMASK "10"
    fi

    write_install_script "# Set mode to \`$MODE'"
    write_install_script "$NETTOOL $MASTER down"
    write_install_script "echo $MODE > $SYSDIR/$MASTER/bonding/mode"
    write_install_script "if_up_sync $MASTER 5"

    if ! nic_in_bonding $MASTER $SLAVE1 ;then
        write_install_script "enslave $MASTER $SLAVE1"
    else
        write_install_script "# \`$SLAVE1' has been in the bonding device \`$MASTER'"
        write_install_script "if_up_sync $SLAVE1 0 0 10"
    fi

    if ! nic_in_bonding $MASTER $SLAVE2 ;then
        write_install_script "enslave $MASTER $SLAVE2"
    else
        write_install_script "# \`$SLAVE2' has been in the bonding device \`$MASTER'"
        write_install_script "if_up_sync $SLAVE2 0 0 10"
    fi

    write_install_script "set_primary $MASTER $SLAVE1"

    if [ -n "$GATEWAY" ];then
        write_install_script "## Build route table"
        write_install_script "led_write \"Update\ gateway\""
        write_install_script "$IPUTILS route delete default > /dev/null 2>&1"
        write_install_script "$IPUTILS route add default via $GATEWAY dev $MASTER"
        write_install_script "check_peer $GATEWAY 60"
    fi

    write_install_script "led_write \"\<$MASTER\>\ created\""
    write_install_script "sleep 3 && led_write \"$(mgnt_dev_addr)\" &"
    close_install_script
    return $?
}

LOGFILE="${LOGDIR}/add-bonding.log"
TIMESTAMP=`date`

# script start
if [ $# -lt 6 ];then
    usage_exit "No enough parameters"
fi

MASTER=$1
MODE=$2
SLAVE1=$3
SLAVE2=$4
IPADDR=$5
NETMASK=$6
GATEWAY=$7

exec &>${LOGFILE}
echo "CAPABILITIES:"
verify_capabilities || exit $?

echo "BONDING MODE SUPPORTED:"
verify_mode $MODE || exit $?
echo "BONDING MODULE:"
verify_module || exit $?

echo "BONDING DEVICE:"
verify_bonding $MASTER || exit $?

echo "ENVIRONMENT TOOLS:"
verify_tools $NETTOOL  || exit $?
verify_tools $DHCLIENT || exit $?
verify_tools $IPUTILS  || exit $?
verify_tools $PING     || exit $?

echo "NET DEVICES:"
verify_nic $SLAVE1 || exit $?
verify_nic $SLAVE2 || exit $?

echo "RELATIONSHIP:"
verify_slave $SLAVE1 $MASTER || exit $?
verify_slave $SLAVE2 $MASTER || exit $?

echo "NETWORK PARAMETERS:"
verify_ip $IPADDR  "address" || exit $?
verify_ip $NETMASK "netmask" || exit $?

if [ -n "$GATEWAY" ];then
    verify_ip $GATEWAY "gateway" || exit $?
fi

echo "Generate \`$INSCRIPT' at Fri Jul 22 06:19:26 UTC 2016"
gen_install_script "$@"
exit $?

###echo "================== ADD START ==================="
###echo "date    : $TIMESTAMP"
###
###led_write "Creating\ ${MASTER}..."
###
#### Add bonding and enslave NIC
###if [ "$EXISTS" -eq "1" ];then
###	add_master $MASTER $IPADDR $NETMASK
###else
###        if_up_sync $MASTER $IPADDR $NETMASK "10"
###fi
###
###enslave ${MASTER} ${SLAVE1}
###enslave ${MASTER} ${SLAVE2}
###set_primary ${MASTER} ${SLAVE1}
###
#### It's a gateway ?
###if [ -n $GATEWAY ];then
###	$IPUTILS route add default via $GATEWAY dev $MASTER || exit $?
###fi
###
#### Show result
###SLAVES=`get_slaves $MASTER`
###PRIMARY=`get_primary $MASTER`
###ACTIVE=`get_active_slave $MASTER`
###
###echo -n "master  : $MASTER"
###if [ -n $GATEWAY ];then
###    echo " [$IPADDR/$NETMASK via $GATEWAY]"
###else
###    echo " [$IPADDR/$NETMASK, no gateway]"
###fi
###echo "slaves  : $SLAVES"
###echo "primary : $PRIMARY [`port_type $PRIMARY`]"
###echo "active  : $ACTIVE [`port_type $ACTIVE`]"
###echo "------------------------------------------------"
###/sbin/route -n
###echo "------------------------------------------------"
###cat /proc/net/bonding/${MASTER}
###echo "------------------------------------------------"
###
###msg="Recovering..."
###count=1
###result=0
###if [ -n "$GATEWAY" ];then
###    while ! $PING $GATEWAY -c 1 -W 1 > /dev/null;do
###        led_write "${msg}\ ${count}s"
###        let count++
###
###	if [ $count -gt "60" ];then
###            let result++
###            break;
###        fi
###    done
###
###    if [ $result -ne 0 ];then
###        led_write "Link\ failure"
###    else
###        led_write "$MASTER\ created"
###    fi
###fi
###
###sleep 3
###led_write `mgnt_dev_addr`
###echo "Errno: $result, Elapsed: ${count}s"
###exit $result
###echo "================== ADD END ===================="
6### Script finishd
