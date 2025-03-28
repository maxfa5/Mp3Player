package org.example.Mp3Player.service;

import jakarta.transaction.Transactional;
import org.example.Mp3Player.DTO.PlaylistDTO;
import org.example.Mp3Player.DTO.SongDTO;
import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.PlaylistRepository;
import org.example.Mp3Player.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    private Playlist currentPlaylist;
    private int currentTrackIndex = -1;

    public  void  setCurrentPlaylist(Playlist playlist) {
        this.currentPlaylist = playlist;
        this.currentTrackIndex = 0; // Начинаем с первого трека
    }

    public Song getCurrentTrack() {
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            return null;
        }
        return currentPlaylist.get(currentTrackIndex);
    }

    public Song nextTrack() {
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            return null;
        }
        currentTrackIndex = (currentTrackIndex + 1) % currentPlaylist.size(); // Переход к следующему треку
        return getCurrentTrack();
    }

    public Song previousTrack() {
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            return null;
        }
        currentTrackIndex = (currentTrackIndex - 1 + currentPlaylist.size()) % currentPlaylist.size(); // Переход к предыдущему треку
        return getCurrentTrack();
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

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

    @Transactional
    public Playlist getPlaylistWithSongs(Long id) {
        Playlist playlist = playlistRepository.findByIdWithSongs(id)
                .orElseThrow(() -> new RuntimeException("Плейлист не найден: " + id));

        return (playlist);
    }


    public  PlaylistDTO convertToDto(Playlist playlist) {
        PlaylistDTO dto = new PlaylistDTO(playlist);
//        dto.setId(playlist.getId());
//        dto.setName(playlist.getName());
//
//        Set<SongDTO> songDTOs = playlist.getSongs().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toSet());
//        dto.setSongs(songDTOs);
//        // Теперь песни должны быть загружены
        return dto;
    }


    public SongDTO convertToDTO(Song song) {
        return new SongDTO(song);
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