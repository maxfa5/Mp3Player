package org.example.Mp3Player.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference
    @ToString.Exclude // Предотвращает StackOverflowError при генерации toString
    @EqualsAndHashCode.Exclude // Предотвращает StackOverflowError при генерации equals/hashCode
    private Set<Playlist> playlists = new HashSet<>();
}