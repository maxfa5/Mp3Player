package org.example.Mp3Player.repository;

import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.Model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByName(String name);
}
