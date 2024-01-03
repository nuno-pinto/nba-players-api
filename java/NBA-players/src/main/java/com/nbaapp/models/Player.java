package com.nbaapp.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

@Entity
@Table(name = "player")
@JsonSerialize(using = PlayerSerializer.class)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Integer id;
    private final String name;
    private final String position;
    private final Team team;

    public Player(int id, String name, String position, int teamId, String teamName) {
        this.id = id;
        this.name = name;
        this.position = parsePosition(position);
        this.team = new Team(teamId, teamName);
    }

    private static String parsePosition(String position) {
        return switch (position) {
            case "G" -> "Guard";
            case "G-F" -> "Guard/Forward";
            case "F-G" -> "Forward/Guard";
            case "F" -> "Forward";
            case "F-C" -> "Forward/Center";
            case "C-F" -> "Center/Forward";
            default -> "Center";
        };
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }


    @Override
    public String toString() {
        return "ID: " + id +", Name: " + name + ", Position: " + position + ", Team: " + team;
    }
}
