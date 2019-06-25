#!/bin/sh

#启动zookeeper


/home/liuchuan/Downloads/kafka/bin/zookeeper-server-start.sh /home/liuchuan/Downloads/kafka/config/zookeeper.properties &

sleep 3 #等3秒后执行

#启动kafka

/home/liuchuan/Downloads/kafka/bin/kafka-server-start.sh /home/liuchuan/Downloads/kafka/config/server.properties &

:wq! #保存退出

#创建关闭脚本
