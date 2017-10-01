#!/usr/bin/bash
# send all the pcap in the file

eth=$1
echo $eth

tant=".pcap"
for file in * 
    do
        if test -f $file
        then
          result1=$(echo "$file" | grep "${tant}")
          if [  "$result1" != "" ]
             then
             sudo tcpreplay -i $eth -M 1  $file 
             sleep 5;
          fi  
        fi
    done

