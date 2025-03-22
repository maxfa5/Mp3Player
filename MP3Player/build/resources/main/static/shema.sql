CREATE TABLE playlist (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255)
);

CREATE TABLE song (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      title VARCHAR(255),
                      file_path VARCHAR(255)
);

CREATE TABLE playlist_song (
                               playlist_id BIGINT,
                               song_id BIGINT,
                               PRIMARY KEY (playlist_id, song_id),
                               FOREIGN KEY (playlist_id) REFERENCES playlist(id),
                               FOREIGN KEY (song_id) REFERENCES song(id)
);