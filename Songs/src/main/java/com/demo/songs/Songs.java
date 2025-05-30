package com.demo.songs;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.data.annotation.Id;


@Document("songs")
public class Songs {

    @Id
    private String Id;



    @Field
    private String song;

    @Field
    private String artist;


    @Field("imageLoc") // MongoDB field
    @JsonProperty("imageLoc")
    private String imageloc;

    @Field("songLoc") // MongoDB field
    @JsonProperty("songLoc")
    private String songloc;

    @Field("UserId") // MongoDB field
    @JsonProperty("UserId")
    private Long UserId;

    public Songs() {

    }


    public Songs(String id, String song, String artist, String imageloc, String songloc, Long userId) {
        Id = id;
        this.song = song;
        this.artist = artist;
        this.imageloc = imageloc;
        this.songloc = songloc;
        UserId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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


}
