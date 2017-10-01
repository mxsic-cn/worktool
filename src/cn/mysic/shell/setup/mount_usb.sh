#!/bin/bash

PROC_FILE='/proc/partitions'
SYSFS_DIR='/sys/block'

function usage_exit()
{
    exec 1>&2
    echo "Usage:"
    echo "    $(basename $0) [DEV MOUNTPOINT]"
    echo "Options:"
    echo "    DEV            The USB device name"
    echo "    MOUNTPOINT     The mounted directory specified"
    echo "Example:"
    echo "    $(basename $0)"
    echo "    $(basename $0) sdd /media/usb0"
    exit 1
}

function dev_exist()
{
    local dev=$1

    grep -q $dev $PROC_FILE 2>/dev/null
}

function is_usb_disk()
{
    local dev=$1
    local removable=$(cat $SYSFS_DIR/$dev/removable)
    
    [ "$removable" -ne 0 ]
}

function proc_partitions_col()
{
    local dev=$1
    local col=$2
    local PROC_FILE='/proc/partitions'

    echo $(grep -P "$dev$" $PROC_FILE|awk "{print \$$col}")
}

function dev_major()
{
    local dev=$1
    echo $(proc_partitions_col $dev 1)
}

function dev_minor()
{
    local dev=$1
    echo $(proc_partitions_col $dev 2)
}

function dev_create_node()
{
    local dev=$1
    local major=$(dev_major $dev)
    local minor=$(dev_minor $dev)
    local node="/dev/$dev"

    if [ -n "$major" -a -n "$minor" ] && [ ! -b "$node" ];then
        if ! mknod $node b $major $minor;then
            echo "Error: Cannot create dev node - '/dev/$disk'" 1>&2
            return 1
        fi
    fi
}

function dev_mount()
{
    local dev="/dev/$1"
    local mountpoint=$2

    if [ ! -d $mountpoint ];then
        if ! mkdir -p $mountpoint;then
            return 1
        fi
    fi 

    mount $dev $mountpoint
    if [ $? -eq 0 -o $? -eq 32 ];then
        return 0
    else
        return 1
    fi
}

function disk_clear_partitions()
{
    local disk="/dev/$1"

    dd if=/dev/zero of=$disk bs=512K count=32 >/dev/null 2>&1
}

function disk_create_one_partition()
{
    local dev="/dev/$1"
    
    (echo -ne "n\np\n\n\n\nw") | fdisk "$dev" > /dev/null 2>&1
    partprobe
}

function dev_format()
{
    local dev="/dev/$1"
    mkfs.vfat $dev >/dev/null 2>&1
}

function mount_usb_disk_node()
{
    local disk=$1
    local partition="${disk}1"
    local mountpoint=$2

    if ! dev_exist $disk;then
        echo "Error: Can't find the USB device - '$disk'" 1>&2
        return 1
    fi

    if ! is_usb_disk $disk;then
        echo "Error: The disk specified is not a USB device - '$disk'" 1>&2
        return 1
    fi

    if ! dev_create_node $disk;then
        return 1
    fi

    #disk_clear_partitions $disk 
    #disk_create_one_partition $disk

    if ! dev_create_node $partition;then
        return 1
    fi

    #dev_format $partition
    dev_mount $partition $2
}

function main()
{
    if [ $# -ne 0 -a $# -ne 2 ];then
        usage_exit
    fi

    local dev=$1
    local mountpoint=$2

    if [ $# -eq 0 ];then
        local dev="sdd"
        local mountpoint="/data/usb0"
    fi

    if ! mount_usb_disk_node $dev $mountpoint;then
        usage_exit
    else
        echo $mountpoint
    fi
}

main $@
