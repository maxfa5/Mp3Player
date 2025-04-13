package org.example.Mp3Player.DTO;

import lombok.Data;
import org.example.Mp3Player.Model.Song;

@Data
public class SongDTO {

    private Long id;
    private String title;
    private String artist;
    private String filePath;

    public SongDTO(Song song) {
        this.id = song.getId();
        this.title = song.getTitle();
        this.artist = song.getArtist();
        this.filePath = song.getFilePath();
    }
}
