#!/usr/bin/env bash
# Scriptname:forloop.sh
for name in Tom Dick Harry Joe
do
    echo "Hi $name"
done
echo "out of loop"
echo "========secod part============="
for name in `cat namelist`
do
    echo "Hi $name"
done
echo "out of loop"