package org.example.Mp3Player.service;

import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.PlaylistRepository;
import org.example.Mp3Player.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        return playlistRepository.save(playlist);
    }

    public void addSongToPlaylist(Long playlistId, Long songId) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        Optional<Song> songOptional = songRepository.findById(songId);

        if (playlistOptional.isPresent() && songOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            Song song = songOptional.get();
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
        }
    }

    public Set<Song> getSongsInPlaylist(Long playlistId) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        return playlistOptional.map(Playlist::getSongs).orElse(null);
    }
}