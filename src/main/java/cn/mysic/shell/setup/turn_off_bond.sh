#!/bin/bash

# CS-9468
LOGFILE='/preserve/logs/bond_monitor.log'
DATE=`date -u +%F\ %T`
echo "${DATE} Stop bonding-port monitor" >> ${LOGFILE}
killall port_monitor.sh

ifconfig agl0 down
ifconfig agl1 down
ifconfig bond0 down

sleep 1
echo -agl0 > /sys/class/net/bond0/bonding/slaves
echo -agl1 > /sys/class/net/bond0/bonding/slaves


echo 0 > /sys/class/net/bond0/bonding/mode
ifconfig agl0 up
ifconfig agl1 up