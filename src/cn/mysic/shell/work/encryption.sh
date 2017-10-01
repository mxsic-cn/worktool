#!/usr/bin/bash
# check stat of encryption path
# param is IP 
ip=$1
echo $ip
min=1
max=10
while [ $min -le $max ]
do
    echo $min
    min=`expr $min + 1`
    echo "ls /cornerstone/boxes_request" | sh ~/Downloads/kafka/bin/zookeeper-shell.sh $ip:2181 # your kafka local path
    sleep 5;
done  

