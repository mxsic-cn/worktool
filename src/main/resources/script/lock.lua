if redis.call('exists', KEYS[1]) == 0
then
    redis.call('hset', KEYS[1], ARGV[1], 1);
    redis.call('expire', KEYS[1], ARGV[2]);
    return 1;
end
if redis.call('hexists', KEYS[1], ARGV[1]) == 1
then
    redis.call('hincrby', KEYS[1], ARGV[1], 1);
    redis.call('expire', KEYS[1], ARGV[2]);
    return 1;
end
return 0