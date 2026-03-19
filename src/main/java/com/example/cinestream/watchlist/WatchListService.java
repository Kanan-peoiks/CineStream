package com.example.cinestream.watchlist;

import com.example.cinestream.media.Media;
import com.example.cinestream.media.MediaRepository;
import com.example.cinestream.user.User;
import com.example.cinestream.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;

    public WatchListItem addToWatchList(Long mediaId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        if (watchListRepository.findByUser_UsernameAndMedia_Id(username, mediaId).isPresent()) {
            throw new RuntimeException("Media already in watch list");
        }

        WatchListItem item = new WatchListItem();
        item.setUser(user);
        item.setMedia(media);
        item.setStatus("PLAN_TO_WATCH");

        return watchListRepository.save(item);
    }

    public List<WatchListItem> myWatchList(String username) {
        return watchListRepository.findByUser_UsernameOrderByAddedAtDesc(username);
    }
}