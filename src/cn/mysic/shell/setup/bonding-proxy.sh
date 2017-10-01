#!/bin/bash

if [ -z "$1" ];then
    exit -1
fi

. /usr/share/cornerstone_setup/script/bonding-functions.sh 1 > /dev/null

command="$@"
result=$($@)
errno=$?
echo -n "$result"
exit $errno
