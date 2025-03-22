package org.example.Mp3Player.repository;

import org.example.Mp3Player.Model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Song findByTitle(String title);
}
