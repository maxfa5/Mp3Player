package org.example.Mp3Player.repository;

import org.example.Mp3Player.Model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Song findByTitle(String title);
}
