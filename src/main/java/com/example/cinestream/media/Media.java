package com.example.cinestream.media;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MediaType type;

    private Double averageRating = 0.0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "media_cast_members", joinColumns = @JoinColumn(name = "media_id"))
    @Column(name = "cast_member")
    private List<String> castMembers = new ArrayList<>();
}