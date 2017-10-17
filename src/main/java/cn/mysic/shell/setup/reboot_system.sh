#!/bin/bash
now=$(date)
echo "$now rebooting system in 5s"
sleep 5
/sbin/initctl emit swdown
sleep 5
service mysql stop
touch /data/configured
reboot
