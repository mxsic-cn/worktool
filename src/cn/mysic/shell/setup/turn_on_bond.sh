#!/bin/bash
#ifdown agl0
#ifdown agl1
#ifdown bond0
#echo 1 > /sys/class/net/bond0/bonding/mode
#echo 100 > /sys/class/net/bond0/bonding/miimon
#ifenslave bond0 agl0 agl1
#ifup bond0
#ifup agl0
#ifup agl1

# CS-9651 use sysfs to control bond before system restart
if [ $# != 3 ]
then
    echo "Invalid parameter"
    return 0
fi

ifconfig agl0 down
ifconfig agl1 down
ifconfig bond0 down

sleep 1
echo -agl0 > /sys/class/net/bond0/bonding/slaves
echo -agl1 > /sys/class/net/bond0/bonding/slaves

echo 1 > /sys/class/net/bond0/bonding/mode
echo 100 > /sys/class/net/bond0/bonding/miimon
echo agl0 > /sys/class/net/bond0/bonding/primary

IP=$1
MASK=$2
GATEWAY=$3
ifconfig bond0 ${IP} netmask ${MASK} up

sleep 1
echo +agl0 > /sys/class/net/bond0/bonding/slaves
echo +agl1 > /sys/class/net/bond0/bonding/slaves

route del default
route add default gw ${GATEWAY}

# CS-9468
LOGFILE='/preserve/logs/bond_monitor.log'
DATE=`date -u +%F\ %T`
echo "${DATE} Start bonding-port monitor" >> ${LOGFILE}
killall port_monitor.sh
setsid /usr/share/cornerstone_setup/script/port_monitor.sh &