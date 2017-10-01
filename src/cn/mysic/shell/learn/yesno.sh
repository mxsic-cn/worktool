#!/usr/bin/env bash
# test case
# scriptname:yesno.sh
#
echo -n "Do you wish to proceed[y/n]:"
read ans
case $ans in
    y|Y|yes|YES|Yes)
     echo "yes is selected"
        ;;
    n|N|No|NO|no)
      echo "no is selected"
        ;;
    *)
        echo "`basename $0`:Unkown response"
        exit 1;
        ;;
esac