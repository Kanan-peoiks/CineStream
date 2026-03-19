package com.example.cinestream.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String TOP_VIEWED_KEY = "media:views";

    private final StringRedisTemplate stringRedisTemplate;

    public void incrementView(Long mediaId) {
        stringRedisTemplate.opsForZSet().incrementScore(TOP_VIEWED_KEY, String.valueOf(mediaId), 1);
    }

    public List<Long> getTopViewedIds(int limit) {
        Set<String> ids = stringRedisTemplate.opsForZSet()
                .reverseRange(TOP_VIEWED_KEY, 0, limit - 1);

        if (ids == null) {
            return List.of();
        }

        List<Long> result = new ArrayList<>();
        for (String id : ids) {
            result.add(Long.parseLong(id));
        }
        return result;
    }
}