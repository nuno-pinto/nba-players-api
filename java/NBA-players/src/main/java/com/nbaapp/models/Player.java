package com.nbaapp.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

@Entity
@Table(name = "player")
@JsonSerialize(using = PlayerSerializer.class)
public class Player {

    @Id
    private Integer id;
    private String name;
    private String position;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    public Player() {

    }

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "ID: " + id +", Name: " + name + ", Position: " + position + ", Team: " + team;
    }
}
