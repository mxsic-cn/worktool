#!/bin/bash

now=$(date)
cmd=$1

echo "$now running command: "$cmd "in 3s"
sleep 3
echo "$now starting..."
$cmd
echo "$now done. restarting nginx"
service nginx restart
echo "$now done"