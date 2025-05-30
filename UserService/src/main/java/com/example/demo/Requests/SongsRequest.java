package com.example.demo.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Base64;

public class SongsRequest {

    private String song;


    private String artist;

    @JsonProperty("imageLoc")
    private String imageloc;

    @JsonProperty("songLoc")
    private String songloc;

    @JsonProperty("UserId")
    private Long UserId;


    public SongsRequest() {

    }


    public SongsRequest(String song, String artist, String imageloc, String songloc, Long userId) {
        this.song = song;
        this.artist = artist;
        this.imageloc = imageloc;
        this.songloc = songloc;
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


    public String getImageloc() {
        return imageloc;
    }

    public void setImageloc(String imageloc) {
        this.imageloc = imageloc;
    }

    public String getSongloc() {
        return songloc;
    }

    public void setSongloc(String songloc) {
        this.songloc = songloc;
    }



    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }


    @Override
    public String toString() {
        return "SongsRequest{" +
                "song='" + song + '\'' +
                ", artist='" + artist + '\'' +
                ", imageloc='" + imageloc + '\'' +
                ", songloc='" + songloc + '\'' +
                ", UserId=" + UserId +
                '}';
    }
}
