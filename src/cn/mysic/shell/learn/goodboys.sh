#!/usr/bin/env bash
# ScriptName:goodboys.sh
PS3="Please choose one of the three boys:"
select choice in tom dan guy
#select choice
do
 case $choice in
        tom)
         echo Tom is a cool dude!
         break;;  # break out of the select loop
        dan | guy)
         echo Dan and Guy are booth wonderful.
         break;;
        *)
          echo "$REPLY is not one of your choices"
          echo "Try again."
          ;;
  esac
done