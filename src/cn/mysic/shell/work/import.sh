#!/bin/sh
echo 'mysql importing...'


TIME=`date +%Y%m%d`

cat /Users/siqishangshu/workspace/data/dump/create.sql /Users/siqishangshu/workspace/data/dump/Dump$TIME.dump | mysql --user=root -proot

PATH=/Users/siqishangshu/workspace/data/dump
echo "IMPORT:$PATH/Dump$TIME.dump"

echo 'mysql imported'
