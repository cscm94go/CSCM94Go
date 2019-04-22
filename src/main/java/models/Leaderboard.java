package models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
@Table("leaderboard")


/**
 * The class Leaderboard allows a users ranking.
 * This is to register wins and losses,
 * and to calculate rates of wins and losses.
 * @author  Idris.
 * @version 1
 */
public class Leaderboard extends Model {
    String username;
    int wins, losses;
    float winlossPercentage;

    /**
     * This gets the user name.
     * @return the user name.
     */
    public String getUsername() {
        return username;
    }
    /**
     * This sets the user name.
     * @param  username the user name.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * This gets the user's wins.
     * @return  the amount of wins.
     */
    public int getWins() {
        return wins;
    }
    /**
     * This sets the user's wins.
     * @param wins amount of wins.
     */
    public void setWins(int wins) {
        this.wins = wins;
    }
    /**
     * This gets the user's losses.
     * @return the amount of losses.
     */
    public int getLosses() {
        return losses;
    }
    /**
     * This sets the user's losses.
     * @param losses amount of losses.
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }
    /**
     * This gets the user's wins as a percentage
     * over all, wins and losses.
     * @return   the percentage of wins.
     */
    public float getWinlossPercentage() {
        winlossPercentage = (wins / (wins + losses)) * 100;
        return winlossPercentage;
    }

    /**
     * This sets the user's wins as a percentage
     * over all, wins and losses.
     * @param winlossPercentage the percentage of wins.
     */
    public void setWinlossPercentage(float winlossPercentage) {
        this.winlossPercentage = winlossPercentage;
    }
    /**
     * Class constructor.
     * @param  username Description text text text.
     * @param wins amount of wins.
     * @param losses amount of losses.
     * @param winlossPercentage the percentage of wins.
     */
    public  Leaderboard(String username, int wins, int losses, float winlossPercentage){
        this.username = username;
        this.wins = wins;
        this.losses = losses;
        this.winlossPercentage = winlossPercentage;
    }
}
