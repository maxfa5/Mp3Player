package org.example.Mp3Player.controller;



import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.service.Mp3PlayerService;
import org.example.Mp3Player.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;


@Controller
public class ConsoleController {

    private final PlaylistService playlistService;
    private final Mp3PlayerService mp3PlayerService;

    @Autowired
    public ConsoleController(PlaylistService playlistService, Mp3PlayerService mp3PlayerService) {
        this.playlistService = playlistService;
        this.mp3PlayerService = mp3PlayerService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Загрузить плейлист");
            System.out.println("2. Воспроизвести текущий трек");
            System.out.println("3. Следующий трек");
            System.out.println("4. Предыдущий трек");
            System.out.println("5. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    System.out.println("Введите ID плейлиста:");
                    Long playlistId = scanner.nextLong();
                    scanner.nextLine(); // Очистка буфера
                    Playlist playlist = playlistService.getPlaylistWithSongs(playlistId);
                    playlistService.setCurrentPlaylist(playlist);
                    System.out.println("Плейлист загружен.");
                    break;
                case 2:
                    Song currentSong = playlistService.getCurrentTrack();
                    if (currentSong != null) {
                        mp3PlayerService.play(currentSong.getFilePath());
                    } else {
                        System.out.println("Плейлист не загружен или пуст.");
                    }
                    break;
                case 3:
                    Song nextSong = playlistService.nextTrack();
                    if (nextSong != null) {
                        System.out.println("Переключение на следующий трек: " + nextSong.getTitle());
                        mp3PlayerService.play(nextSong.getFilePath());
                    } else {
                        System.out.println("Плейлист не загружен или пуст.");
                    }
                    break;
                case 4:
                    Song previousSong = playlistService.previousTrack();
                    if (previousSong != null) {
                        System.out.println("Переключение на предыдущий трек: " + previousSong.getTitle());
                        mp3PlayerService.play(previousSong.getFilePath());
                    } else {
                        System.out.println("Плейлист не загружен или пуст.");
                    }
                    break;
                case 5:
                    System.out.println("Выход из приложения...");
                    return;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }
}