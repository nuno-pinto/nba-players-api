package com.nbaapp;

import com.nbaapp.models.Player;
import com.nbaapp.models.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NBAJsonParser {

    private List<Player> playerList;
    private List<Team> teamList;

    public void parseJson(String originJson, String finalJson) {

        JSONArray rowSet = accessRowSet(originJson);

        playerList = rowSetToList(rowSet);

        listToJson(playerList, finalJson);

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

    private void downloadPictures(List<Player> playerList) {


//
//        try {
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//


    }


    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Team> getTeamList() {
        return teamList;
    }
}
