package com.example.cinestream.media;

import com.example.cinestream.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final RedisService redisService;

    public Media addMedia(String title, MediaType type, List<String> castMembers) {
        Media media = new Media();
        media.setTitle(title);
        media.setType(type);
        media.setCastMembers(castMembers == null ? new ArrayList<>() : castMembers);
        return mediaRepository.save(media);
    }

    public List<Media> getAll() {
        return mediaRepository.findAll();
    }

    @Cacheable(value = "mediaById", key = "#id") //REDIS HISSESI
    public Media getById(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));
    }

    public void increaseViewCount(Long mediaId) {
        redisService.incrementView(mediaId);
    }

    public List<Media> topViewed(int limit) {
        List<Long> ids = redisService.getTopViewedIds(limit);

        if (ids.isEmpty()) {
            return List.of();
        }

        List<Media> all = mediaRepository.findAllById(ids);
        Map<Long, Media> map = all.stream()
                .collect(Collectors.toMap(Media::getId, m -> m));

        List<Media> ordered = new ArrayList<>();
        for (Long id : ids) {
            Media media = map.get(id);
            if (media != null) {
                ordered.add(media);
            }
        }
        return ordered;
    }

    public void updateAverageRating(Media media, double avg) {
        media.setAverageRating(avg);
        mediaRepository.save(media);
    }
}