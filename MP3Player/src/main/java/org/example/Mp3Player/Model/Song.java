package org.example.Mp3Player.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column
    private Long artist;
    @Column
    private String filePath;

    @ManyToMany(mappedBy = "songs")
    private Set<Playlist> playlists = new HashSet<>();
}