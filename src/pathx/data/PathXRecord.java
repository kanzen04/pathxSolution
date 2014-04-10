/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.data;

import java.util.HashMap;

/**
 * Holds the stats of the player for gameplay including the current balance,
 * levels unlocked, and specials unlocked.
 * @author Andrew
 */
public class PathXRecord {
    private int balance;
    private HashMap<String, PathXLevel>  levels;
    private HashMap<String, Boolean> specials;

    //Used for updating the player's current balance.
    public void setBalance(int balance) {
        this.balance = balance;
    }

    //Used for updating the player's unlocked levels.
    public void setLevels(HashMap<String, PathXLevel> levels) {
        this.levels = levels;
    }

    //Used for updating the player's unlocked specials.
    public void setSpecials(HashMap<String, Boolean> specials) {
        this.specials = specials;
    }
    
    
}
