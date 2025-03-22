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

    public Playlist addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Плейлист не найден: " + playlistId));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Песня не найдена: " + songId));

        playlist.getSongs().add(song);
        return playlistRepository.save(playlist);
    }

    public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Плейлист не найден: " + playlistId));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Песня не найдена: " + songId));

        playlist.getSongs().remove(song);
        return playlistRepository.save(playlist);
    }

    @Autowired  
    public PlaylistService(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }
    public Playlist getPlaylistByName(String name) {
        return playlistRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Плейлист не найден: " + name));
    }

    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Плейлист не найден: " + id));
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        return playlistRepository.save(playlist);
    }



    public Set<Song> getSongsInPlaylist(Long playlistId) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        return playlistOptional.map(Playlist::getSongs).orElse(null);
    }

    public void deletePlaylistById(Long id) {
        playlistRepository.deleteById(id);
    }

    public void deletePlaylistByName(String name) {
        Playlist playlist = playlistRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Плейлист не найден: " + name));
        playlistRepository.delete(playlist);
    }


}