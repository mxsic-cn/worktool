#!/bin/bash

status_raid=0
status_active=0
status_resyncing=0
status_degraded=0
status_rebuilding=0
disk_1=0
disk_2=0
sn_1=0
sn_2=0
disk_degraded=0
sn_degraded=0
disk_normal=0
sn_normal=0
disk_rebuilding=0
sn_rebuilding=0
status_tmp=0

check_raid_config()
{
    cat /proc/mdstat | grep -w md126 > /dev/null
    if [ "$?" -eq "0" ] ; then
        status_raid=1
    else
        status_raid=0
    fi
}

chech_raid_status()
{
    if [ "$status_raid" -eq "1" ] ; then
        mdadm -D /dev/md126 | grep "State :" | grep active > /dev/null
        if [ "$?" -eq "0" ] ; then
            status_active=1
        else
            status_active=0
        fi

        mdadm -D /dev/md126 | grep "State :" | grep resyncing > /dev/null
        if [ "$?" -eq "0" ] ; then
            status_resyncing=1
        else
            status_resyncing=0
        fi

        mdadm -D /dev/md126 | grep "State :" | grep recovering > /dev/null
        if [ "$?" -eq "0" ] ; then
            status_rebuilding=1
        else
            status_rebuilding=0
        fi

        mdadm -D /dev/md126 | grep "State :" | grep degraded > /dev/null
        if [ "$?" -eq "0" ] ; then
            status_degraded=1
        else
            status_degraded=0
        fi
    fi

}

update_disk_info()
{
    :>$1
    echo  "$disk_1" >> $1
    echo  "$sn_1">> $1
    echo  "$disk_2" >> $1
    echo  "$sn_2" >> $1
}

create_disk_info()
{
    touch /shadow/tmp/disk_info
}

get_disk_info()
{
    if [ "$status_raid" -eq "1" ] ; then
        #degraded
        if [ "$status_degraded" -eq "1" -a "$status_rebuilding" -eq "0" ] ; then
            disk_1=$(cat /shadow/tmp/disk_info| awk '{print $1}'|awk 'NR==1')
            disk_2=$(cat /shadow/tmp/disk_info| awk '{print $1}'|awk 'NR==3')
            sn_1=$(cat /shadow/tmp/disk_info| awk '{print $1}'|awk 'NR==2')
            sn_2=$(cat /shadow/tmp/disk_info| awk '{print $1}'|awk 'NR==4')

            disk_degraded=$(mdadm -D /dev/md126 | grep /dev/sd | awk '{print $7}')
            if [ "$disk_degraded" = "$disk_1" ]; then
                disk_degraded=$disk_2
                sn_degraded=$sn_2
                disk_normal=$disk_1
                sn_normal=$sn_1
            
            elif [ "$disk_degraded" = "$disk_2" ]; then
                disk_degraded=$disk_1
                sn_degraded=$sn_1
                disk_normal=$disk_2
                sn_normal=$sn_2
            fi

        elif [ "$status_rebuilding" -eq "1" ] ; then
            status_tmp=$(mdadm -D /dev/md126 | grep /dev/sd | awk '{print $6}'|awk 'NR==2')
            disk_1=$(mdadm -D /dev/md126 | grep /dev/sd | awk '{print $7}'|awk 'NR==1')
            disk_2=$(mdadm -D /dev/md126 | grep /dev/sd | awk '{print $7}'|awk 'NR==2')
            sn_1=$(udevadm info "$disk_1" | grep SHORT |awk -F = '{printf $2}')
            sn_2=$(udevadm info "$disk_2" | grep SHORT |awk -F = '{printf $2}')

            if [ "$status_tmp" = "rebuilding" ] ; then
                disk_rebuilding=$disk_2
                sn_rebuilding=$sn_2
                disk_normal=$disk_1
                sn_normal=$sn_1
            else
                disk_rebuilding=$disk_1
                sn_rebuilding=$sn_1
                disk_normal=$disk_2
                sn_normal=$sn_2
            fi            
           
            if [ -e /shadow/tmp/disk_info ]; then
                update_disk_info /shadow/tmp/disk_info
            else
                create_disk_info
                update_disk_info /shadow/tmp/disk_info
            fi

        else
            disk_1=$(mdadm -D /dev/md126 | grep /dev/sd | awk '{print $7}'|awk 'NR==1')
            disk_2=$(mdadm -D /dev/md126 | grep /dev/sd | awk '{print $7}'|awk 'NR==2')
            sn_1=$(udevadm info "$disk_1" | grep SHORT |awk -F = '{printf $2}')
            sn_2=$(udevadm info "$disk_2" | grep SHORT |awk -F = '{printf $2}')

            if [ -e /shadow/tmp/disk_info ]; then
                update_disk_info /shadow/tmp/disk_info
            else
                create_disk_info
                update_disk_info /shadow/tmp/disk_info
            fi
        fi
    fi
}

send_status_info()
{
    if [ "$status_raid" -eq "0" ] ; then
        echo "no_raid"
        return
    elif [ "$status_rebuilding" -eq "1" ]; then
        echo "rebuilding"
        return
    elif [ "$status_degraded" -eq "1" ]; then
        echo "degraded"
        return
    elif [ "$status_resyncing" -eq "1" ]; then
        echo "resyncing"
        return
    elif [ "$status_active" -eq "1" ]; then
        echo "normal"
        return
    fi
}

send_disk_info()
{
    if [ "$status_degraded" -eq "1" -a "$status_rebuilding" -eq "0" ] ; then
        echo  $disk_degraded $sn_degraded degraded
        echo  $disk_normal $sn_normal normal
        return
    fi

    if [ "$status_rebuilding" -eq "1" ] ; then
        echo  $disk_rebuilding $sn_rebuilding rebuilding
        echo  $disk_normal $sn_normal  normal
    else
        echo  $disk_1 $sn_1 normal
        echo  $disk_2 $sn_2 normal
    fi
}

#set -x
check_raid_config
chech_raid_status
get_disk_info
send_status_info
send_disk_info