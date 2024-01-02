package com.nbaapp;

import com.nbaapp.models.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PlayerImageDownloader {

    public void downloadPlayerImages(List<Player> playerList, String filePath) {


        playerList.forEach(player -> {

            try {

                InputStream in = new URL("https://cdn.nba.com/headshots/nba/latest/260x190/" + player.getId() + ".png").openStream();

                Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });


    }
}
