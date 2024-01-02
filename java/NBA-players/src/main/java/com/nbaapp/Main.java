package com.nbaapp;

import com.nbaapp.models.Player;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        NBAJsonParser jsonParser = new NBAJsonParser();

        jsonParser.parseJson("src/main/resources/Players.json", "src/main/resources/final-players.json");

        PlayerImageDownloader dl = new PlayerImageDownloader();

        dl.downloadPlayerImages(jsonParser.getPlayerList(), "src/main/resources/photos/test.png");

    }

}
