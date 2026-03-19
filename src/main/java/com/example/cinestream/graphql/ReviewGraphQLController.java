package com.example.cinestream.graphql;

import com.example.cinestream.review.Review;
import com.example.cinestream.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReviewGraphQLController {

    private final ReviewService reviewService;

    @MutationMapping
    public Review addReview(@Argument Long mediaId,
                            @Argument int rating,
                            @Argument String text,
                            Authentication authentication) {

        if (authentication == null
                || authentication instanceof AnonymousAuthenticationToken
                || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        String username = authentication.getName();
        return reviewService.addReview(mediaId, rating, text, username);
    }
}