package com.nbaapp;


import com.nbaapp.models.Player;
import com.nbaapp.models.Team;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String ORIGIN_JSON_PATH = "src/main/resources/original-players.json";
        String FINAL_JSON_PATH ="src/main/resources/final-players.json";
        String PLAYERS_IMAGE_PATH = "src/main/resources/photos/";
        String TEAMS_IMAGE_PATH = "src/main/resources/teams/";

        NBAJsonParser jsonParser = new NBAJsonParser();
        ImageDownloader dl = new ImageDownloader();
        List<Player> playerList;
        List<Team> teamList;

        jsonParser.parseJson(ORIGIN_JSON_PATH, FINAL_JSON_PATH);

        playerList = jsonParser.getPlayerList();
        teamList = jsonParser.getTeamList();

        //dl.downloadPlayerImages(playerList, PLAYERS_IMAGE_PATH);

        dl.downloadTeamImages(teamList, TEAMS_IMAGE_PATH);

        dl.convertTeamImages(teamList, TEAMS_IMAGE_PATH);

     }

}
