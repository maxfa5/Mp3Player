package org.example.Mp3Player.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.Mp3Player.DTO.PlaylistDTO;

import java.util.*;


@ToString(exclude = "songs")
@Data
@Entity
@Table(name = "playlists",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
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
    private Set<Song> songs;


    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public Playlist() {
        this.songs = new LinkedHashSet<>(); // Инициализация в конструкторе
    }

    public Song get(int currentTrackIndex) {
        return songs.stream().toList().get(currentTrackIndex);
    }

    public int size() {
        return songs.size();
    }

    public List<Song> getSongByName(String name) {
        List<Song> result= new ArrayList<Song>();
        for (Song song : songs) {
            if (song.getTitle().equals(name)) {
                result.add(song);
            }
        }
        return result;
    }
}


