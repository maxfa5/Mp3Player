package org.example.Mp3Player.DTO;

import lombok.Data;
import org.example.Mp3Player.Model.Playlist;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PlaylistDTO {
    private Long id;
    private String name;
    private Set<SongDTO> songs;

    public PlaylistDTO(Playlist playlist) {
        this.id = playlist.getId();
        this.name = playlist.getName();
        this.songs = playlist.getSongs().stream()
                .map(SongDTO::new)
                .collect(Collectors.toSet());
    }
}