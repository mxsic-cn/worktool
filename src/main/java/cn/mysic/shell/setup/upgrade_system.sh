#!/bin/bash

set -e

file=$1

if [ ! -z "$2" ]; then
   log_file=$2
else
   log_file="/preserve/logs/upgrade_system.log"
fi

now=$(date)

echo "$now Start running upgrade script $file" | tee -a $log_file

echo "System version before upgrading:" | tee -a $log_file
cat /usr/share/version.txt | tee -a $log_file

if [ ! -e $file ]; then
    echo "Error: upgrade script $file does not exist" | tee -a $log_file
    exit 1
fi

chown root:root $file
sh $file | tee -a $log_file

echo "Upgrade script is run successfully" | tee -a $log_file
echo "System version after upgrading:" | tee -a $log_file
cat /usr/share/version.txt | tee -a $log_file
true
