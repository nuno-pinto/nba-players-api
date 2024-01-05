package com.nbaapp.scraper;

import com.nbaapp.models.Player;
import com.nbaapp.models.Team;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ImageDownloader {

    public void downloadPlayerImages(List<Player> playerList, String filePath) {

        playerList.forEach(player -> {

            try {

                System.out.println("Downloading image of " + player.getName() + "...");

                InputStream in = new URL("https://cdn.nba.com/headshots/nba/latest/260x190/" + player.getId() + ".png").openStream();

                Files.copy(in, Paths.get(filePath + player.getId() + ".png"), StandardCopyOption.REPLACE_EXISTING);

                Thread.sleep(200);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });


    }

    public void downloadTeamImages(List<Team> teamList, String filePath) {

        teamList.forEach(team -> {

            try {

                System.out.println("Downloading logo from the " + team.getName() + "...");

                InputStream in = new URL("https://cdn.nba.com/logos/nba/" + team.getId() + "/global/L/logo.svg").openStream();

                Files.copy(in, Paths.get(filePath + team.getId() + ".svg"), StandardCopyOption.REPLACE_EXISTING);

                Thread.sleep(200);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    public void convertTeamImages(List<Team> teamList, String filePath) {

        teamList.forEach(team -> {

            try {

                System.out.println("Converting logo from the " + team.getName() + "...");

                PNGTranscoder transcoder = new PNGTranscoder();

                TranscoderInput input = new TranscoderInput(filePath + team.getId() + ".svg");

                OutputStream outputStream = new FileOutputStream(filePath + team.getId() + ".png");
                TranscoderOutput output = new TranscoderOutput(outputStream);

                transcoder.transcode(input, output);

                outputStream.flush();
                outputStream.close();

                File oldFile = new File(filePath + team.getId() + ".svg");
                oldFile.delete();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (TranscoderException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
