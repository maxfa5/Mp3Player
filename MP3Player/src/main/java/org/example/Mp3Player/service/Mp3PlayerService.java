package org.example.Mp3Player.service;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class Mp3PlayerService {

    @Autowired
    private SongRepository songRepository;

    public Song addSong(Song song) {
        return songRepository.save(song);
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
    public void play(String filePath) {
        stop(); // Остановить текущее воспроизведение
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            player = new Player(fileInputStream);
            isPlaying = true;
            System.out.println("Воспроизведение: " + filePath);
            try {
                player.play();
            } catch (JavaLayerException e) {
                System.err.println("Ошибка при воспроизведении файла: " + e.getMessage());
            }

        } catch (FileNotFoundException | JavaLayerException e) {
            System.err.println("Ошибка при воспроизведении файла: " + e.getMessage());
        }
    }

    public void stop() {
        if (player != null) {
            player.close();
            isPlaying = false;
            System.out.println("Воспроизведение остановлено.");
        }
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

}