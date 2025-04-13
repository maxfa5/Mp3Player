package org.example.Mp3Player.service;

import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongsService {

    @Autowired
    private SongRepository songRepository;



    public Optional<Song> addSong(Song song) {
        try {
            Song savedSong = songRepository.save(song);
            System.out.println("Song saved successfully: " + savedSong);
            return Optional.of(savedSong);
        } catch (DataAccessException e) {
            System.err.println("Error saving song: " + song + ". Error details: " + e.getMessage());
            return Optional.empty(); // Возвращаем пустой Optional в случае ошибки
        }
    }


    public void deleteSongById(Long id) {
        songRepository.deleteById(id);
    }

    public void deleteSongByTitle(String title) throws Exception{
        Song song = songRepository.findByTitle(title);
        if (song == null) {
            throw  new RuntimeException("Песня не найдена: " + title);
        }
        songRepository.delete(song);
    }
    public List<Song> getAllSongs(){
        return songRepository.findAll();
    }

    public Song SongfindByTitle(String title){
        return songRepository.findByTitle(title);
    }
    public Song findById(Long id){
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Песня не найдена"));
    }
}
