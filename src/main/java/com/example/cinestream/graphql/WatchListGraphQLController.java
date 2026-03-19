package com.example.cinestream.graphql;

import com.example.cinestream.watchlist.WatchListItem;
import com.example.cinestream.watchlist.WatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WatchListGraphQLController {

    private final WatchListService watchListService;

    @MutationMapping
    public WatchListItem addToWatchList(@Argument Long mediaId, Authentication authentication) {
        if (authentication == null
                || authentication instanceof AnonymousAuthenticationToken
                || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        return watchListService.addToWatchList(mediaId, authentication.getName());
    }

    @QueryMapping
    public List<WatchListItem> myWatchList(Authentication authentication) {
        if (authentication == null
                || authentication instanceof AnonymousAuthenticationToken
                || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        return watchListService.myWatchList(authentication.getName());
    }
}