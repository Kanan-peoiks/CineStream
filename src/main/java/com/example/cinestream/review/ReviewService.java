package com.example.cinestream.review;

import com.example.cinestream.media.Media;
import com.example.cinestream.media.MediaRepository;
import com.example.cinestream.media.MediaService;
import com.example.cinestream.user.User;
import com.example.cinestream.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final MediaService mediaService;

    @Transactional
    public Review addReview(Long mediaId, int rating, String text, String username) {
        if (rating < 1 || rating > 10) {
            throw new RuntimeException("Rating must be between 1 and 10");
        }

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (reviewRepository.findByMedia_IdAndAuthor_Username(mediaId, username).isPresent()) {
            throw new RuntimeException("You already reviewed this media");
        }

        Review review = new Review();
        review.setMedia(media);
        review.setAuthor(user);
        review.setRating(rating);
        review.setText(text);

        Review saved = reviewRepository.save(review);

        Double avg = reviewRepository.getAverageRatingByMediaId(mediaId);
        mediaService.updateAverageRating(media, avg);

        return saved;
    }

    public List<Review> getRecentReviews(Long mediaId, int limit) {
        return reviewRepository.findByMedia_IdOrderByCreatedAtDesc(mediaId)
                .stream()
                .limit(limit)
                .toList();
    }
}