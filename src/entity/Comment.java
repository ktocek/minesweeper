package entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private String game;
    private String username;
    private String comment;
    private Date playedOn;

    public Comment(String game, String username, String comment, Date playedOn) {
        this.game = game;
        this.username = username;
        this.comment = comment;
        this.playedOn = playedOn;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "game='" + game + '\'' +
                ", username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", playedOn=" + playedOn +
                '}' + '\n';
    }
}
