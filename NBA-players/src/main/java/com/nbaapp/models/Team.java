package com.nbaapp.models;

import java.util.Objects;

public class Team {

    private final int id;
    private final String name;
    private final String logo;

    public Team(int id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id && Objects.equals(name, team.name) && Objects.equals(logo, team.logo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, logo);
    }

    @Override
    public String toString() {
        return "ID: " + id +", Name: " + name + ", Logo: " + logo;
    }
}
