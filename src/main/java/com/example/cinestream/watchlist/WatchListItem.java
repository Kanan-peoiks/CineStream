package com.example.cinestream.watchlist;

import com.example.cinestream.media.Media;
import com.example.cinestream.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;

@Entity
@Table(
        name = "watch_list_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "media_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status = "PLAN_TO_WATCH";

    @Column(nullable = false)
    private Instant addedAt = Instant.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;
}