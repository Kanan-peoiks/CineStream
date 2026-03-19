package com.example.cinestream.graphql;

import com.example.cinestream.media.Media;
import com.example.cinestream.media.MediaService;
import com.example.cinestream.media.MediaType;
import com.example.cinestream.review.Review;
import com.example.cinestream.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MediaGraphQLController {

    private final MediaService mediaService;
    private final ReviewService reviewService;

    @QueryMapping
    public List<Media> medias() {
        return mediaService.getAll();
    }

    @QueryMapping
    public Media media(@Argument Long id) {
        mediaService.increaseViewCount(id);
        return mediaService.getById(id);
    }

    @QueryMapping
    public List<Media> topViewed(@Argument int limit) {
        return mediaService.topViewed(limit);
    }

    @MutationMapping
    public Media addMedia(@Argument String title,
                          @Argument MediaType type,
                          @Argument List<String> castMembers) {
        return mediaService.addMedia(title, type, castMembers);
    }

    @SchemaMapping(typeName = "Media", field = "recentReviews")
    public List<Review> recentReviews(Media media, @Argument int limit) {
        return reviewService.getRecentReviews(media.getId(), limit);
    }
}