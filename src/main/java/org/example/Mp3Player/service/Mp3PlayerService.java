package org.example.Mp3Player.service;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class Mp3PlayerService {

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
    public Song findById(Long id){
            return songRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Песня не найдена"));
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

        filePath = "music/" + filePath;
        File file = new File(filePath);
        System.out.println("Attempting to play from: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
//        try {
//            startPlayback(file.getAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
//
//    private Process aplayProcess;
//
//    public void startPlayback(String audioFile) throws IOException {
//        ProcessBuilder processBuilder = new ProcessBuilder("aplay", audioFile);
//        aplayProcess = processBuilder.start();
//    }
//
//    public void stopPlayback() throws IOException, InterruptedException {
//        if (aplayProcess != null && aplayProcess.isAlive()) {
//            // Attempt a graceful shutdown first (send SIGINT)
//            aplayProcess.destroy();
//
//            // Wait a short time to allow the process to terminate gracefully
//            boolean terminated = aplayProcess.waitFor(2, java.util.concurrent.TimeUnit.SECONDS);
//
//            // If the process still hasn't terminated, force it to terminate
//            if (!terminated) {
//                aplayProcess.destroyForcibly();
//                aplayProcess.waitFor(); // Wait for forced termination
//            }
//        }
//    }
}