/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import mini_game.MiniGameDataModel;
import pathx.data.PathXLevel;

/**
 * This class will manage all of the event handling for the game. Namely the 
 * game's various specials.
 * @author Andrew
 */
public class PathXEventHandler {
    
    PathXMiniGame game;
    MiniGameDataModel dataModel;
    
    public PathXEventHandler(PathXMiniGame initGame){
        game = initGame;
        dataModel = initGame.getDataModel();
    }
    
    public void switchToMainMenu(){
        
    }
    
    //Resets the player's record.
    public void resetRequest(){
        
    }
    
    public void switchToSettingsMenu(){
        
    }
    
    public void switchToHelpView(){
        
    }
    
    //Should save the player record before quitting.
    public void quitGameRequest(){
        
    }
    
    public void displayLevelInfoRequest(PathXLevel level){
        
    }
    
    public void closeLevelDialog(){
        
    }
    
    //Triggered when the "try again" option is chosen after a level completion
    //or failure.
    public void resetLevel(){
        
    }
    //Will either scroll the level select or game level view.
    public void scrollUpRequest(){
        
    }
    
    //Will either scroll the level select or game level view.
    public void scrollDownRequest(){
        
    }
    
    //Will either scroll the level select or game level view.
    public void scrollLeftRequest(){
        
    }
    
    //Will either scroll the level select or game level view.
    public void scrollRightRequest(){
        
    }
    
    public void toggleSoundRequest(){
        
    }
    
    public void toggleMusicRequest(){
        
    }
    
    public void changeGameSpeed(double multiplier){
        
    }
    
    //GAME SPECIALS
    public void makeLightGreen(Node node){
        
    }
    
    public void makeLightRed(Node node){
        
    }
    
    public void freezeTime(){
        
    }
    
    public void decreaseRoadSpeed(Road road){
        
    }
    
    public void increaseRoadSpeed(Road road){
        
    }
    
    public void increasePlayerSpeed(){
        
    }
    
    public void flattenTires(Car car){
        
    }
    
    public void emptyGasTank(Car car){
        
    }
    
    public void closeRoad(Road road){
        
    }
    
    public void closeIntersection(Node node){
        
    }
    
    public void openIntersection(Node node){
        
    }
    
    public void steal(){
        
    }
    
    //Will use changeDestination within the car class.
    public void mindControl(Car car){
        
    }
    
    public void intangibility(){
        
    }
    
    public void mindlessTerror(Car car){
        
    }
    
    public void fly(){
        
    }
    
    public void godMode(){
        
    }
}
