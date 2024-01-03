package com.nbaapp.controllers;

import com.nbaapp.daos.PlayerDao;
import com.nbaapp.models.Player;
import com.nbaapp.services.PlayerService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private PlayerDao dao;
    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Autowired
    public void setDao(PlayerDao dao) {
        this.dao = dao;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok().body(playerService.getPlayerList());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/random",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Player> getRandomPlayer(HttpServletResponse response) {

        response.addHeader("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok().body(playerService.getRandomPlayer());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/populate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity populatePlayersAndTeams() {

        playerService.populatePlayersAndTeams();

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/random/{id}.png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getPlayerImage(@PathVariable(value = "id") Integer id) {


        File image = new File("src/main/resources/photos/" + id + ".png");
        byte[] result = new byte[0];

        try {
            result = IOUtils.toByteArray(new FileInputStream(image));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/random/images/teamspics/{id}.png",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getTeamImage(@PathVariable(value = "id") Integer id) {

        File image = new File("src/main/resources/teams/" + id + ".png");
        byte[] result = new byte[0];

        try {
            result = IOUtils.toByteArray(new FileInputStream(image));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
