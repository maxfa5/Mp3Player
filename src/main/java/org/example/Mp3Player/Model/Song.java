package org.example.Mp3Player.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.Mp3Player.service.SongsService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="song",
        uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Song(String title, String artist) throws IOException {

        this.title = title;
        this.artist = artist;
        filePath = SongsService.createEmptyMp3File(title);
        filePath = filePath.substring(filePath.lastIndexOf("/")+1);
    }
    @Column
    private String title;
    @Column
    private String artist;
    @Column
    private String filePath;

    @ManyToMany(mappedBy = "songs")
    @JsonBackReference
    @ToString.Exclude // Предотвращает StackOverflowError при генерации toString
    @EqualsAndHashCode.Exclude // Предотвращает StackOverflowError при генерации equals/hashCode
    private Set<Playlist> playlists = new HashSet<>();
}