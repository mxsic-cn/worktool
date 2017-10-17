#!/usr/bin/env bash
# ScriptName:greet.sh
# usage:greet.sh Tom John Anndy

echo "==using  \$*=="
for name in $* # same as for name in $@
do
    echo Hi $name
done

echo "==using  \$@=="

for name in $@ #sname as for name in $*
do echo Hi $name
done

echo '==using "$*" =='

for name in "$*"
do echo Hi $name
done

echo '==using "$@"=='
for name in "$@"
do echo Hi $name
done
