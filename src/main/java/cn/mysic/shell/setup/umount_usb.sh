#!/bin/bash

function usage_exit()
{
    exec 1>&2
    echo "Usage:"
    echo "    $(basename $0) [PARTITION | MOUNTPOINT]"
    echo "Options:"
    echo "    PARTITION      The USB device partition name"
    echo "    MOUNTPOINT     The mounted directory specified"
    echo "Example:"
    echo "    $(basename $0)"
    echo "    $(basename $0) sdd1"
    echo "    $(basename $0) /mnt/usb0"
    exit 1
}

function target_dev()
{
    local target=$1

    if [ $# -eq 0 ];then
        target=sdd1
    fi

    target=$(basename "$(grep $target /proc/mounts|awk '{print $1}')")
    echo $target
}

function dev_delete_node()
{
    local dev=$1
    local node="/dev/$dev"

    rm -f $node
}

function dev_umount()
{
    local dev="/dev/$1"
    umount $dev
}

function main()
{
    if [ $# -gt 1 ];then
        usage_exit
    fi

    local target=$(target_dev $1)

    if [ -z "$target" ];then
        echo "Error: Can't find the target - '$target'"
        usage_exit
    fi
    
    local disk=$(echo $target|sed 's/[[:digit:]]//g')

    if dev_umount $target;then
        dev_delete_node $target
        dev_delete_node $disk
    else
        usage_exit
    fi
}

main $@
