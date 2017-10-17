#!/usr/bin/env bash
# Name: doit.sh
# Purpose:shift through command line arguments
# Usage:doit.sh[args]
echo  $2  $#
while [ $# -gt 0 ] # or (($#>0))
do
   echo  $*
   echo  $1
   shift
done
echo $1