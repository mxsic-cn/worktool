#!/bin/bash

LOCK_FILE='/var/lock/reset.lock'
LOCK_FD=11

function timestamp_echo()
{
    local now="$(date "+%Y-%m-%d %H:%M:%S")"
    echo "[$now] $@"
}

function lock()
{
    exec 11>$LOCK_FILE
    flock -xn 11 || exit 1
}

function unlock()
{
    flock -u 11
    rm -f $LOCK_FILE
}

function register_sig_handler()
{
    trap unlock SIGINT
}

function main()
{
    register_sig_handler
    lock

    (
    now=$(date)

    if [ ! -z "$1" ]; then
        log_file=$1
    else
        log_file="/preserve/logs/factory_reset.log"
    fi

    exec 2>&1
    truncate --size=0 $log_file

    timestamp_echo "MW shutting down..." | tee -a $log_file
    timestamp_echo "first print some useful information" | tee -a $log_file
    #df -k | tee -a $log_file
    df -k >> $log_file
    #top -n 1 -b | tee -a $log_file
    top -n 1 -b >> $log_file
    timestamp_echo "emit swdown..."
    initctl emit swdown
    #sync && sleep 1

    timestamp_echo "removing data files..."
    rm -rvf /data/logs/kafka-logs | tee -a $log_file
    rm -rvf /data/logs/zookeeper | tee -a $log_file
    if [ -f /data/sw/env.sh ]; then
        . /data/sw/env.sh
    fi

    #if [ -f /preserve/routing_table.conf ]; then
    #    rm /preserve/routing_table.conf
    #    touch /preserve/routing_table.conf
    #fi

    #ifdown agl0; ifup agl0

    timestamp_echo "Stopping mysql..."
    service mysql stop

    #sync && sleep 1

    timestamp_echo "removing mysql data..."
    if [ -d /data/mysql ]; then
        rm -rf /data/mysql | tee -a $log_file
    fi

    #clear ntp configuration
    timestamp_echo "removing ntp files..."
    rm -vf /data/settings/ntp_service.conf | tee -a $log_file
    
    #clear syslog conf
    rm -f /data/syslog-ng/enabled
    service syslog-ng restart
    rm -rf /preserve/syslog_collection/*

    #sync && sleep 1

    timestamp_echo "starting mysql..."
    service mysql start | tee -a $log_file
    while ! service mysql status; do
        sleep 1
    done

    timestamp_echo "emit swup..."
    initctl emit swup
    while ! netstat -tulpn | grep 8333; do
        sleep 1
    done

    #rm /etc/nginx/sites-enabled/default
    #ln -s /etc/nginx/sites-available/setup /etc/nginx/sites-enabled/default

    timestamp_echo "restarting nginx..."
    service nginx restart
    #top -n 1 -b | tee -a $log_file
    top -n 1 -b >> $log_file
    initctl list|grep sw|tee -a $log_file
    timestamp_echo "done."
    exit 0
    )

    unlock
}

main $*

