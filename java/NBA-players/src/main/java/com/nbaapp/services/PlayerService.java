package com.nbaapp.services;

import com.nbaapp.daos.PlayerDao;
import com.nbaapp.models.Player;
import com.nbaapp.models.Team;
import com.nbaapp.scraper.ImageDownloader;
import com.nbaapp.scraper.NBAJsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private PlayerDao dao;
    private TeamService teamService;

    @Autowired
    public void setDao(PlayerDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public List<Player> getPlayerList() {
        return dao.findAll();
    }

    public Player getRandomPlayer() {
        List<Player> playerList = dao.findAll();

        return playerList.get((int) Math.floor(Math.random() * playerList.size()));
    }

    public void populatePlayersAndTeams() {

        NBAJsonParser jsonParser = parseJson();

        teamService.populateTeams(jsonParser.getTeamList());

        jsonParser.getPlayerList().forEach(player -> dao.saveOrUpdate(player));

        //downloadFiles(jsonParser);

    }

    private NBAJsonParser parseJson() {

        String ORIGIN_JSON_PATH = "src/main/resources/original-players.json";
        String FINAL_JSON_PATH = "src/main/resources/final-players.json";

        NBAJsonParser jsonParser = new NBAJsonParser();

        jsonParser.parseJson(ORIGIN_JSON_PATH, FINAL_JSON_PATH);

        return jsonParser;

    }

    private void downloadFiles(NBAJsonParser jsonParser) {

        String PLAYERS_IMAGE_PATH = "src/main/resources/photos/";
        String TEAMS_IMAGE_PATH = "src/main/resources/teams/";

        ImageDownloader dl = new ImageDownloader();
        List<Player> playerList = jsonParser.getPlayerList();
        List<Team> teamList = jsonParser.getTeamList();

        dl.downloadPlayerImages(playerList, PLAYERS_IMAGE_PATH);
        dl.downloadTeamImages(teamList, TEAMS_IMAGE_PATH);
        dl.convertTeamImages(teamList, TEAMS_IMAGE_PATH);

    }


}
