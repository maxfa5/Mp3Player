import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.Model.Song;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.example.Mp3Player.repository.PlaylistRepository;
import org.example.Mp3Player.repository.SongRepository;
import org.example.Mp3Player.service.Mp3PlayerService;
import org.example.Mp3Player.service.PlaylistService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // For Mockito annotations
@ExtendWith(SpringExtension.class) // For Spring context loading
@SpringBootTest // Loads the Spring context for testing
class Mp3PlayerServiceTest {

    @Lazy
    @InjectMocks // Inject mocks into this service
    private Mp3PlayerService playerService;

    @Mock // Mock the PlaylistService dependency
    private PlaylistService playlistService;

    @Mock // Mock dependencies of playlistService
    private PlaylistRepository playlistRepository;

    @Mock
    private SongRepository songRepository;

    private Playlist testPlaylist;
    private Song song1, song2, song3;

    @BeforeEach
    void setUp() {
        // Set up your test data
        song1 = new Song("Song1", "Artist1", "path/to/song1.mp3");
        song2 = new Song("Song2", "Artist2", "path/to/song2.mp3");
        song3 = new Song("Song3", "Artist3", "path/to/song3.mp3");

        testPlaylist = new Playlist("Test Playlist");
        playlistService.addSongToPlaylist(testPlaylist.getId(), song1.getId());
        playlistService.addSongToPlaylist(testPlaylist.getId(), song2.getId());
        playlistService.addSongToPlaylist(testPlaylist.getId(), song3.getId());
    }

    @Test
    void testPlayPlaylist() {
        // Mock the behavior of PlaylistService if Mp3PlayerService uses it
        when(playlistService.getPlaylistWithSongs(1L)).thenReturn(testPlaylist);  // Example: If playerService loads playlist by ID

        // Act: Call the method you are testing
        mp3pla.playPlaylist(1L);

        // Assert: Verify that the expected actions occurred (replace with your actual assertions)
        // Example: Verify that the correct songs were played in the correct order
        List<Song> expectedSongs = new ArrayList<>();
        expectedSongs.add(song1);
        expectedSongs.add(song2);
        expectedSongs.add(song3);

        // Assuming playPlaylist actually creates a play order list
        assertEquals(expectedSongs, playerService.getCurrentlyPlaying()); // Adjust assertion as needed for your service's logic

    }

    // Add more test methods for other functionalities of Mp3PlayerService
}