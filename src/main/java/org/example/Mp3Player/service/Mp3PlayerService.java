package org.example.Mp3Player.service;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.Data;
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
@Data
public class Mp3PlayerService {

    private Playlist currentPlaylist;
    private int currentTrackIndex = -1;

    public  void  setCurrentPlaylist(Playlist playlist) {
        this.currentPlaylist = playlist;
        this.currentTrackIndex = 0;
    }

    public Song getCurrentTrack() {
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            return null;
        }
        return currentPlaylist.get(currentTrackIndex);
    }
    public Song nextTrack() {
        System.out.println(currentPlaylist +" "+ currentPlaylist.getSongs());
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            return null;
        }
        currentTrackIndex = (currentTrackIndex + 1) % currentPlaylist.size();
        return getCurrentTrack();
    }

    public Song previousTrack() {
        if (currentPlaylist == null || currentPlaylist.isEmpty()) {
            return null;
        }
        currentTrackIndex = (currentTrackIndex - 1 + currentPlaylist.size()) % currentPlaylist.size(); // Переход к предыдущему треку
        return getCurrentTrack();
    }

    private Player player;
    private boolean isPlaying = false;

    public boolean isPlaying() {

        if (isPlaying && player.isComplete()){
            isPlaying = false;
        }
        return isPlaying;
    }

    public FileInputStream createFileInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }

    public Player createPlayer(FileInputStream stream) throws JavaLayerException {
        return new Player(stream);
    }

    public List<Song> findByName(String name) {
        return currentPlaylist.getSongByName(name);
    }

    public void play(String filePath) {
        stop();
        filePath = "music/" + filePath;
        try {
            FileInputStream fileInputStream = createFileInputStream(filePath);
            player = createPlayer(fileInputStream);
            isPlaying = true;
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    System.err.println("Ошибка при воспроизведении файла: " + e.getMessage());
                } finally {
                    isPlaying = false;
                }
            }).start();
        } catch (FileNotFoundException | JavaLayerException e) {
            System.err.println("Ошибка при воспроизведении файла: " + e.getMessage());
        }
    }

    public void stop() {
        if (player != null) {
            isPlaying = false;
            player.close();
        }
    }

    public void printMenu() {
        if (isPlaying()){
            System.out.print( "Сейчас играет:" + getCurrentTrack().toString());}
        System.out.println("Выберите действие:");
        System.out.println("1. Загрузить плейлист");
        System.out.println("2. Повторить текущий трек");
        System.out.println("3. Следующий трек :");
        System.out.println("4. Предыдущий трек");
        System.out.println("5. Остановить проигрывание");
        System.out.println("6. Найти песню по названию");
        System.out.println("7. Добавить песню в плейлист");
        System.out.println("8. Загрузить песню");
        System.out.println("9. Добавить плейлист");
        System.out.println("10. Просмотреть плейлист");
        System.out.println("11. Просмотреть все треки");
        System.out.println("12. Удалить трек");
        System.out.println("13. Удалить плейлист");
        System.out.println("14. Показать все плейлисты");
        System.out.println("15. Удалить трек из плейлиста");
        System.out.println("6. Выйти");

        System.out.println("99. Выйти");
    }

}