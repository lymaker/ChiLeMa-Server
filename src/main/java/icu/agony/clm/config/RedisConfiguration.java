package icu.agony.clm.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;


@SpringBootConfiguration
public class RedisConfiguration {

    private final GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

    @Bean
    RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.setDefaultSerializer(serializer);
        return redisTemplate;
    }

    @Bean
    RedisCacheManagerBuilderCustomizer serializeCustomize(CacheProperties cacheProperties) {
        return builder -> builder.cacheDefaults(
            RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .disableCachingNullValues()
                .computePrefixWith(key -> cacheProperties.getRedis().getKeyPrefix() + key + ":")
        );
    }

}
