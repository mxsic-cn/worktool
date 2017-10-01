#!/bin/bash
# this script named: kafkaEncrypt.sh
# it's check the kafka encrypt config make sure the kafka start with clean logs data
# and mysql offsets data to be reset

del_kafka_logs(){
    for file in /data/logs/kafka-logs/* # clean the kafka logs
    do
        if test -d $file
        then
            sudo rm -f $file/*   # the reboot user have the root permission -f
        fi
    done
#    sudo rm -rf /data/logs/kafka-logs/*
     sudo rm -rf /data/logs/zookeeper/version-2/*
    num=$?
    echo $num
    if [ $num -eq 0 ]
    then
        echo "del kafka data OK !"
        return 0
    else
        echo "del kafka data Faild !"
        return 2
     fi
}

reset_mysql_data(){
    if [ -z "$MYSQL_HOST" ]; then
        MYSQL_HOST='127.0.0.1'
    fi
    mysql_cmd="mysql -u cornerstone -pLipton305! -h ${MYSQL_HOST} -P 3306 --connect-timeout=5"
    sql="use cornerstone; show tables like 'ConsumerOffsets';"
    result="$($mysql_cmd -e "$sql")"
    constant="Tables_in_cornerstone"
    result1=$(echo "$result" | grep "${constant}")
    if [  "$result1" != "" ]
    then
        echo "ConsumerOffsets is exist! "
        echo "delete from cornerstone.ConsumerOffsets;" | $mysql_cmd > /dev/null 2>&1
        if [ $? -eq 0 ]
        then
            echo "reset mysql data OK !"
            del_kafka_logs
            return $?
        else
            echo "reset mysql data Faild !"
            return 1
        fi
    else
        echo "ConsumerOffsets Not is exist! "
        del_kafka_logs
        return $?
    fi

}

encrypt=/data/encrypt.lock # Encrypted identification file

if [ -f $encrypt ]
then
    echo "Encryption change !"
    sudo rm -rvf $encrypt   # the reboot user have the root permission -rvf
    reset_mysql_data
    return $?
else
    echo "No encryption changes !"
    return 0
fi
