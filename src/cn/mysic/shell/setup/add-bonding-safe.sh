#!/bin/bash

. /usr/share/cornerstone_setup/script/bonding-functions.sh

led_write "Create snapshot"
if [ -n "$4" ];then
	bash /usr/share/cornerstone_setup/script/snapshot.sh $3 $4
else
        bash /usr/share/cornerstone_setup/script/snapshot.sh
fi

bash /usr/share/cornerstone_setup/script/add-bonding.sh $@

if [ $# -eq 0 ];then
    exit 1
fi

bash $INSCRIPT

if [ "$?" -ne "0" ];then
        led_write "Bonding\ failed"
        led_write "Restore\ network"
        remove_master $1
	bash $ROLLFILE	
        if [ "$?" -ne "0" ];then
	    led_write "Restore\ failure"
        else
	    led_write "Restore\ success"
        fi
fi
