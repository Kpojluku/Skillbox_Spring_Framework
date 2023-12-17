package ru.goltsov.education.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnProperty(prefix = "app.redis", name = "enabled", havingValue = "true")
public class RedisConfig {

    /**
     * Создает и конфигурирует фабрику подключения LettuceConnectionFactory на основе настроек Redis.
     *
     * @param redisProperties объект, содержащий настройки подключения к Redis
     * @return экземпляр LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisProperties redisProperties){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        configuration.setHostName(redisProperties.getHost());
        configuration.setPort(redisProperties.getPort());

        return new LettuceConnectionFactory(configuration);
    }

    /**
     * Создает и конфигурирует RedisTemplate на основе фабрики подключения LettuceConnectionFactory.
     *
     * @param lettuceConnectionFactory фабрика подключения LettuceConnectionFactory
     * @return экземпляр RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }
}
