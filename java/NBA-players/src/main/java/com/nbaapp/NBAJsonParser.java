package com.nbaapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbaapp.models.Player;
import com.nbaapp.models.Team;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class NBAJsonParser {

    private List<Player> playerList;
    private List<Team> teamList;


    public void parseJson(String originJson, String finalJson) {

        System.out.println("Downloading Json from nba.com...");

        downloadPlayersJson(originJson);

        System.out.println("Converting Json to players...");

        playerList = jsonToList(originJson);

        System.out.println("Converting players to updated Json...");

        listToJson(playerList, finalJson);

        System.out.println("Creating teams...");

        teamList = createTeams(playerList);

    }

    private List<Player> jsonToList(String originJson) {

        List<Player> players = new ArrayList<>();

        try {

            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(new File(originJson));

            JsonNode rowSetNode = rootNode.get("resultSets").get(0).get("rowSet");

            rowSetNode.forEach(playerNode -> {

                if (playerNode.get(25).asText().equals("2023")) {

                    players.add(new Player(
                            Integer.parseInt(playerNode.get(0).asText()),
                            playerNode.get(2).asText() + " " + playerNode.get(1).asText(),
                            playerNode.get(11).asText(),
                            Integer.parseInt(playerNode.get(4).asText()),
                            playerNode.get(7).asText() + " " + playerNode.get(8).asText()
                    ));
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }


    private void listToJson(List<Player> playerList, String jsonPath) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            String jsonArray = objectMapper.writeValueAsString(playerList);

            FileWriter writer = new FileWriter(jsonPath);

            writer.write(jsonArray);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Team> createTeams(List<Player> playerList) {

        Set<Team> teamSet = new HashSet<>();

        playerList.forEach(player -> teamSet.add(player.getTeam()));

        return new LinkedList<>(teamSet);

    }

    private void downloadPlayersJson(String filePath) {

        try {

            String requestUrl = "https://stats.nba.com/stats/playerindex?College=&Country=&DraftPick=&DraftRound=&DraftYear=&Height=&Historical=1&LeagueID=00&Season=2023-24&SeasonType=Regular%20Season&TeamID=0&Weight=";

            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Host", "stats.nba.com");
            con.setRequestProperty("Origin", "https://www.nba.com");
            con.setRequestProperty("Referer", "https://www.nba.com/");
            con.setRequestProperty("Sec-Ch-Ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Brave\";v=\"120\"");
            con.setRequestProperty("Sec-Ch-Ua-Mobile", "?0");
            con.setRequestProperty("Sec-Ch-Ua-Platform", "\"Linux\"");
            con.setRequestProperty("Sec-Fetch-Dest", "empty");
            con.setRequestProperty("Sec-Fetch-Mode", "cors");
            con.setRequestProperty("Sec-Fetch-Site", "same-site");
            con.setRequestProperty("Sec-Gpc", "1");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            InputStream inputStream = new GZIPInputStream(con.getInputStream());

            Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Team> getTeamList() {
        return teamList;
    }
}
