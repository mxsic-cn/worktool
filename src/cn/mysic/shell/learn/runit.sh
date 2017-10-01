#!/usr/bin/env bash
# ScriptName: runit.sh

PS3="Select a program to execute:"
select program in 'ls -F' pwd date ps break exit
do
    $program
done
