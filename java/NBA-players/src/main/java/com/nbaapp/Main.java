package com.nbaapp;

import com.nbaapp.models.Player;
import com.nbaapp.models.Team;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        NBAJsonParser jsonParser = parseJson();

        downloadFiles(jsonParser);

    }

    public static NBAJsonParser parseJson() {

        String ORIGIN_JSON_PATH = "src/main/resources/original-players.json";
        String FINAL_JSON_PATH = "src/main/resources/final-players.json";

        NBAJsonParser jsonParser = new NBAJsonParser();

        jsonParser.parseJson(ORIGIN_JSON_PATH, FINAL_JSON_PATH);

        return jsonParser;

    }

    public static void downloadFiles(NBAJsonParser jsonParser) {

        String PLAYERS_IMAGE_PATH = "src/main/resources/photos/";
        String TEAMS_IMAGE_PATH = "src/main/resources/teams/";

        ImageDownloader dl = new ImageDownloader();
        List<Player> playerList = jsonParser.getPlayerList();
        List<Team> teamList = jsonParser.getTeamList();

        dl.downloadPlayerImages(playerList, PLAYERS_IMAGE_PATH);
        dl.downloadTeamImages(teamList, TEAMS_IMAGE_PATH);
        dl.convertTeamImages(teamList, TEAMS_IMAGE_PATH);

    }
}
