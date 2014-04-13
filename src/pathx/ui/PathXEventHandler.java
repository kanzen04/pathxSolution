/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import java.awt.event.KeyEvent;
import mini_game.MiniGameDataModel;
import mini_game.Sprite;
import mini_game.Viewport;
import pathx.PathXConstants;
import static pathx.PathXConstants.LEVEL_SELECT_SCREEN_STATE;
import pathx.data.PathXLevel;

/**
 * This class will manage all of the event handling for the game. Namely the 
 * game's various specials.
 * @author Andrew
 */
public class PathXEventHandler {
    
    PathXMiniGame game;
    MiniGameDataModel dataModel;
    Viewport vp;
    
    public PathXEventHandler(PathXMiniGame initGame){
        game = initGame;
        dataModel = initGame.getDataModel();
        vp = dataModel.getViewport();
    }
    
    /**
     * Called when the user clicks the close window button.
     */    
    public void respondToExitRequest()
    {
        // IF THE GAME IS STILL GOING ON, END IT AS A LOSS
        if (game.getDataModel().inProgress())
        {
            game.getDataModel().endGameAsLoss();
        }
        // AND CLOSE THE ALL
        System.exit(0);        
    }
    
    public void switchToMainMenu(){
        if (game.isCurrentScreenState(PathXConstants.LEVEL_SELECT_SCREEN_STATE) ||
                game.isCurrentScreenState(PathXConstants.SETTINGS_SCREEN_STATE)){
            game.switchToMainMenu();
        }
    }
    
    public void switchToLevelSelectScreen(){
        if (game.isCurrentScreenState(PathXConstants.MENU_SCREEN_STATE)){
            game.switchToLevelSelectScreen();
        }
    }
    
    //Resets the player's record.
    public void resetRequest(){
        
    }
    
    public void switchToSettingsMenu(){
        if (game.isCurrentScreenState(PathXConstants.MENU_SCREEN_STATE)){
            game.switchToSettingsScreen();
        }
    }
    
    public void switchToHelpView(){
        
    }
    
    //Should save the player record before quitting.
    public void quitGameRequest(){
        respondToExitRequest();
    }
    
    public void displayLevelInfoRequest(PathXLevel level){
        
    }
    
    public void closeLevelDialog(){
        
    }
    
    public void startLevelRequest(){
        
    }
    
    //Triggered when the "try again" option is chosen after a level completion
    //or failure.
    public void resetLevel(){
        
    }
    //Will either scroll the level select or game level view.
    public void scrollUpRequest(){
        if (vp.getMinViewportY() < vp.getViewportY() - 4) {
            dataModel.getViewport().scroll(0, -4);
        }
    }
    
    //Will either scroll the level select or game level view.
    public void scrollDownRequest(){
        if (vp.getMaxViewportY() > vp.getViewportY() + 4) {
            dataModel.getViewport().scroll(0, 4);
        }
    }
    
    //Will either scroll the level select or game level view.
    public void scrollLeftRequest(){
        if (vp.getMinViewportX() < vp.getViewportX() - 4) {
            dataModel.getViewport().scroll(-4, 0);
        }
    }
    
    //Will either scroll the level select or game level view.
    public void scrollRightRequest(){
        if (vp.getMaxViewportX() > vp.getViewportX() + 4) {
            dataModel.getViewport().scroll(4, 0);
        }
    }
    
    public void toggleSoundRequest(){
        //TODO Toggle the game's sound
        Sprite soundToggle = game.getGUIButtons().get(PathXConstants.SOUND_TOGGLE_BUTTON_TYPE);
        if (soundToggle.getState().equals(PathXSpriteState.ENABLED.toString()))
            soundToggle.setState(PathXSpriteState.DISABLED.toString());
        else
            soundToggle.setState(PathXSpriteState.ENABLED.toString());
    }
    
    public void toggleMusicRequest(){
        //TODO Toggle the game's music
        Sprite musicToggle = game.getGUIButtons().get(PathXConstants.MUSIC_TOGGLE_BUTTON_TYPE);
        if (musicToggle.getState().equals(PathXSpriteState.ENABLED.toString()))
            musicToggle.setState(PathXSpriteState.DISABLED.toString());
        else
            musicToggle.setState(PathXSpriteState.ENABLED.toString());
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

    void respondToKeyPress(int keyCode) {
        //Right key press on level select screen
        if (keyCode == KeyEvent.VK_RIGHT){
            if (game.isCurrentScreenState(LEVEL_SELECT_SCREEN_STATE)){
                scrollRightRequest();
            }
        }else if (keyCode == KeyEvent.VK_LEFT){
            if (game.isCurrentScreenState(LEVEL_SELECT_SCREEN_STATE)){
                scrollLeftRequest();
            }
        }else if (keyCode == KeyEvent.VK_UP){
            if (game.isCurrentScreenState(LEVEL_SELECT_SCREEN_STATE)){
                scrollUpRequest();
            }
        }else if (keyCode == KeyEvent.VK_DOWN){
            if (game.isCurrentScreenState(LEVEL_SELECT_SCREEN_STATE)){
                scrollDownRequest();
            }
        }
    }
}
