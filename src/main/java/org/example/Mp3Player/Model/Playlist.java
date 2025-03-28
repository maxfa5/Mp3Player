package org.example.Mp3Player.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.Mp3Player.DTO.PlaylistDTO;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinTable(
            name = "playlist_song", // Название связующей таблицы
            joinColumns = @JoinColumn(name = "playlist_id"), // Столбец для плейлиста
            inverseJoinColumns = @JoinColumn(name = "song_id") // Столбец для песни
    )
    private Set<Song> songs = new HashSet<>();


    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public Song get(int currentTrackIndex) {
        return songs.stream().toList().get(currentTrackIndex);
    }

    public int size() {
        return songs.size();
    }
}


