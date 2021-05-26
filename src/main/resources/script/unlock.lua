if (redis.call('exists',KEYS[1]) == 0)
then
    return 0;
end
if (redis.call('hexists',KEYS[1],ARGV[1]) == 0)
then
    return 0;
end
if (redis.call('hexists',KEYS[1],ARGV[1]) == 1)
then
    redis.call('del',KEYS[1]);
    return 1;
end
return 0;