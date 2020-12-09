if redis.call('hexists',KEYS[1],ARGV[1]) == 1
then
    redis.call('del',KEYS[1]);
    return 1;
else
    return 0;
end