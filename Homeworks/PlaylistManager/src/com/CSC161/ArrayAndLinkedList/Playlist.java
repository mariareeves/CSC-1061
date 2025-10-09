//Maria Reeves
// CSC1061 - ASSIGNMENT PLAYLIST MANAGER
package com.CSC161.ArrayAndLinkedList;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Playlist {

    public static void main(String[] args) {
        MyDoubleLinkedList<Song> playlist = new MyDoubleLinkedList<>();
        Scanner in = new Scanner(System.in);

        printHelp();

        while (true) {
            System.out.print("\n: ");
            String cmd = in.nextLine().trim().toLowerCase();
            if (cmd.isEmpty()) continue;

            switch (cmd) {
                case "add":
                    addSong(playlist, in);
                    break;
                case "remove":
                    removeSong(playlist, in);
                    break;
                case "count":
                    System.out.println(playlist.size());
                    break;
                case "play":
                    play(playlist);
                    break;
                case "shuffle":
                    shuffle(playlist);
                    break;
                case "reverse":
                    playlist.reverse();
                    break;
                case "save":
                    saveToFile(playlist, in);
                    break;
                case "load":
                    loadFromFile(playlist, in);
                    break;
                case "quit":
                    in.close();
                    return;
                default:
                    printHelp();
            }
        }
    }

    // ------------------- Menu helpers -------------------

    private static void printHelp() {
        System.out.println("*** Playlist Manager! ***");
        System.out.println("Commands:");
        System.out.println("add");
        System.out.println("remove");
        System.out.println("count");
        System.out.println("play");
        System.out.println("shuffle");
        System.out.println("reverse");
        System.out.println("save");
        System.out.println("load");
        System.out.println("quit");
    }

    private static void addSong(MyDoubleLinkedList<Song> playlist, Scanner in) {
        System.out.print("Enter artist: ");
        String artist = in.nextLine().trim();
        System.out.print("Enter title: ");
        String title = in.nextLine().trim();
        playlist.add(new Song(title, artist));
    }

    private static void removeSong(MyDoubleLinkedList<Song> playlist, Scanner in) {
        System.out.print("Enter artist: ");
        String artist = in.nextLine().trim();
        System.out.print("Enter title: ");
        String title = in.nextLine().trim();

        int idx = -1;
        for (int i = 0; i < playlist.size(); i++) {
            Song s = playlist.get(i);
            if (s.getArtist().equalsIgnoreCase(artist)
                    && s.getTitle().equalsIgnoreCase(title)) {
                idx = i;
                break;
            }
        }

        if (idx >= 0) {
            playlist.remove(idx);
        } else {
            System.out.println("Not found.");
        }
    }

    private static void play(MyDoubleLinkedList<Song> playlist) {
        for (Song s : playlist) {
            System.out.println(s.getArtist() + " - " + s.getTitle());
        }
    }

    private static void shuffle(MyDoubleLinkedList<Song> playlist) {
        if (playlist.size() < 2) return;

        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : playlist) songs.add(s);

        Collections.shuffle(songs, new Random());
        playlist.clear();

        for (Song s : songs) {
            playlist.add(s);
        }
    }

    private static void saveToFile(MyDoubleLinkedList<Song> playlist, Scanner in) {
        System.out.print("Enter file: ");
        String filename = in.nextLine().trim();

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename))) {
            for (Song s : playlist) {
                writer.write(s.getTitle() + "|" + s.getArtist());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    private static void loadFromFile(MyDoubleLinkedList<Song> playlist, Scanner in) {
        System.out.print("Enter file: ");
        String filename = in.nextLine().trim();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            playlist.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts.length == 2) {
                    playlist.add(new Song(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Load failed: " + e.getMessage());
        }
    }
}

