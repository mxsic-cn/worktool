#!/usr/bin/env bash

install_dir="/usr/share/cornerstone_setup"
upgrade_dir="/usr/share/cornerstone_setup_upgrade"
if [ -d ${upgrade_dir} ]; then
    mount -o remount,rw /
    {
        rm -rf ${install_dir}
        mkdir -p ${install_dir}
        cp -r ${upgrade_dir}/* ${install_dir}
        chmod -R 755 ${install_dir}
        chown -R swrun:swrun ${install_dir}/main/engine_wrapper.pyc
        rm -rf ${upgrade_dir}
    } || {
        sync; sync; sync
        echo "remounting root as read-only after upgrade"
        mount -no remount,ro /
    }
fi
