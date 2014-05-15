/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import java.util.ArrayList;
import pathx.ui.Node;
import mini_game.Sprite;

/**
 * This Car class lays out the template for any other cars to be used in the game.
 * This Car class is what the player will be using as his/her get away car. 
 * Enemy cars in game will be descendants of this class.
 * @author Dawa Lama 
 */
public class Car {
    
    //The associated sprite
    private Sprite s;
    
    //Pixel positions in game
    private float xPos = s.getX();
    private float yPos = s.getY();
    
    private boolean flat_tire = false;
    private boolean empty_gas = false;
    //Speed of the sprite
    private double speed;
    
    
    //Intersection the sprite can be at at any time
    private Node intersection;
    
    
    
    //The current state of the car. This is mostly used for special status.
    private String currentState;
    
    //Path to take;
    private ArrayList<Node> path;
    private int pathIndex;
    
    /*Associates this Car with an already created Sprite and designates it's
    * starting intersection in-game.
    */
    public Car(Sprite s, Node startSpot, String state){
        this.s = s;
        intersection = startSpot;
        currentState = state;
    }
    
    public Sprite getSprite(){
        return s;
    }
    
    public void flattenTire() {
        flat_tire = true;
    }
    
    public void emptyGas() {
        empty_gas = true;
    }
    
    public void changeDestination(ArrayList<Node> path){
        this.path = path;
        pathIndex = 0;
    }
    
    //This method will be overidden and defined for other, AI-driven cars in game.
    public ArrayList<Node> generatePath(){
        return null;
    }
}
