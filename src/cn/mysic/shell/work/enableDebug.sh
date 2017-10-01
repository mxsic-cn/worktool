#!/bin/sh

mount -o remount rw /

filePath='/usr/share/server/bin/server'

echo "Change JVM options..." 
sed -i 's/DEFAULT_JVM_OPTS=".*"/DEFAULT_JVM_OPTS="-Dlog4j.configuration=file:\/data\/sw\/log4j.xml -Dlog_dir=\/preserve\/logs\/ -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5006"/g' $filePath 
if [ $? != 0 ]
then 
    echo "[Failed]"
    exit 1
fi

echo "[Sucessed]"

echo "Now will restart sw/server "
service sw/server stop
sleep 2
service sw/server start


