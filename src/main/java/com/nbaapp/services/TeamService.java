package com.nbaapp.services;

import com.nbaapp.daos.TeamDao;
import com.nbaapp.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private TeamDao dao;

    @Autowired
    public void setDao(TeamDao dao) {
        this.dao = dao;
    }

    public List<Team> getTeamList() {
        return dao.findAll();
    }

    public Team getRandomTeam() {
        List<Team> teamList = dao.findAll();

        return teamList.get((int) Math.floor(Math.random() * teamList.size()));
    }

    public void populateTeams(List<Team> teamList) {
        teamList.forEach(team -> dao.saveOrUpdate(team));
    }
}
