package org.example.Mp3Player.controller;

import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.SongRepository;
import org.example.Mp3Player.service.Mp3PlayerService;
import org.example.Mp3Player.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {


    @PostMapping("/{playlistId}/add-song/{songId}")
    public Playlist addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        return playlistService.addSongToPlaylist(playlistId, songId);
    }

    @PostMapping("/{playlistId}/remove-song/{songId}")
    public Playlist removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        return playlistService.removeSongFromPlaylist(playlistId, songId);
    }


    @GetMapping("/ByName/{name}")
    public Playlist getPlaylistByName(@PathVariable String name) {
        return playlistService.getPlaylistByName(name);
    }

    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable Long id) {
        return playlistService.getPlaylistById(id);
    }

    @Autowired
    private PlaylistService playlistService;

    @PostMapping
    public Playlist createPlaylist(@RequestParam String name) {
        return playlistService.createPlaylist(name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylistById(Long id) {
        playlistService.deletePlaylistById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<Void> deletePlaylistByName(@PathVariable String name) {
        playlistService.deletePlaylistByName(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }
}