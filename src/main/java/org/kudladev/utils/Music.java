package org.kudladev.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;


public class Music {

    private MediaPlayer tunePlayer;

    private MediaPlayer inGameTunePlayer;

    private MediaPlayer jumpingPlayer;

    private MediaPlayer fallingPlayer;

    private MediaPlayer gameOverPlayer;

    private MediaPlayer diePlayer;


    public MediaPlayer getTunePlayer() {
        return tunePlayer;
    }

    public MediaPlayer getInGameTunePlayer() {
        return inGameTunePlayer;
    }

    public MediaPlayer getJumpingPlayer() {
        return jumpingPlayer;
    }

    public MediaPlayer getFallingPlayer() {
        return fallingPlayer;
    }

    public MediaPlayer getGameOverPlayer(){
        return gameOverPlayer;
    }

    public Music(){
        URL resource = getClass().getResource(Constants.tunePath);
        Media media = new Media(resource.toString());
        this.tunePlayer = new MediaPlayer(media);

        resource = getClass().getResource(Constants.inGameTunePath);
        media = new Media(resource.toString());
        this.inGameTunePlayer = new MediaPlayer(media);

        resource = getClass().getResource(Constants.jumpingPath);
        media = new Media(resource.toString());
        this.jumpingPlayer = new MediaPlayer(media);

        resource = getClass().getResource(Constants.fallingPath);
        media = new Media(resource.toString());
        this.fallingPlayer = new MediaPlayer(media);

        resource = getClass().getResource(Constants.gameOverPath);
        media = new Media(resource.toString());
        this.gameOverPlayer = new MediaPlayer(media);

        resource = getClass().getResource(Constants.diePath);
        media = new Media(resource.toString());
        this.diePlayer = new MediaPlayer(media);
    }

    public void playTune(){
        tunePlayer.setVolume(0.04);
        tunePlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                tunePlayer.seek(Duration.ZERO);
            }
        });
        tunePlayer.play();
    }

    public void playInGameTune(){
        inGameTunePlayer.setVolume(0.04);
        inGameTunePlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                inGameTunePlayer.seek(Duration.ZERO);
            }
        });
        inGameTunePlayer.play();
    }

    public void playJumping(){
        jumpingPlayer.setVolume(0.04);
        jumpingPlayer.setRate(2.0);
        jumpingPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                jumpingPlayer.seek(Duration.ZERO);
            }
        });
        jumpingPlayer.play();
    }

    public void playFalling(){
        fallingPlayer.setVolume(0.04);
        fallingPlayer.setRate(2.0);
        fallingPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                fallingPlayer.seek(Duration.ZERO);
            }
        });
        fallingPlayer.play();
    }

    public void playGameOver(){
        gameOverPlayer.setVolume(0.04);
        if (!gameOverPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
            gameOverPlayer.seek(Duration.ZERO);
        }
        gameOverPlayer.play();
    }


    public void playDie(){
        diePlayer.setVolume(0.04);
        gameOverPlayer.setRate(2.0);
        if (!diePlayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
            diePlayer.seek(Duration.ZERO);
        }
        diePlayer.play();
    }


}
