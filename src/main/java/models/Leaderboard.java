package models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
@Table("leaderboard")

public class Leaderboard extends Model {
    String username;
    int wins, losses;
    float winlossPercentage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public float getWinlossPercentage() {
        return winlossPercentage;
    }

    public void setWinlossPercentage(float winlossPercentage) {
        this.winlossPercentage = winlossPercentage;
    }

    public  Leaderboard(String username, int wins, int losses, float winlossPercentage){
        this.username = username;
        this.wins = wins;
        this.losses = losses;
        this.winlossPercentage = winlossPercentage;

    }
}
