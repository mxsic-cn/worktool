#!/bin/bash

. /usr/share/cornerstone_setup/script/bonding-functions.sh

if [ -n "$2" -a $2 != "force" ];then
	bash /usr/share/cornerstone_setup/script/snapshot.sh $1 $2
else
        bash /usr/share/cornerstone_setup/script/snapshot.sh
fi

bash /usr/share/cornerstone_setup/script/rem-bonding.sh "$@"

if [ $# -eq 0 ];then
    exit 1
fi

bash $RESCRIPT

if [ "$?" -ne "0" ];then
        led_write "Remove\ failed"
        led_write "Restore\ network"
	bash $ROLLFILE	
        if [ "$?" -ne "0" ];then
	    led_write "Restore\ failure"
        else
	    led_write "Restore\ success"
        fi
fi
