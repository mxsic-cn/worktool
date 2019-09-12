#!/usr/bin/env bash
# ScriptName:mybackup.sh
# Purpose:Create backup files and store
# them in a backup directory.
#
backup_dir=~/Documents/shell/backup
mkdir $backup_dir
for file in *.sh
do
  if [ -f $file ]
  then
    cp $file $backup_dir/${file}.bak
    echo "$file is backup in $backup_dir"
  fi
done



for file in $backup_dir/*
do
    echo "file is $file";
done