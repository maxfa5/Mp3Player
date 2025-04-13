package org.example.Mp3Player.service;

import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class SongsService {

    private static SongRepository songRepository = null;
    private static String storageLocation;

    // Правильный конструктор с внедрением зависимостей
    public SongsService(
            SongRepository songRepository,
            @Value("${file.storage.location}") String storageLocation) {
        this.songRepository = songRepository;
        SongsService.storageLocation = storageLocation;
    }

    public static Optional<Song> addSong(String newSongName,String newAuthor) throws IOException {
    Song newSong = new Song(newSongName,newAuthor);
        return addSong(newSong);
    }
    public static Optional<Song> addSong(Song song) {
        try {
            Song savedSong = songRepository.save(song);
            System.out.println("Song saved successfully: " + savedSong);
            return Optional.of(savedSong);
        } catch (DataAccessException e) {
            System.err.println("Error saving song: " + song + ". Error details: " + e.getMessage());
            return Optional.empty(); // Возвращаем пустой Optional в случае ошибки
        }
    }

    /**
     * Генерирует уникальный путь к файлу в папке music
     * @param title исходное название файла (с расширением)
     * @return уникальный путь вида "music/название_1.mp3"
     */
    public static String genPath(String title) {
        // Извлекаем расширение файла
        String baseName = title.lastIndexOf('.') > 0
                ? title.substring(0, title.lastIndexOf('.'))
                : title;
        String extension = title.lastIndexOf('.') > 0
                ? title.substring(title.lastIndexOf('.'))
                : "";

        String filePath = "";
        int number = 1;
System.out.println(storageLocation);
        // Проверяем существование файлов и добавляем номер при необходимости
        do {
            System.out.println(filePath);
            filePath = (number == 1)
                    ? Paths.get(storageLocation, title).toString()
                    : Paths.get(storageLocation, baseName + "_" + number + extension).toString();
            number++;
        } while (new File(filePath).exists());

        return filePath;
    }

    public static String createEmptyMp3File(String title) throws IOException {
        String filePath = SongsService.genPath(title) + ".mp3";
        System.out.println(filePath);
        Path path = Paths.get(filePath);
        System.out.println(filePath);

        if (!Files.exists(path)) {
            // Создаем пустой файл с MP3-заголовком
            byte[] mp3Header = new byte[] {
                    0x49, 0x44, 0x33, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
                    // Это минимальный корректный заголовок MP3 файла
            };
            Files.write(path, mp3Header);
        }
        return filePath;
    }


    public void deleteSongById(Long id) {
        songRepository.deleteById(id);
    }

    public void deleteSongByTitle(String title) throws Exception {
        Optional<Song> songOptional = songRepository.findByTitle(title);
        Song song = songOptional.orElseThrow(() -> new Exception("Песня не найдена: " + title));
        songRepository.delete(song);
    }

    public List<Song> getAllSongs(){
        return songRepository.findAll();
    }

    public Optional<Song> SongfindByTitle(String title){
        return songRepository.findByTitle(title);
    }
    public Song findById(Long id){
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Песня не найдена"));
    }
}
