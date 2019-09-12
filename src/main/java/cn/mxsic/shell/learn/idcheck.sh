#!/bin/bash
# ScriptName: idcheck.sh
# purpose:check user id to see if user is root.
# only root has a uid of 0
# Format for id output:uid=501(tt)gid=501(tt)groups=501(tt)
# root's uid=0 :uid=0(root)gid=0(root)groups=0(root)...
#
id=`id | awk -F'[=(]' '{print $2}'` # get userid
echo "your user id is:$id"
if [ $id -eq 0 ] # ((id==0))
then
    echo "you are superuser."
else
    echo "you are not superuser."
fi