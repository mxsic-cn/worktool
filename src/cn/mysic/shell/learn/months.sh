#!/usr/bin/env bash
# ScriptName:months.sh
for month in Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec
do
 for week in 1 2 3 4
  do
   echo  "Processing the month of $month.OK?"
   read ans
   if [ "$ans" = n -o -z "$ans" ]
   then
    continue 2
   else
    echo  "Process week $week of $month?"
    read ans
    if [ "$ans" = n -o -z "$ans" ]
    then
     continue
    else
     echo "Now processing week $week of $month."
     sleep 1 # Commands go here
     echo "Done processing..."
    fi
   fi
  done
 done