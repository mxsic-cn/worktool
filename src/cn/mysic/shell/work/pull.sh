#!/bin/sh
# name:pull.sh
# discription:this script will pull all the new version git file from origin with it's branch or the branch has been given
echo 'pulling...'

cd /Users/siqishangshu/workspace/code
echo 'cd to the code dir'

DIR=$(cd `dirname $0`; pwd)
echo $DIR

for file in ./*
do
    if test -d $file
    then
        echo $file 
	cd $file
	git branch;
	git status;
	git pull;
	cd $DIR
    fi
done
echo 'pull done'
