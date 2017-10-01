#!/usr/bin/env bash
#ScriptName:permx.sh
#
back_dir=~/Documents/shell/backup/*
ls -la $back_dir

for file in $back_dir # Empty wordlist
 do
  if [[ -f $file && ! -x $file ]]
  then
   chmod +x $file
   echo "==$file now has execute permission"
  fi
 done
