package com.nbaapp.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nbaapp.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

public class PlayerSerializer extends JsonSerializer<Player> {

    private TeamService teamService;

    @Autowired
    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        List<Team> teamList = createShuffledTeamList(player);

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", player.getId());
        jsonGenerator.writeStringField("name", player.getName());
        jsonGenerator.writeStringField("picture", player.getId() + ".png");
        jsonGenerator.writeStringField("pos", player.getPosition());
        jsonGenerator.writeNumberField("teamId", player.getTeam().getId());
        jsonGenerator.writeStringField("team", player.getTeam().getName());
        jsonGenerator.writeStringField("teampicture", "images/teamspics/" + player.getTeam().getId() + ".png");
        jsonGenerator.writeNumberField("rightchoice", teamList.indexOf(player.getTeam()));
        jsonGenerator.writeArrayFieldStart("teams");

        for (Team team : teamList) {
            jsonGenerator.writeObject(team);
        }

        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();

    }

    public List<Team> createShuffledTeamList(Player player) {

        Set<Team> teamSet = new HashSet<>();

        Team temp;

        while (teamSet.size() < 3) {
            if (!(temp = teamService.getRandomTeam()).equals(player.getTeam())) {
                teamSet.add(temp);
            }
        }

        List<Team> teamList = new ArrayList<>(teamSet);

        teamList.add(player.getTeam());

        Collections.shuffle(teamList);

        return teamList;

    }

}
