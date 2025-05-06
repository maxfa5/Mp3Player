package org.example.Mp3Player.controller;



import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.ExitApplication;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.service.Mp3PlayerService;
import org.example.Mp3Player.service.PlaylistService;
import org.example.Mp3Player.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;


@Controller
public class ConsoleController {    private final Scanner scanner;


    private final ExitApplication exitApplication;
    private final PlaylistService playlistService;
    private final Mp3PlayerService mp3PlayerService;
    private final SongsService songsService;

    @Autowired
    public ConsoleController(PlaylistService playlistService,SongsService songsService, Mp3PlayerService mp3PlayerService, ExitApplication exitApplication) {
        this.playlistService = playlistService;
        this.mp3PlayerService = mp3PlayerService;
        this.exitApplication = exitApplication;
        this.songsService = songsService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            mp3PlayerService.printMenu();
            int choice = -1;
            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
            boolean is_change = true;
            scanner.nextLine();

            switch (choice) {
                case 1:
                {
                    Long playlistId;
                    playlistService.printAllPlaylists();
                    System.out.println();
                    int mode = selectSearchMode("плейлиста");
                    if (mode == 1) {
                        System.out.println("Введите ID плейлиста:");
                        playlistId = scanner.nextLong();
                        scanner.nextLine();
                        try {
                            Playlist playlist = playlistService.getPlaylistWithSongs(playlistId);
                            mp3PlayerService.setCurrentPlaylist(playlist);
                            System.out.println("Плейлист выбран.");
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (mode == 0) {
                        scanner.nextLine();
                        System.out.println("Введите название плейлиста:");
                        String playlistTitle = scanner.nextLine();
                        try {
                            Playlist playlist = playlistService.getPlaylistByName(playlistTitle);
                            mp3PlayerService.setCurrentPlaylist(playlist);
                            System.out.println("Плейлист выбран.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
                case 2:
                    Song currentSong = mp3PlayerService.getCurrentTrack();
                    if (currentSong != null) {
                        mp3PlayerService.play(currentSong.getFilePath());
                    } else {
                        System.out.println("Плейлист не загружен, пуст или песня не выбранна.");
                    }
                    break;
                case 3:{
                    Song nextSong = mp3PlayerService.nextTrack();
                    if (nextSong != null) {
                        System.out.println("Переключение на следующий трек: " + nextSong.getTitle());
                        mp3PlayerService.play(nextSong.getFilePath());
                    } else {
                        System.out.println("Плейлист не загружен или пуст.");
                    }
                    break;}
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
                    if(mp3PlayerService.isPlaying()){
                        System.out.println("Остановка текущего трека: " + mp3PlayerService.getCurrentTrack().getTitle());
                        mp3PlayerService.stop();
                    }else {
                        System.out.println("Трек не запущен!");
                    }
                    break;
                case 6:
                    System.out.println("Введите названия песни которую хотите найти");
                    String findName = scanner.nextLine();
                    try {
                        Optional<Song> song = songsService.SongfindByTitle(findName);
                        if (song.isPresent()) {
                            System.out.println(song.toString());
                        } else {
                            System.out.println("Песня не найдена");
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:{
                    Long playlistId;
                    System.out.println("Введите ID плейлиста:");
                    playlistId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Введите ID песни:");
                    Long songId = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        playlistService.addSongToPlaylist(playlistId, songId);
                    }catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Песня добавленна успешно!");
                    break;}
                case 8:
                    System.out.println("Введите названия песни которую хотите добавить");
                    String newSongName = scanner.nextLine();
                    System.out.println("Введите названия Испоплителя новой песни");
                    String newAuthorName = scanner.nextLine();
                    try {
                        SongsService.addSong(newSongName, newAuthorName);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 9: {
                    System.out.println("Введите название плейлиста:");
                    String playlistTitle = scanner.nextLine();
                    try {
                        playlistService.createPlaylist(playlistTitle);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 99: {
                    System.out.println("Выход из приложения...");
                    exitApplication.exit();
                    break;
                }
                case 10: {
                    Long playlistId;
                    int mode = selectSearchMode("плейлиста");
                    if (mode == 1) {
                        System.out.println("Введите ID плейлиста:");
                        playlistId = scanner.nextLong();
                        scanner.nextLine();
                        try {
                            Playlist playlist = playlistService.getPlaylistWithSongs(playlistId);
                            System.out.println(playlist.toString());
                            for (Song song : playlist.getSongs()) {
                                System.out.println("\t" + song.toString());
                            }
                            System.out.println("Вывод песен закончен.");
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (mode == 0) {
                        System.out.println("Введите название плейлиста:");
                        String playlistTitle = scanner.nextLine();
                        try {
                            Playlist playlist = playlistService.getPlaylistByName(playlistTitle);
                            System.out.println(playlist.toString());
                            for (Song song : playlist.getSongs()) {
                                System.out.println(song.toString());
                            }
                            System.out.println("Вывод песен закончен.");
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
                case 11:
                    songsService.getAllSongs().stream().map(Song::toString).forEach(System.out::println);
                    break;
                case 12: {
                    int mode = selectSearchMode("трека");
                    if (mode == 1) {
                        System.out.println("Введите ID трека:");
                        Long songID = scanner.nextLong();
                        scanner.nextLine();
                        try {
                            songsService.findById(songID);
                            songsService.deleteSongById(songID);
                            System.out.println("Песня удалена успешно!");
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (mode == 0) {
                        System.out.println("Введите название трека:");
                        String songTitle = scanner.nextLine();
                        try {
                            songsService.deleteSongByTitle(songTitle);
                            System.out.println("Песня удалена успешно.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
                case 13: {
                    int mode = selectSearchMode("плейлиста");
                    if (mode == 1) {
                        System.out.println("Введите ID плейлиста:");
                        Long songID = scanner.nextLong();
                        scanner.nextLine();
                        try {
                            playlistService.findById(songID);
                            playlistService.deletePlaylistById(songID);
                            System.out.println("Плейлист удален успешно!");
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (mode == 0) {
                        System.out.println("Введите название плейлиста:");
                        String playlistTitle = scanner.nextLine();
                        try {
                            playlistService.deletePlaylistByName(playlistTitle);
                            System.out.println("Плейлист удалён успешно.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;}
                case 14:{
                    playlistService.printAllPlaylists();
                    break;}
                case 15:{
                    if (mp3PlayerService.getCurrentPlaylist() == null) {
                        System.out.println("Плейлист не устанновлен.Утсановите текущий плейлист и попробуйте снова");
                        break;
                    }
                    System.out.println("Введите ID трека:");
                    Long songID = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        if(playlistService.removeSongFromPlaylistById(mp3PlayerService.getCurrentPlaylist().getId(),songID)){
                        System.out.println("Песня удалена успешно!");}
                        else{
                            System.out.println("Ошибка удаления песни из плейлиста!");
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }break;}

                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

    private int selectSearchMode(String entityType) {
        System.out.printf("Введите способ нахождения %s:\n0 - по имени\n1 - по ID\n", entityType);
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: введите число 0 или 1");
            scanner.nextLine(); // Очистка буфера
            return -1;
        }
    }
}