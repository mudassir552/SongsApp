package com.example.demo.Requests;

import java.util.Arrays;
import java.util.Base64;

public class SongsRequest {

    private String song;


    private String artist;


    private byte[] image;

    private byte[] SongFile;


    private Long UserId;

    public SongsRequest() {

    }



    public SongsRequest(String id, String song, String artist, byte[] image, byte[] songFile, Long userId) {
        super();

        this.song = song;
        this.artist = artist;
        this.image = image;
        SongFile = songFile;
        UserId = userId;
    }






    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }





    public byte[] getImage() {
        return image;
    }





    public void setImage(byte[] image) {
        this.image = image;
    }



    public byte[] getSongFile() {
        return SongFile;
    }



    public void setSongFile(byte[] songFile) {
        SongFile = songFile;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }


    @Override
    public String toString() {
        return "{" +
                "\"song\": \"" + song + "\"," +
                "\"artist\": \"" + artist + "\"," +
                "\"image\": \"" + Base64.getEncoder().encodeToString(image) + "\"," +
                "\"songFile\": \"" + Base64.getEncoder().encodeToString(SongFile) + "\"," +
                "\"userId\": " + UserId +
                "}";
    }


}
