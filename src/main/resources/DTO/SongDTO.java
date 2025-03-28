package org.example.Mp3Player.DTO;

import lombok.Data;

@Data
public class SongDTO {

    private Long id;
    private String title;
    private Long artist;
    private String filePath;
}
