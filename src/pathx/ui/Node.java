/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import pathX.*;
import mini_game.*;
import java.util.ArrayList;

/**
 * This node class represents intersections in-game and will manage nodes in a
 * corresponding levels graph.
 * @author Andrew
 */
public class Node {
    
    private Sprite s;
    
    //Pixel position in game.
    private float xPos = s.getX();
    private float yPos = s.getY();
    
    //A list of roads this Node is connected to.
    private ArrayList<Road> roads;
    
    //The current state of this node be it GREEN, RED or CLOSED.
    private String currentState;
    
    public Node(Sprite s, String state){
        this.s = s;
        currentState = state;
    }
    
    public Node(Sprite s, String state, ArrayList<Road> roads){
        this(s, state);
        this.roads = roads;
    }
    
    public void makeGreen(){
        setCurrentState(NodeState.GREEN.toString());
    }
    
    public void makeRed(){
        setCurrentState(NodeState.RED.toString());
    }
    
    public void close(){
        setCurrentState(NodeState.CLOSED.toString());       
    }
    
    public boolean hasRoad(Road r){
        for (Road road : roads){
            if (road == r) return true;
        }
        return false;
    }

    public Sprite getSprite() {
        return s;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
    
}
