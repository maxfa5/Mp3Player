package org.example.Mp3Player.controller;

import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.SongRepository;
import org.example.Mp3Player.service.Mp3PlayerService;
import org.example.Mp3Player.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
public class WebController {

    @GetMapping("/ByName/{name}")
    public Song getSongByTitle(@PathVariable String name) {
        System.out.println( "Получена песня:" + name);
        return  songsService.SongfindByTitle(name);
    }

    @GetMapping("/song{id}")
    @ResponseBody
    public ResponseEntity<Song> getSongById(@PathVariable String id) {
        System.out.println( "Получена песня №" +id);
        try {
            return new ResponseEntity<>(songsService.findById(Long.valueOf(id)), HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(songsService.findById(Long.valueOf(id)), HttpStatus.BAD_REQUEST);
        }
    }

    private final Mp3PlayerService mp3PlayerService;
    private final SongsService songsService;

    @Autowired
    public WebController(SongRepository songRepository, Mp3PlayerService mp3PlayerService, SongsService songsService) {
        this.mp3PlayerService = mp3PlayerService;
        this.songsService = songsService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        System.out.println("Добавленна песня:" + song.toString());
        Optional<Song> savedSong = songsService.addSong(song);

        // Return 201 with the saved song
        // Return 500 if saving failed
        return savedSong.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

            @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongById(@PathVariable Long id) {
                songsService.deleteSongById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/Bytitle/{title}")
    public ResponseEntity<Void> deleteSongByTitle(@PathVariable String title) throws Exception {
//        try {
        songsService.deleteSongByTitle(title);
            System.out.println("Song" + title + "was delete");
            return ResponseEntity.ok().build();
//        }catch (Exception e) {
//            System.out.println("Song" + title + "wasn`t delete");
//            return ResponseEntity.status(500).build(); //?
//        }
    }

//    @Override
//    public void run(String... args) {
////        if (System.console() == null) {
////            System.err.println("Консольный ввод недоступен.");
////            return;
////        }
//        try {
//            while (true) {
//                printMenu();
//                int choice = readChoice();
//
//                switch (choice) {
//                    case 1 -> showSongs();
//                    case 2 -> playSong();
//                    case 3 -> {
//                        System.out.println("Выход из приложения...");
//                        return;
//                    }
//                    default -> System.out.println("Неверный выбор!");
//                }
//            }
//        } finally {
//
//        }
//    }
//
//    private void printMenu() {
//        System.out.println("\nВыберите действие:");
//        System.out.println("1. Список песен");
//        System.out.println("2. Воспроизвести песню");
//        System.out.println("3. Выйти");
//    }
//
//    private int readChoice() {
//        System.out.print("Введите номер действия: ");
//        while (true) {
//            try {
//                String input = reader.readLine().trim();
//
//                if (input.isEmpty()) {
//                    throw new InputMismatchException();
//                }
//
//                int choice = Integer.parseInt(input);
//
//                if (choice >= 1 && choice <= 3) {
//                    return choice;
//                }
//
//                System.out.println("Неверный выбор. Введите число от 1 до 3.");
//            } catch (NumberFormatException | InputMismatchException e) {
//                System.out.println("Ошибка ввода. Пожалуйста    , введите целое число.");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    @GetMapping
    @ResponseBody
    private List<Song> showSongs() {
        List<Song> songs= songsService.getAllSongs();
        songs.forEach(s -> System.out.println(s.getId() + ". " + s.getTitle()));
        return songs;
    }
//
//    private void playSong() {
//        try {
//            System.out.print("Введите ID песни: ");
//            long songId = Long.parseLong(reader.readLine().trim());
//            Song song = songRepository.findById(songId).orElse(null);
//
//            if (song != null) {
//                mp3PlayerService.play(song.getFilePath());
//            } else {
//                System.out.println("Песня не найдена!");
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Некорректный ID песни!");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}