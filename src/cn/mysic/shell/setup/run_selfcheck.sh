#!/bin/bash
cd /usr/share/cornerstone_setup
export PYTHONPATH=`pwd`
cd service
python self_check_service.py