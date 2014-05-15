/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.data;

import mini_game.Viewport;
import static pathx.PathXConstants.VIEWPORT_X;
import static pathx.PathXConstants.VIEWPORT_Y;
import pathx.ui.Node;

/**
 * This class will be used to hold information for every level in the pathX 
 * Mini-Game. Notably, the graph data structure will be stored here.
 * @author Dawa Lama
 */
public class PathXLevel {
    
    private String levelName;
    private int reward;
    //Coordinates of the level on the level select map.
    private int xPos, yPos;
    //Whatever graph implementation I'll be using.
    //private Graph graph;
    private boolean completed;
    private Node startNode;
    private PathXDataModel dataModel;
    
    
    public PathXLevel(String levelName, int reward, int xPos, int yPos, boolean completed, PathXDataModel data){
        this.dataModel = data;
        this.levelName = levelName;
        this.reward = reward;
        this.xPos = xPos;
        this.yPos = yPos;
        this.completed = completed;
    }
//    public ArrayList<Node> findPath(Node from, Node to){{
//        
//    }
    
//    public boolean hasNode(Node node){
//        
//    }
    
    public Node getStartNode(){
        return startNode;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getReward() {
        return reward;
    }

    public int getxPos() {
        return VIEWPORT_X + xPos - dataModel.getViewport().getViewportX();
    }

    public int getyPos() {
        return VIEWPORT_Y + yPos - dataModel.getViewport().getViewportY();
    }

    public boolean isCompleted() {
        return completed;
    }
}
