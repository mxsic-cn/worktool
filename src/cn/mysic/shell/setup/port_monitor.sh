#!/bin/bash

sleep 3
LOGFILE='/preserve/logs/bond_monitor.log'

SLAVE1_NAME=`cat /proc/net/bonding/bond0 | awk -F ': ' '/^Slave Interface/{print $2}' | awk 'NR==1'`
SLAVE2_NAME=`cat /proc/net/bonding/bond0 | awk -F ': ' '/^Slave Interface/{print $2}' | awk 'NR==2'`
if [ "${SLAVE1_NAME}" = "agl0" ]
then
    SLAVE1_NAME="ETH1"
    SLAVE2_NAME="ETH2"
else
    SLAVE1_NAME="ETH2"
    SLAVE2_NAME="ETH1"
fi

SLAVE1_STATE=""
SLAVE2_STATE=""

cd ../service
while true
do
    sleep 2

   CUR_SLAVE1_STATE=`cat /proc/net/bonding/bond0 | awk -F ': ' '/^MII Status/{print $2}' | awk 'NR==2'`
   CUR_SLAVE2_STATE=`cat /proc/net/bonding/bond0 | awk -F ': ' '/^MII Status/{print $2}' | awk 'NR==3'`

    if [ "${CUR_SLAVE1_STATE}" = "" ] || [ "${CUR_SLAVE2_STATE}" = "" ]
    then
        DATE=`date -u +%F\ %T`
        echo "${DATE} Get port status failed" >> ${LOGFILE}
        continue
    fi

    PARA=""
    if [ "${CUR_SLAVE1_STATE}" != "${SLAVE1_STATE}" ]
    then
        PARA="${SLAVE1_NAME} ${CUR_SLAVE1_STATE}"
 
        DATE=`date -u +%F\ %T`
        echo "${DATE} Port ${SLAVE1_NAME} status changed, pre:${SLAVE1_STATE}, now:${CUR_SLAVE1_STATE}" >> ${LOGFILE}
    fi

    if [ "${CUR_SLAVE2_STATE}" != "${SLAVE2_STATE}" ]
    then
        PARA="${PARA} ${SLAVE2_NAME} ${CUR_SLAVE2_STATE}"

        DATE=`date -u +%F\ %T`
        echo "${DATE} Port ${SLAVE2_NAME} status changed, pre:${SLAVE2_STATE}, now:${CUR_SLAVE2_STATE}" >> ${LOGFILE}
    fi

    if [ "${PARA}" != "" ]
    then
        SLAVE1_STATE=${CUR_SLAVE1_STATE}
        SLAVE2_STATE=${CUR_SLAVE2_STATE}
        python bondingStatus.py ${PARA} 
        if [ $? != 0 ]
        then
            echo "${DATE} Call bondingStatus.py failed" >> ${LOGFILE}
        fi
    fi            
done

