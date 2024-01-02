package com.nbaapp.models;

public class Player {

    private final int id;
    private final String name;
    private final String position;
    private final String team;
    private final int teamId;


    public Player(int id, String name, String position, String team, int teamId) {
        this.id = id;
        this.name = name;
        this.position = parsePosition(position);
        this.team = team;
        this.teamId = teamId;
    }

    private static String parsePosition(String position) {
        switch (position) {
            case "G":
                return "Guard";
            case "G-F":
                return "Guard/Forward";
            case "F-G":
                return "Forward/Guard";
            case "F":
                return "Forward";
            case "F-C":
                return "Forward/Center";
            case "C-F":
                return "Center/Forward";
            default:
                return "Center";
        }
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

    public String getTeam() {
        return team;
    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public String toString() {
        return "ID: " + id +", Name: " + name + ", Position: " + position + ", Team: " + team;
    }
}
