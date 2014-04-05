/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import mini_game.Sprite;

/**
 *
 * @author Andrew
 */
public class BanditCar extends Car{

    public BanditCar(Sprite s, Node startSpot, String state) {
        super(s, startSpot, state);
    }
    
    @Override
    public ArrayList<Node> generatePath(){
        //TODO Create algorithm for finding a BanditCar's path.
    }
}
