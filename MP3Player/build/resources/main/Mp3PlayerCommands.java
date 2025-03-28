//package org.example.Mp3Player;
//
//import org.example.Mp3Player.Model.Playlist;
//import org.example.Mp3Player.Model.Song;
//import org.example.Mp3Player.service.Mp3PlayerService;
//import org.example.Mp3Player.service.PlaylistService;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
//
//@ShellComponent
//public class Mp3PlayerCommands {
//    @ShellMethod(key = "hello", value = "Say hello")
//    public String hello() {
//        return "Hello, World!";
//    }
//    private final PlaylistService playlistService;
//    private final Mp3PlayerService mp3PlayerService;
//
//    public Mp3PlayerCommands(PlaylistService playlistService, Mp3PlayerService mp3PlayerService) {
//        this.playlistService = playlistService;
//        this.mp3PlayerService = mp3PlayerService;
//    }
//
//    @ShellMethod(key = "load-playlist", value = "Load a playlist by ID")
//    public String loadPlaylist(@ShellOption Long playlistId) {
//        Playlist playlist = playlistService.getPlaylistWithSongs(playlistId);
//        playlistService.setCurrentPlaylist(playlist);
//        return "Playlist loaded.";
//    }
//
//    @ShellMethod(key = "play", value = "Play the current track")
//    public String play() {
//        Song currentSong = playlistService.getCurrentTrack();
//        if (currentSong != null) {
//            mp3PlayerService.play(currentSong.getFilePath());
//            return "Playing: " + currentSong.getTitle();
//        } else {
//            return "Playlist is not loaded or empty.";
//        }
//    }
//
//    @ShellMethod(key = "next", value = "Play the next track")
//    public String next() {
//        Song nextSong = playlistService.nextTrack();
//        if (nextSong != null) {
//            mp3PlayerService.play(nextSong.getFilePath());
//            return "Playing next track: " + nextSong.getTitle();
//        } else {
//            return "Playlist is not loaded or empty.";
//        }
//    }
//
//    @ShellMethod(key = "previous", value = "Play the previous track")
//    public String previous() {
//        Song previousSong = playlistService.previousTrack();
//        if (previousSong != null) {
//            mp3PlayerService.play(previousSong.getFilePath());
//            return "Playing previous track: " + previousSong.getTitle();
//        } else {
//            return "Playlist is not loaded or empty.";
//        }
//    }
//
//    @ShellMethod(key = "exit", value = "Exit the application")
//    public void exit() {
//        System.exit(0);
//    }
//}