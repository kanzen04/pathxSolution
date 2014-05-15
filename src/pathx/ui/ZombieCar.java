/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import java.util.ArrayList;
import mini_game.Sprite;

/**
 *
 * @author Dawa Lama
 */
public class ZombieCar extends Car{

    public ZombieCar(Sprite s, Node startSpot, String state) {
        super(s, startSpot, state);
    }
    
    @Override
    public ArrayList<Node> generatePath(){
        return null;
        //TODO create algorithm for finding ZombieCar paths.
    }
}
