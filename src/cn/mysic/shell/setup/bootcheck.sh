#/bin/bash
#set -xv
LOGDIR='/data/log'
RESULT="/data/log/selftest.result"
REPORT="/data/log/selftest.info"
DEBUG=1
LEDCOMMAND='/usr/bin/led_display' 
rm -f $RESULT
rm -f $REPORT
LED_FLAG=0
output_report()
{
	if [ -f $REPORT ]; then
	    :
	else
            mkdir -p $LOGDIR
	    touch $REPORT
	fi
    if [ -f $RESULT ]; then
        :
    else
        mkdir -p $LOGDIR
        touch $RESULT
	    echo 'result : 1' >> $RESULT
	    echo 'errorInfo : selftest pass' >> $RESULT
    fi
    if [ "$DEBUG" = "1" ]; then 
	    echo  "$1" >> $REPORT
	fi
}

output_error()
{
    local str
	local var_str
    LED_FLAG=1
	var_str=($1)
    if [ -f $RESULT ]; then
        :
    else
        touch $RESULT
	    echo 'result : 0' >> $RESULT
	    echo 'errorInfo : ' >> $RESULT
    fi
	output_report "$1"
	sed -i 's/^result : 1/result : 0/' $RESULT
	sed -i 's/^errorInfo : selftest pass/errorInfo : /' $RESULT
    if [ "$DEBUG" = "1" ]; then 
	    count=0
	    for str in ${var_str[@]} ; do
            #count=`expr $count + 1`
            let count++
	    done
	    for((i=$count;i>=0;i--)); do
	        sed -i 's%^errorInfo :*%& '''${var_str[$i]}'''%' $RESULT
            #    sed -i 's/^errorInfo :*/& '''$str'''/' $RESULT
	    done
    fi
}

parse_SN()
{
    P_C=${nSN:0:2}
    P_S=${nSN:2:4}

    if [ $P_C = "JA" ]; then
        if [ $P_S = "0301" ]; then
            P_NAME=KEA
            P_MODEL=U1000
            P_CODE=1
        elif [ $P_S = "0401" ]; then
            P_NAME=KEA
            P_MODEL=U2000
            P_CODE=2
        elif [ $P_S = "0302" ]; then
            P_NAME=KEA
            P_MODEL=U1000E
            P_CODE=3
        else
            :
        fi
    elif [ $P_C = "SS" ]; then
        if [ $P_S = "0101" ]; then
            P_NAME=KEC
            P_MODEL=U1000
            P_CODE=3
        elif [ $P_S = "0201" ]; then
            P_NAME=KEC
            P_MODEL=U2000
            P_CODE=4
        else
            :
        fi
    elif [ $P_C = "AJ" ]; then
        if [ $P_S = "0201" ]; then
		    P_NAME=KEP
			P_MODEL=U1000
			P_CODE=5
		else
			:
		fi
    elif [ $P_C = "ZG" ]; then
	    if [ $P_S = "0101" ]; then
		    P_NAME=KEP
			P_MODEL=U2000
			P_CODE=6
		else
		    :
        fi
    fi
}
    
check_SN()
{
    if [ x$1 != x ];then
        nSN=$1
    else
        nSN=`acorn_eeprom 2>/dev/null | sed -n '/serialnum/p' | awk 'gsub(/^ *| *$/, "")' | awk -F : '{print $2}'`
    fi
    echo $nSN
    if [ -n "$nSN" ];then
		output_report "Check SN OK \nSN: $nSN"
        parse_SN
        if [ $P_CODE = 0 ];then
			output_error "Parse SN Failure"
            flag=1
        else
            output_report  "Parse SN OK \nProduct Name: $P_NAME-$P_MODEL"
        fi
    else
        flag=1
        output_error   "Check SN Failure, no SN " 
    fi
}

check_CPU()
{
    if [ -f /proc/cpuinfo ]; then
        FIND_CPU_CORNS=`cat /proc/cpuinfo | grep "cpu cores" | awk -F : 'BEGIN{sum=0}{sum+=$2}END{print sum}' | sed s/[[:space:]]//g`
        FIND_CPU_VENDOR=`cat /proc/cpuinfo | sed -n '/vendor_id/{p;q}' | awk -F : '{print $2}' | sed s/[[:space:]]//g`
        FIND_CPU_BAND=`cat /proc/cpuinfo | sed -n '/model name/{p;q}' | awk '{print $6}' | sed s/[[:space:]]//g`
    fi	
	
	if [ "$FIND_CPU_CORNS" = "$CHECK_CPU_CORNS" ]; then
	    output_report "Cpu corns $FIND_CPU_CORNS meet"
	else
	    output_error "Cpu corns $FIND_CPU_CORNS not meet $CHECK_CPU_CORNS"
	fi
	
	if [ "$FIND_CPU_BAND" = "$CHECK_CPU" ]; then
	    output_report "Cpu type $FIND_CPU_BAND meet"
	else
	    output_error "Cpu type $FIND_CPU_BAND not meet $CHECK_CPU"
	fi

	if [ "$FIND_CPU_VENDOR" = "$CHECK_CPU_VENDOR" ]; then
	    output_report "Cpu vendor $FIND_CPU_VENDOR meet"
	else
	    output_error "Cpu vendor $FIND_CPU_VENDOR not meet $CHECK_CPU_VENDOR"
	fi	
}

check_MEM()
{
    if [ -f /proc/meminfo ]; then
        FIND=`cat /proc/meminfo | grep "^MemTotal" | tr -s ' ' | awk '{ print $2" "$3 }'`
        FIND_MEM_SIZE=`cat /proc/meminfo | grep "^MemTotal" | tr -s ' ' | awk '{ print $2" "$3 }' | awk '{ print $1 }'`
        FIND_MEM_UNITS=`cat /proc/meminfo | grep "^MemTotal" | tr -s ' ' | awk '{ print $2" "$3 }' | awk '{ print $2 }'`
    fi
	
	FIND_MEMORY_SIZE=`expr substr "$FIND_MEM_SIZE" 1 2`
	if [ "$FIND_MEMORY_SIZE" = "`expr substr "$CHECK_MEM_SIZE" 1 2`" ]; then
	    output_report "memory size $FIND_MEM_SIZE $FIND_MEM_UNITS  meet "
	else
		output_error "memory size $FIND_MEM_SIZE $FIND_MEM_UNITS not meet $CHECK_MEM_SIZE "
    fi
}

check_OS()
{
    FIND_OS=`uname`
    FIND_HARDWARE=`uname -m`
    FIND_OS_VERSION=`uname -r | sed 's/-.*//'`

	if [ "$FIND_OS" = "$CHECK_OS" -a "$FIND_OS_VERSION" = "$CHECK_KERNEL_VERSION" ]; then
	    output_report "OS $FIND_OS $FIND_OS_VERSION meet"
	else
	    output_error "OS $FIND_OS $FIND_OS_VERSION not meet"
    fi
}

check_FILESYSTEM()
{
    local x
	local p1
	local p2
	for x in ${CHECK_PARTITION[@]}; do
	    p1=$(echo $x | awk -F ':' '$0=$1')
	    p2=$(echo $x | awk -F ':' '$0=$2')
	    FIND_MOUNT=`mount -t ext2,ext3,ext4 | awk '{ print $1","$3","$5 }' | grep $p1 | cut -d ',' -f2`
	    if [ "$p2" = "$FIND_MOUNT" ]; then
	        output_report "$p1 mount $FIND_MOUNT ok"
	    else
	        output_error "$p1 mount $FIND_MOUNT error"
	    fi
	done
}

check_NETWORK()
{
   local x
   local p1
   local p2
   for x in ${CHECK_NETWORK[@]}; do
	   p1=$(echo $x | awk -F '_' '$0=$1')
	   p2=$(echo $x | awk -F '_' '$0=$2')
	   nnif=`ip a | grep $p1`
	   if [ -n "$nnif" ]; then
		   output_report "$p2 interface up"
	   else
		   output_error "$p2 interface down"
	   fi
   done
}

check_SN $1

case "$P_NAME"_"$P_MODEL" in
	"KEP_U1000")
		echo "KEP_U1000"
		CHECK_OS="Linux"
		CHECK_KERNEL_VERSION="3.13.10"
		CHECK_PARTITION=( "/dev/disk/by-label/SOUTHWEST:/" "/dev/mapper/lvm_crypts:/data" "/dev/sdb1:/shadow" "/dev/sdb2:/preserve" )
		CHECK_DISK_SIZE="900_G"
		CHECK_MEM_SIZE="16_G"
		CHECK_CPU="i7-4770S"
		CHECK_CPU_VENDOR="GenuineIntel"
		CHECK_CPU_CORNS="32"
		CHECK_NETWORK=( "agl0_MGMT" "eth1_net1" "eth2_net2" "agl1_EXT" )
                LEDCOMMAND='/usr/bin/ezio_300_api'
	;;
	"KEP_U2000")
	    echo "KEP_U2000"
		CHECK_OS="Linux"
		CHECK_KERNEL_VERSION="3.13.10"
		CHECK_PARTITION=( "/dev/disk/by-label/SOUTHWEST:/" "/dev/mapper/lvm_crypts:/data" "/dev/sdb1:/shadow" "/dev/sdb2:/preserve" )
		CHECK_DISK_SIZE="900_G"
		CHECK_MEM_SIZE="16_G"
		CHECK_CPU="i7-4770S"
		CHECK_CPU_VENDOR="GenuineIntel"
		CHECK_CPU_CORNS="32"
		CHECK_NETWORK=( "agl0_MGMT" "eth1_net1" "eth2_net2" "agl1_EXT" )
                LEDCOMMAND='/usr/bin/ezio_300_api'
	;;
	"KEA_U1000")
	    echo "KEA_U1000"
		CHECK_OS="Linux"
		CHECK_KERNEL_VERSION="3.13.10"
		CHECK_PARTITION=( "/dev/disk/by-label/SOUTHWEST:/" "/dev/mapper/lvm_crypts:/data" "/dev/sdb1:/shadow" "/dev/sdb2:/preserve" )
		CHECK_DISK_SIZE="900_G"
		CHECK_MEM_SIZE="16_G"
		CHECK_CPU="i7-4770S"
		CHECK_CPU_VENDOR="GenuineIntel"
		CHECK_CPU_CORNS="32"
		CHECK_NETWORK=( "agl0_MGMT" "eth1_net1" "eth2_net2" "agl1_EXT" )
	;;
	"KEA_U2000")
	    echo "KEA_U2000"
		CHECK_OS="Linux"
		CHECK_KERNEL_VERSION="3.13.10"
		CHECK_PARTITION=( "/dev/disk/by-label/SOUTHWEST:/" "/dev/mapper/lvm_crypts:/data" "/dev/sdb1:/shadow" "/dev/sdb2:/preserve" )
		CHECK_DISK_SIZE="900_G"
		CHECK_MEM_SIZE="16_G"
		CHECK_CPU="i7-4770S"
		CHECK_CPU_VENDOR="GenuineIntel"
		CHECK_CPU_CORNS="32"
		CHECK_NETWORK=( "agl0_MGMT" "eth1_net1" "eth2_net2" "agl1_EXT" )
	;;
	"KEA_U1000E")
	    echo "KEA_U1000E"
		CHECK_OS="Linux"
		CHECK_KERNEL_VERSION="3.13.10"
		CHECK_PARTITION=( "/dev/disk/by-label/SOUTHWEST:/" "/dev/mapper/lvm_crypts:/data" "/dev/sdb1:/shadow" "/dev/sdb2:/preserve" )
		CHECK_DISK_SIZE="900_G"
		CHECK_MEM_SIZE="16_G"
		CHECK_CPU="i7-4770S"
		CHECK_CPU_VENDOR="GenuineIntel"
		CHECK_CPU_CORNS="32"
		CHECK_NETWORK=( "agl0_MGMT" "eth0_P0" "eth1_P1" "eth2_P2" "eth3_P3" "eth4_EXT" )
	;;
	"KEC_U1000")
	    echo "KEC_U1000"
		CHECK_OS="Linux"
		CHECK_KERNEL_VERSION="3.13.10"
		CHECK_PARTITION=( "/dev/disk/by-label/SOUTHWEST:/" "/dev/mapper/lvm_crypts:/data" "/dev/sdb1:/shadow" "/dev/sdb2:/preserve" )
		CHECK_DISK_SIZE="900_G"
		CHECK_MEM_SIZE="16_G"
		CHECK_CPU="i7-4770S"
		CHECK_CPU_VENDOR="GenuineIntel"
		CHECK_CPU_CORNS="32"
		CHECK_NETWORK=( "agl0_MGMT" "eth0_P0" "eth1_P1" "eth2_P2" "eth3_P3" "agl1_EXT" )
	 ;;
	"KEC_U2000")
		echo "KEC_U2000"
		CHECK_OS="Linux"
		CHECK_KERNEL_VERSION="3.13.10"
		CHECK_PARTITION=( "/dev/disk/by-label/SOUTHWEST:/" "/dev/mapper/lvm_crypts:/data" "/dev/sdb1:/shadow" "/dev/sdb2:/preserve" )
		CHECK_DISK_SIZE="900_G"
		CHECK_MEM_SIZE="16_G"
		CHECK_CPU="i7-4770S"
		CHECK_CPU_VENDOR="GenuineIntel"
		CHECK_CPU_CORNS="32"
		CHECK_NETWORK=( "agl0_MGMT" "eth0_P0" "eth1_P1" "eth2_P2" "eth3_P3" "eth4_EXT" )
	;;
	*)
		output_error "unknown $P_NAME"_"$P_MODEL"
esac


check_OS
check_FILESYSTEM
check_MEM
check_CPU
check_NETWORK
if [ "$LED_FLAG" = "1" ]; then
     $LEDCOMMAND -d /dev/ttyS1 -s 1 "selftest fail!"
else
     $LEDCOMMAND -d /dev/ttyS1 -s 1 "selftest pass!"
fi
