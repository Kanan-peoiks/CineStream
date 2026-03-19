package com.example.cinestream.watchlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchListRepository extends JpaRepository<WatchListItem, Long> {
    List<WatchListItem> findByUser_UsernameOrderByAddedAtDesc(String username);

    Optional<WatchListItem> findByUser_UsernameAndMedia_Id(String username, Long mediaId);
}