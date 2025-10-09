package com.CSC161.ArrayAndLinkedList;

public class Song {
    private String title;
    private String artist;

    //constructor
    public Song(String title, String artist){
        this.title = title;
        this.artist = artist;
    }


    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String toString() {
        return "\"" + title + "\" — " + artist;
    }

}
