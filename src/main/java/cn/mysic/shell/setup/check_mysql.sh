#!/usr/bin/env bash
if [ -z "$MYSQL_HOST" ]; then
    MYSQL_HOST='127.0.0.1'
fi

mysql_cmd="mysql -u cornerstone -pLipton305! -h ${MYSQL_HOST} -P 3306 --connect-timeout=5"
echo "select 1 from dual;" | $mysql_cmd > /dev/null 2>&1
ret=$?
exit $ret