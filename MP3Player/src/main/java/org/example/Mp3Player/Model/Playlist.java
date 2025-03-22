package org.example.Mp3Player.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "playlist_song", // Название связующей таблицы
            joinColumns = @JoinColumn(name = "playlist_id"), // Столбец для плейлиста
            inverseJoinColumns = @JoinColumn(name = "song_id") // Столбец для песни
    )
    private Set<Song> songs = new HashSet<>();


}
