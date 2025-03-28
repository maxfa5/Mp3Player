package org.example.Mp3Player.service;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class Mp3PlayerService {

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

    private Player player;
    private boolean isPlaying = false;
    public Song SongfindByTitle(String title){
        return songRepository.findByTitle(title);
    }
    public Optional<Song> findById(Long id){
        return songRepository.findById(id);
    }
    public List<Song> getAllSongs(){
        return songRepository.findAll();
    }


    public boolean isPlaying() {
        return isPlaying;
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



    public void play(String filePath) {
        stop(); // Остановить текущее воспроизведение
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            player = new Player(fileInputStream);
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    System.err.println("Ошибка при воспроизведении файла: " + e.getMessage());
                }
            }).start();
        } catch (FileNotFoundException | JavaLayerException e) {
            System.err.println("Ошибка при воспроизведении файла: " + e.getMessage());
        }
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
    }
}