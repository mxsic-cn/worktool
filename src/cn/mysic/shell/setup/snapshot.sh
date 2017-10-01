#!/bin/bash

MSG="# --- `date` ---\n# This script is automatically generated, Please don't edit.\n# Restore network environment"

function write_rollfile()
{
    write_file "$1" $ROLLFILE
}

function write_rollfile_comment()
{
    write_rollfile "# $1"
}

function rollback_affected()
{
    if [ -z "$1" ];then
         return 1
    fi

    local NIC_LIST=($@)
    for nic in ${NIC_LIST[@]};do
        local addr=$(get_ipv4_address $nic)
        local mask=$(get_ipv4_netmask $nic)
        local gateway=$(get_gateway $nic)
        local stat=$(nic_status $nic)

        if [ $stat = "none" ];then 
            write_rollfile_comment "Network adapter does not exist: \`$nic'"
            continue
        fi

        if bonding_dev $nic;then
            write_rollfile_comment "Create bonding: $nic"
            local mode=$(bonding_mode $nic)
            write_rollfile "echo +$nic > $SYSDIR/bonding_masters"
            write_rollfile "echo $mode > $SYSDIR/$nic/bonding/mode"

            if [ -n "$addr" -a -n "$mask" ];then
                write_rollfile "$NETTOOL $nic $addr netmask $mask $stat"
            else
                write_rollfile "$NETTOOL $nic $stat"
            fi

        	local slaves=$(get_slaves $nic)
                for slave in ${slaves[@]};do
                    write_rollfile_comment "enslave \`$slave' to \`$nic'"
                    write_rollfile "$NETTOOL $slave 0 down"
                    write_rollfile "echo +$slave > $SYSDIR/$nic/bonding/slaves"
                    write_rollfile "$NETTOOL $slave up"
                done

                if [ "$mode" = "active-backup" ];then
		    local primary=get_primary $nic
                    write_rollfile_comment "set primary \`$primary'"
                    write_rollfile "echo $primary > $SYSDIR/$nic/bonding/primary"
                fi
                
                local miimon=$(bonding_get_option $nic "miimon")
                write_rollfile_comment "Set mii monitor interval"
                write_rollfile "echo 100 > $SYSDIR/$nic/bonding/miimon"
        else
            if nic_enslaved $nic;then
		write_rollfile_comment "\`$nic' is enslaved in \`$(nic_master $nic)', ignored"
                continue
            fi

            if [ -n "$addr" -a -n "$mask" ];then
                write_rollfile "$NETTOOL $nic $addr netmask $mask $stat"
            else
                write_rollfile "$NETTOOL $nic $stat"
            fi
	fi

    if [ -n "$gateway" ];then
        write_rollfile_comment "\`$nic' is a gateway port"
        write_rollfile "$IPUTILS route delete default > /dev/null 2>&1"
        write_rollfile "$IPUTILS route add default via $gateway dev $nic"
        write_rollfile "source `pwd`/bonding-functions.sh"
        write_rollfile "check_peer $gateway 60"
	write_rollfile "sleep 3 && led_write \"$(mgnt_dev_addr)\" &"
    fi
    done
}

function rollback_all()
{
    local ADDR_LIST=$(list_all_nic_name)
    if [ -n "$ADDR_LIST" ];then
        rollback_affected $ADDR_LIST
    fi
}

# script start
. /usr/share/cornerstone_setup/script/bonding-functions.sh
mkdir -p $ROLLDIR
echo "Generate script: $ROLLFILE"
echo -e "$MSG\n\n" > $ROLLFILE

if [ $# -eq 0 ];then
	rollback_all
elif [ $# -ge 1 ];then
	rollback_affected "$@"
fi

