package org.example.Mp3Player.controller;



import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.ExitApplication;
import org.example.Mp3Player.Model.Song;
import org.example.Mp3Player.repository.PlaylistRepository;
import org.example.Mp3Player.service.Mp3PlayerService;
import org.example.Mp3Player.service.PlaylistService;
import org.example.Mp3Player.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static org.assertj.core.api.Assertions.*;
import static org.example.Mp3Player.service.SongsService.songRepository;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


@Controller
public class ConsoleController {    private final Scanner scanner;


    private final ExitApplication exitApplication;
    private final PlaylistService playlistService;
    private final Mp3PlayerService mp3PlayerService;
    private final SongsService songsService;
    private final PlaylistRepository playlistRepository;
    @Autowired
    public ConsoleController(PlaylistService playlistService, SongsService songsService, Mp3PlayerService mp3PlayerService, ExitApplication exitApplication, PlaylistRepository playlistRepository) {
        this.playlistService = playlistService;
        this.mp3PlayerService = mp3PlayerService;
        this.exitApplication = exitApplication;
        this.songsService = songsService;
        this.playlistRepository = playlistRepository;
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
                        System.out.println(e.toString() + "NEN");
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
    void checkMp3Service(){
        try {
            cleanData();
            setUp();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        setCurrentPlaylist_ShouldSetPlaylistAndResetIndex();

        try {
            cleanData();
            setUp();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        getCurrentTrack_ShouldReturnNullForEmptyPlaylist();

        try {
            cleanData();
            setUp();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        findByName_ShouldReturnMatchingSongs();

    }
    void checkSongService(){
        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        createPlaylist_ShouldCreateNewPlaylist();

        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        addSongToPlaylist_ShouldAddSong();

        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        removeSongFromPlaylist_ShouldRemoveSong();

        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        getPlaylistByName_ShouldReturnPlaylist();

        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        getAllPlaylists_ShouldReturnAllPlaylists();
        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        getSongsInPlaylist_ShouldReturnSongs();

        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        deletePlaylistById_ShouldRemovePlaylist();

        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        findById_ShouldReturnPlaylist();

        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        removeSongFromPlaylistById_ShouldReturnTrueWhenRemoved();
        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        getPlaylistWithSongs_ShouldReturnPlaylistWithSongs();

        try {
            cleanData();
            setUpPlaylist();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        whenPlaylistNotFound_ShouldThrowException();

    }

    public void test(){
        cleanData();
        BaseCheckForAddAndDel();
        checkMp3Service();
        checkSongService();
    }
void BaseCheckForAddAndDel(){
    Optional<Song> newSong = Optional.empty();
    try {
        newSong = SongsService.addSong("newSongName", "newAuthorName");
        assert newSong.get().getName().equals("newSongName");
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    Playlist playlsit = null;
    try {
        playlsit = playlistService.createPlaylist("NewPlaylist");
        assert "NewPlaylist".equals(playlsit.getName());

    } catch (Exception e) {
        System.out.println(e.getMessage());
    }


    songsService.getAllSongs().stream().map(Song::toString).forEach(System.out::println);

    try {
        songsService.findById(newSong.get().getId());
        songsService.deleteSongById(newSong.get().getId());
        assert  songsService.findById(newSong.get().getId())==null;
        System.out.println("Песня удалена успешно!");
    } catch (RuntimeException e) {
        System.out.println(e.getMessage());
    }
//
    assert null==mp3PlayerService.getCurrentTrack();
    assert null== mp3PlayerService.nextTrack();
    assert null==mp3PlayerService.previousTrack();
    assert !mp3PlayerService.isPlaying();
}
    private Playlist testPlaylist;
    private Song song1, song2, song3;
    void setUp() throws Exception{
        try {
            songsService.deleteSongByTitle("song1");
        }catch (Exception e) {
            System.out.println(e.getMessage() + "Это норм");
        }
        try {
            songsService.deleteSongByTitle("song2");

        }catch (Exception e) {
            System.out.println(e.getMessage() + "Это норм");
        }
        try {
            songsService.deleteSongByTitle("song3");

        }catch (Exception e) {
            System.out.println(e.getMessage() + "Это норм");
        }
        song1 = SongsService.addSong("song1");
        song2 = SongsService.addSong("song2");
        song3 = SongsService.addSong("song3");
        try {
            playlistService.deletePlaylistByName("Test Playlist");
        }catch (Exception e) {
            System.out.println(e.getMessage() + "Это норм");
        }
        testPlaylist = playlistService.createPlaylist("Test Playlist");
        playlistService.addSongToPlaylist(testPlaylist.getId(), song1.getId());
        playlistService.addSongToPlaylist(testPlaylist.getId(), song2.getId());
        playlistService.addSongToPlaylist(testPlaylist.getId(), song3.getId());
        System.out.println(testPlaylist.toString());
        testPlaylist = playlistService.getPlaylistWithSongs(testPlaylist.getId());
        System.out.println(testPlaylist.getSongs().toString());

    }
    void cleanData() {
        playlistRepository.deleteAll();
        songRepository.deleteAll();
    }
    private Song testSong1;
    private Song testSong2;
    void setUpPlaylist() {
        // Создаем тестовые данные перед каждым тестом
        testPlaylist = new Playlist();
        testPlaylist.setName("Test Playlist");
        playlistRepository.save(testPlaylist);

        testSong1 = new Song();
        testSong1.setTitle("Song 1");
        testSong1.setArtist("Artist 1");
        testSong1.setFilePath("path1.mp3");
        songRepository.save(testSong1);

        testSong2 = new Song();
        testSong2.setTitle("Song 2");
        testSong2.setArtist("Artist 2");
        testSong2.setFilePath("path2.mp3");
        songRepository.save(testSong2);
    }

    void setCurrentPlaylist_ShouldSetPlaylistAndResetIndex() {
        mp3PlayerService.setCurrentPlaylist(testPlaylist);

        assertThat(mp3PlayerService.getCurrentPlaylist())
                .isEqualTo(testPlaylist);

        assertThat(mp3PlayerService.getCurrentTrackIndex())
                .isZero();
    }
    void createPlaylist_ShouldCreateNewPlaylist() {
        Playlist created = playlistService.createPlaylist("New Playlist");

        assertThat(created)
                .isNotNull()
                .extracting(Playlist::getName)
                .isEqualTo("New Playlist");

        assertThat(created.getSongs()).isEmpty();
    }

    void addSongToPlaylist_ShouldAddSong() {
        Playlist updated = playlistService.addSongToPlaylist(testPlaylist.getId(), testSong1.getId());

        assertThat(updated.getSongs())
                .hasSize(1)
                .containsExactly(testSong1);
    }

    void removeSongFromPlaylist_ShouldRemoveSong() {
        playlistService.addSongToPlaylist(testPlaylist.getId(), testSong1.getId());

        Playlist updated = playlistService.removeSongFromPlaylist(testPlaylist.getId(), testSong1.getId());

        assertThat(updated.getSongs()).isEmpty();
    }

    void getPlaylistByName_ShouldReturnPlaylist() {
        Playlist found = playlistService.getPlaylistByName("Test Playlist");

        assertThat(found)
                .extracting(Playlist::getId, Playlist::getName)
                .containsExactly(testPlaylist.getId(), "Test Playlist");
    }

    void getAllPlaylists_ShouldReturnAllPlaylists() {
        playlistService.createPlaylist("Another Playlist");

        assertThat(playlistService.getAllPlaylists())
                .hasSize(2)
                .extracting(Playlist::getName)
                .containsExactlyInAnyOrder("Test Playlist", "Another Playlist");
    }

    void getSongsInPlaylist_ShouldReturnSongs() {
        playlistService.addSongToPlaylist(testPlaylist.getId(), testSong1.getId());
        playlistService.addSongToPlaylist(testPlaylist.getId(), testSong2.getId());

        assertThat(playlistService.getSongsInPlaylist(testPlaylist.getId()))
                .hasSize(2)
                .containsExactlyInAnyOrder(testSong1, testSong2);
    }

    void deletePlaylistById_ShouldRemovePlaylist() {
        playlistService.deletePlaylistById(testPlaylist.getId());

        assertThat(playlistRepository.findById(testPlaylist.getId())).isEmpty();
    }

    void findById_ShouldReturnPlaylist() {
        assertThat(playlistService.findById(testPlaylist.getId()))
                .isPresent()
                .get()
                .extracting(Playlist::getId)
                .isEqualTo(testPlaylist.getId());
    }

    void removeSongFromPlaylistById_ShouldReturnTrueWhenRemoved() {
        playlistService.addSongToPlaylist(testPlaylist.getId(), testSong1.getId());

        assertThat(playlistService.removeSongFromPlaylistById(testPlaylist.getId(), testSong1.getId())).isTrue();

        assertThat(playlistRepository.findById(testPlaylist.getId()).get().getSongs())
                .doesNotContain(testSong1);
    }

    void getPlaylistWithSongs_ShouldReturnPlaylistWithSongs() {
        playlistService.addSongToPlaylist(testPlaylist.getId(), testSong1.getId());

        assertThat(playlistService.getPlaylistWithSongs(testPlaylist.getId()).getSongs())
                .hasSize(1)
                .first()
                .extracting(Song::getId)
                .isEqualTo(testSong1.getId());
    }

    void whenPlaylistNotFound_ShouldThrowException() {
        assertThatThrownBy(() -> playlistService.getPlaylistByName("Nonexistent"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Плейлист не найден");
    }
    void getCurrentTrack_ShouldReturnNullForEmptyPlaylist() {
        Playlist emptyPlaylist = new Playlist();
        emptyPlaylist.setName("Empty");
        mp3PlayerService.setCurrentPlaylist(emptyPlaylist);

        assertThat(mp3PlayerService.getCurrentTrack())
                .isNull();

        mp3PlayerService.setCurrentPlaylist(null);

        assertThat(mp3PlayerService.getCurrentTrack())
                .isNull();
    }

    void findByName_ShouldReturnMatchingSongs() {
        mp3PlayerService.setCurrentPlaylist(testPlaylist);
        System.out.println(testPlaylist);
        assertThat(songsService.SongfindByTitle("song1")).isPresent().hasValueSatisfying(song -> {
            assertThat(song.getTitle()).isEqualTo("song1");});

        assertThat(mp3PlayerService.findByName("NonExisting"))
                .isEmpty();

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