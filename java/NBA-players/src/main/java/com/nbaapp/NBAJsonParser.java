package com.nbaapp;

import com.nbaapp.models.Player;
import com.nbaapp.models.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class NBAJsonParser {

    private List<Player> playerList;
    private List<Team> teamList;

    public void parseJson(String originJson, String finalJson) {

        System.out.println("Downloading Json from nba.com...");

        downloadPlayersJson(originJson);

        System.out.println("Reading Json...");

        JSONArray rowSet = accessRowSet(originJson);

        System.out.println("Converting Json to players...");

        playerList = rowSetToList(rowSet);

        System.out.println("Converting players to updated Json...");

        listToJson(playerList, finalJson);

        System.out.println("Creating teams...");

        teamList = createTeams(playerList);

     }

    private JSONArray accessRowSet(String jsonPath) {

        JSONParser jsonParser = new JSONParser();

        JSONArray rowSet = null;

        try {

            FileReader reader = new FileReader(jsonPath);

            JSONObject initObj = (JSONObject) jsonParser.parse(reader);

            JSONArray resultSets = (JSONArray) initObj.get("resultSets");

            JSONObject secondObj = (JSONObject) resultSets.get(0);

            rowSet = (JSONArray) secondObj.get("rowSet");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rowSet;
    }

    @SuppressWarnings("unchecked")
    private List<Player> rowSetToList(JSONArray rowSet) {

        return (List<Player>) rowSet.stream()
                .filter(elm -> elm instanceof JSONArray && ((JSONArray) elm).get(25).equals("2023"))
                .map(elm -> {
                    JSONArray player = (JSONArray) elm;
                    return new Player(
                            Integer.parseInt(player.get(0).toString()),
                            player.get(2) + " " + player.get(1),
                            player.get(11).toString(),
                            player.get(7) + " " + player.get(8),
                            Integer.parseInt(player.get(4).toString())
                    );
                })
                .collect(Collectors.toList());

    }

    @SuppressWarnings("unchecked")
    private void listToJson(List<Player> playerList, String jsonPath) {

        JSONArray arr = new JSONArray();

        playerList.forEach(player -> {

            JSONObject jsonPlayer = new JSONObject();
            jsonPlayer.put("id", player.getId());
            jsonPlayer.put("name", player.getName());
            jsonPlayer.put("picture", player.getId() + ".png");
            jsonPlayer.put("pos", player.getPosition());
            jsonPlayer.put("team", player.getTeam());
            jsonPlayer.put("teamId", player.getTeamId());
            jsonPlayer.put("teampicture", "images/teamspics/" + player.getTeamId() + ".png");

            arr.add(jsonPlayer);
        });

        try {
            FileWriter writer = new FileWriter(jsonPath);

            writer.write(arr.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Team> createTeams(List<Player> playerList) {

        Set<Team> teamSet = new HashSet<>();

        playerList.forEach(player -> {

            teamSet.add(new Team(player.getTeamId(), player.getTeam(), "images/teamspics/" + player.getTeamId() + ".png"));

        });

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
