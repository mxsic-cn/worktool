#!/bin/bash
#echo "rebooting system in 5s"
#sleep 5
#reboot

now=$(date)
echo "$now restarting mw and mysql processes"

initctl emit swdown
sleep 5
#service mysql stop
#service mysql start
#while ! service mysql status; do
#    sleep 1
#done
initctl emit swup
while ! netstat -tulnp | grep 8333; do
    sleep 1
done
service nginx restart

stop cornerstone_setup
sleep 2
start cornerstone_setup