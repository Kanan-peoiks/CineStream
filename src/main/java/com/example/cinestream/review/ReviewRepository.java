package com.example.cinestream.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMedia_IdOrderByCreatedAtDesc(Long mediaId);

    List<Review> findTop10ByMedia_IdOrderByCreatedAtDesc(Long mediaId);

    Optional<Review> findByMedia_IdAndAuthor_Username(Long mediaId, String username);

    @Query("select coalesce(avg(r.rating), 0) from Review r where r.media.id = :mediaId")
    Double getAverageRatingByMediaId(@Param("mediaId") Long mediaId);
}