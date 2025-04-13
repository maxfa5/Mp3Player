package org.example.Mp3Player.controller;



import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.ExitApplication;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.service.Mp3PlayerService;
import org.example.Mp3Player.service.PlaylistService;
import org.example.Mp3Player.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Controller
public class ConsoleController {

    private final ExitApplication exitApplication;
    private final PlaylistService playlistService;
    private final Mp3PlayerService mp3PlayerService;

    @Autowired
    public ConsoleController(PlaylistService playlistService, Mp3PlayerService mp3PlayerService, ExitApplication exitApplication) {
        this.playlistService = playlistService;
        this.mp3PlayerService = mp3PlayerService;
        this.exitApplication = exitApplication;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (mp3PlayerService.isPlaying()){
            System.out.print( "Сейчас играет:" +  mp3PlayerService.getCurrentTrack().toString());}
            System.out.println("Выберите действие:");
            System.out.println("1. Загрузить плейлист");
            System.out.println("2. Воспроизвести текущий трек");
            System.out.println("3. Следующий трек :");
            System.out.println("4. Предыдущий трек");
            System.out.println("5. Остановить проигрывание");
            System.out.println("6. Найти песню по названию");
            System.out.println("7. Добавить песню в плейлист");
            System.out.println("8. Загрузить песню");
            System.out.println("9. Добавить плейлист");
            System.out.println("10. Открыть плейлист");

            System.out.println("6. Выйти");
            System.out.println("6. Выйти");

            System.out.println("99. Выйти");

            int choice = scanner.nextInt();
            boolean is_change = true;
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    playlistService.printAllPlaylists();
                    System.out.println();
                    System.out.println("Введите ID плейлиста:");
                    Long playlistId = scanner.nextLong();
                    scanner.nextLine(); // Очистка буфера
                    try {
                        Playlist playlist = playlistService.getPlaylistWithSongs(playlistId);
                        mp3PlayerService.setCurrentPlaylist(playlist);
                        System.out.println("Плейлист загружен.");
                    }catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    Song currentSong = mp3PlayerService.getCurrentTrack();
                    if (currentSong != null) {
                        mp3PlayerService.play(currentSong.getFilePath());
                    } else {
                        System.out.println("Плейлист не загружен или пуст.");
                    }
                    break;
                case 3:
                    Song nextSong = mp3PlayerService.nextTrack();
                    if (nextSong != null) {
                        System.out.println("Переключение на следующий трек: " + nextSong.getTitle());
                        mp3PlayerService.play( nextSong.getFilePath());
                        System.out.println("Сеёчас играет: " + nextSong.getTitle());
                    } else {
                        System.out.println("Плейлист не загружен или пуст.");
                    }
                    break;
                case 4:
                    Song previousSong = mp3PlayerService.previousTrack();
                    if (previousSong != null) {
                        System.out.println("Переключение на предыдущий трек: " + previousSong.getTitle());
                        mp3PlayerService.play(previousSong.getFilePath());
                    } else {
                        System.out.println("Плейлист не загружен или пуст.");
                    }
                    break;
                case 5:
                    System.out.println("Остановка текущего трека: " + mp3PlayerService.getCurrentTrack().getTitle());
                    mp3PlayerService.stop();
                    break;
                case 6:
                    System.out.println("Введите названия песни которую хотите найти");
                    String findName = scanner.nextLine();
                    try {
                        List<Song> songs= mp3PlayerService.findByName(findName);
                        for (Song song :songs){
                            System.out.println(song.toString());
                        }
                    }catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    System.out.println("Введите ID плейлиста:");
                    playlistId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Введите ID песни:");
                    Long songId = scanner.nextLong();
                    scanner.nextLine();
                    playlistService.addSongToPlaylist(playlistId,songId);
                    break;
                case 8:
                    System.out.println("Введите названия песни которую хотите добавить");
                    String newSongName = scanner.nextLine();
                    System.out.println("Введите названия Испоплителя новой песни");
                    String newAuthorName = scanner.nextLine();
                    try {
                        SongsService.addSong(newSongName, newAuthorName);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                }
                    break;
                case 9:{
                    System.out.println("Введите название плейлиста:");
                    String playlistTitle= scanner.nextLine();
                    playlistService.createPlaylist(playlistTitle);
                    break;}
                    case 99:{
                    System.out.println("Выход из приложения...");
                    exitApplication.exit();
                    break;}
                case 10:{
                    int mode;
                    System.out.println("Введите способ нахождения плейлиста:\n 0 - по имени\n1 - по ID\n");
                    mode = scanner.nextInt();
                    if (mode==1){
                        System.out.println("Введите ID плейлиста:");
                        playlistId = scanner.nextLong();
                        scanner.nextLine();
                        Playlist playlist =  playlistService.getPlaylistWithSongs(playlistId);
                        System.out.println(playlist.toString());
                        for (Song song : playlist.getSongs()){
                            System.out.println("\t" + song.toString());
                        }
                        System.out.println("Вывод песен закончен.");
                    }else if (mode==0){
                        scanner.nextLine();
                        System.out.println("Введите название плейлиста:");
                        String playlistTitle = scanner.nextLine();
                        try {
                            Playlist playlist =  playlistService.getPlaylistByName(playlistTitle);
                            System.out.println(playlist.toString());
                            for (Song song : playlist.getSongs()){
                                System.out.println(song.toString());
                            }
                            System.out.println("Вывод песен закончен.");
                        }catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    }else{
                        System.out.println("Выбран некоректный режим.");
                    }
                    break;}
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }
}