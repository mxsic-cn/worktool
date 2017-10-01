#!/usr/bin/env bash
dirname="/usr/bin/local/bin";
echo "dirname=$dirname"
echo -n '${#dirname}='; sleep 0.1; echo "${#dirname}"
echo
echo -n '${dirname:4}=';sleep 0.1; echo "${dirname:4}"
echo
echo -n '${dirname:8:6}='; sleep 0.1; echo "${dirname:8:6}"
echo
echo -n '${dirname#*bin}='; sleep 0.1; echo "${dirname#*bin}"
echo
echo -n '${dirname##*bin}='; sleep 0.1; echo "${dirname##*bin}"
echo
echo -n  '${dirname%*bin}='; sleep 0.1; echo "${dirname%*bin}"
echo
echo -n '${dirname%%*bin}='; sleep 0.1; echo "${dirname%%*bin}"
echo
echo -n '${dirname/bin/sbin}=';sleep 0.1; echo "${dirname/bin/sbin}"
echo
echo -n '${dirname//bin/lib}=';sleep 0.1; echo "${dirname//bin/lib}"
echo
echo -n '${dirname/bin*/lib}=';sleep 0.1; echo "${dirname/bin*/lib}"
