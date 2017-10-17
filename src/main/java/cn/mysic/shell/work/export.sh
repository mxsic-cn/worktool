#!/bin/sh
echo 'mysql dumpping...'


TIME=`date +%Y%m%d`

 mysqldump -u root -p logistics > /Users/siqishangshu/workspace/data/dump/Dump$TIME.dump

PATH=/Users/siqishangshu/workspace/data/dump
echo "DUMP:$PATH/Dump$TIME.dump"

echo 'mysql dumped'
