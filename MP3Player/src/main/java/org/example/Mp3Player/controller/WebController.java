package org.example.Mp3Player.controller;

import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.SongRepository;
import org.example.Mp3Player.service.Mp3PlayerService;
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
        return  mp3PlayerService.SongfindByTitle(name);
    }

    @GetMapping("/song{id}")
    @ResponseBody
    public Optional<Song> getSongById(@PathVariable String id) {
        System.out.println( "Получена песня №" +id);
        return  mp3PlayerService.findById(Long.valueOf(id));
    }

    private final Mp3PlayerService mp3PlayerService;

    @Autowired
    public WebController(SongRepository songRepository, Mp3PlayerService mp3PlayerService) {
        this.mp3PlayerService = mp3PlayerService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        System.out.println("Добавленна песня:" + song.toString());
        Optional<Song> savedSong = mp3PlayerService.addSong(song);

        if (savedSong.isPresent()) {
            return new ResponseEntity<>(savedSong.get(), HttpStatus.CREATED); // Return 201 with the saved song
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 if saving failed
        }
    }

            @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongById(@PathVariable Long id) {
        mp3PlayerService.deleteSongById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/Bytitle/{title}")
    public ResponseEntity<Void> deleteSongByTitle(@PathVariable String title) throws Exception {
//        try {
            mp3PlayerService.deleteSongByTitle(title);
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
        List<Song> songs= mp3PlayerService.getAllSongs();
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