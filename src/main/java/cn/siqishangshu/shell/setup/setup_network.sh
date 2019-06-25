#!/usr/bin/env bash

set -e

export JQ="/usr/share/server/jq"
export HARDWARE_JSON="/hardware.json"
export mgntPort=`${JQ} --raw-output '.mgntPort' ${HARDWARE_JSON}`
export led=`${JQ} --raw-output '.led' ${HARDWARE_JSON}`
export ext=`${JQ} --raw-output '.ext' ${HARDWARE_JSON}`

echo "management port: $mgntPort"
echo "led: $led"
echo "ext: $ext"

echo "Configuring $mgntPort to agl0"
ifconfig $mgntPort down || true
ip link set $mgntPort name agl0 || true

echo "Configuring $ext to agl1"
ifconfig $ext down || true
ip link set $ext name agl1 || true

#ls /sys/class/net | while read intf_name; do
#    echo "Up $intf_name"
#    ifup $intf_name || true
#done

ifup agl0 up || true
ifup agl1 up || true
ifup eth2 up || true
ifup eth3 up || true
ifup eth4 up || true
ifup eth5 up || true

echo "display lcd to $led"
gw_port=`/usr/share/cornerstone_setup/script/bonding-proxy.sh  get_default_gateway |cut -d" " -f2`
if [ "${gw_port}" == "" ]
then
    gw_port="agl0"
fi
/usr/share/server/led_status.sh ${gw_port} $led || true

if [ ! -d /data/syslog-ng ];then
    mkdir -p /data/syslog-ng
fi

