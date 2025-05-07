import org.example.Mp3Player.Model.Playlist;
import org.example.Mp3Player.Model.Song;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.example.Mp3Player.service.Mp3PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Mp3PlayerServiceTest {

    @InjectMocks
    private Mp3PlayerService mp3PlayerService;

    @Mock
    private Playlist playlist;

    @Mock
    private Player player;

    private final Song song1 = new Song("Song1", "artist1", "album1");
    private final Song song2 = new Song("Song2", "artist2", "album2");

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mp3PlayerService, "player", null);
        ReflectionTestUtils.setField(mp3PlayerService, "isPlaying", false);
    }
//
//    @Test
//    void setCurrentPlaylist_ShouldSetPlaylistAndResetIndex() {
//        when(playlist.size()).thenReturn(2);
//        when(playlist.isEmpty()).thenReturn(false);
//
//        mp3PlayerService.setCurrentPlaylist(playlist);
//
//        assertEquals(playlist, mp3PlayerService.getCurrentPlaylist());
//        assertEquals(0, mp3PlayerService.getCurrentTrackIndex());
//    }
//
//    @Test
//    void getCurrentTrack_ShouldReturnNullForEmptyPlaylist() {
//        when(playlist.isEmpty()).thenReturn(true);
//        mp3PlayerService.setCurrentPlaylist(playlist);
//
//        assertNull(mp3PlayerService.getCurrentTrack());
//    }
//
//    @Test
//    void nextTrack_ShouldCycleThroughTracks() {
//        when(playlist.size()).thenReturn(2);
//        when(playlist.isEmpty()).thenReturn(false);
//        when(playlist.get(0)).thenReturn(song1);
//        when(playlist.get(1)).thenReturn(song2);
//
//        mp3PlayerService.setCurrentPlaylist(playlist);
//
//        assertEquals(song2, mp3PlayerService.nextTrack());
//        assertEquals(song1, mp3PlayerService.nextTrack());
//    }
//
//    @Test
//    void previousTrack_ShouldCycleBackwards() {
//        when(playlist.size()).thenReturn(2);
//        when(playlist.isEmpty()).thenReturn(false);
//        when(playlist.get(0)).thenReturn(song1);
//        when(playlist.get(1)).thenReturn(song2);
//
//        mp3PlayerService.setCurrentPlaylist(playlist);
//        mp3PlayerService.nextTrack(); // move to index 1
//
//        assertEquals(song1, mp3PlayerService.previousTrack());
//    }
//
//    @Test
//    void play_ShouldStartNewPlayerThread() throws Exception {
//        FileInputStream mockStream = mock(FileInputStream.class);
//        Mp3PlayerService spyService = spy(mp3PlayerService);
//
//        doReturn(mockStream).when(spyService).createFileInputStream(anyString());
//        doReturn(player).when(spyService).createPlayer(mockStream);
//
//        spyService.play("test.mp3");
//
//        assertTrue(spyService.isPlaying());
//        verify(player).play();
//    }
//
//    @Test
//    void stop_ShouldClosePlayer() {
//        ReflectionTestUtils.setField(mp3PlayerService, "player", player);
//        ReflectionTestUtils.setField(mp3PlayerService, "isPlaying", true);
//
//        mp3PlayerService.stop();
//
//        assertFalse(mp3PlayerService.isPlaying());
//        verify(player).close();
//    }

    @Test
    void findByName_ShouldReturnMatchingSongs() {
        List<Song> songs = Arrays.asList(song1, song2);
        when(playlist.getSongByName("Song1")).thenReturn(Collections.singletonList(song1));
        mp3PlayerService.setCurrentPlaylist(playlist);

        List<Song> result = mp3PlayerService.findByName("Song1");

        assertEquals(1, result.size());
        assertEquals(song1, result.get(0));
    }
}