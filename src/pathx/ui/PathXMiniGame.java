/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JFrame;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import pathx.PathX.PathXPropertyType;
import pathx.PathXConstants;
import static pathx.PathXConstants.*;
import pathx.data.PathXDataModel;
import pathx.data.PathXLevel;
import pathx.data.PathXRecord;
import pathx.file.PathXFileManager;
import static pathx.ui.PathXSpriteState.*;
import properties_manager.PropertiesManager;

/**
 *
 * @author Dawa Lama
 */
public class PathXMiniGame extends MiniGame{
    
    //Manages game data including the player's record, different cars in-game,
    //private PathXDataModel dataModel;
    
    //Holds the stats for the current player including balance, unlocked levels,
    //and unlocked specials.
    private PathXRecord record;
    
    //Handles mostly UI events, namely special activations and screen switching.
    private PathXEventHandler eventHandler;
    
    //Handles errors that will probably occur when loading from property XML
    //files images and other things.
    private PathXErrorHandler errorHandler;
    
    private Viewport levelSelect;
    //Loads and saves player records.
    private PathXFileManager fileManager;
    
    //Indicates the current screen being displayed.
    String screenState;
    
    public PathXErrorHandler getErrorHandler(){
        return errorHandler;
    }
    
    //This method is used to get images of a locked, incomplete, or complete level
    // node for the level select screen.
    public BufferedImage getLevelNodeImage(String levelStatus){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
        switch (levelStatus) {
            case PathXConstants.LOCKED_LEVEL_TYPE:
                return loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_LOCKED_LEVEL));
            case PathXConstants.INCOMPLETE_LEVEL_TYPE:
                return loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_INCOMPLETE_LEVEL));
            case PathXConstants.COMPLETE_LEVEL_TYPE:
                return loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_COMPLETED_LEVEL));
            default:
                return null;
        }
    }
    
    public boolean isCurrentScreenState(String screenState){
        return this.screenState.equals(screenState);
    }
    
    public void switchToLevelSelectScreen(){
        //PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(LEVEL_SELECT_SCREEN_STATE);
        
        //DEACTIVATE POPUP DIALOG AND CLOSE BUTTON
        guiDecor.get(GAME_POPUP_TYPE).setState(INVISIBLE.toString());
        guiDecor.get(GAME_POPUP_TYPE).setEnabled(false);
        guiButtons.get(CLOSE_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(CLOSE_BUTTON_TYPE).setEnabled(false);
        
        //ACTIVE NORTH PANEL CONTROLS
        guiButtons.get(BACK_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(BACK_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(QUIT_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(QUIT_BUTTON_TYPE).setEnabled(true);
        //ACTIVATE ARROW BUTTONS
        guiButtons.get(UP_ARROW_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(UP_ARROW_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(DOWN_ARROW_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(DOWN_ARROW_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(RIGHT_ARROW_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(RIGHT_ARROW_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(LEFT_ARROW_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(LEFT_ARROW_BUTTON_TYPE).setEnabled(true);
        
        //Activatate the Level buttons
        ArrayList<PathXLevelSprite> levelSprites = ((PathXDataModel)data).getLevelSprites();
        for (PathXLevelSprite ls : levelSprites){
            guiButtons.put(ls.getName(), ls.getSprite());
        }
        
        if (screenState.equals(MENU_SCREEN_STATE)) {
            //DEACTIVATE MAIN MENU BUTTONS
            guiButtons.get(PLAY_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(RESET_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(RESET_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(SETTINGS_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(SETTINGS_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(HELP_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(HELP_BUTTON_TYPE).setEnabled(false);

            screenState = LEVEL_SELECT_SCREEN_STATE;
        } else {
            //DEACTIVATE GAME SCREEN BUTTONS
            guiButtons.get(GAME_BACK_BUTTON_TYPE).setState(INVISIBLE.toString());
            guiButtons.get(GAME_BACK_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(GAME_QUIT_BUTTON_TYPE).setState(INVISIBLE.toString());
            guiButtons.get(GAME_QUIT_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(START_BUTTON_TYPE).setState(INVISIBLE.toString());
            guiButtons.get(START_BUTTON_TYPE).setEnabled(false);
            deactivateSpecialButtons();
            
            screenState = LEVEL_SELECT_SCREEN_STATE;
        }
        //SONGS
    }
    
    public void switchToGameScreen(){
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(GAME_SCREEN_STATE);
        //ACTIVATE POPUP DIALOG AND CLOSE BUTTON
        guiDecor.get(GAME_POPUP_TYPE).setState(VISIBLE.toString());
        guiDecor.get(GAME_POPUP_TYPE).setEnabled(true);
        guiButtons.get(CLOSE_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(CLOSE_BUTTON_TYPE).setEnabled(true);
        
        //Activate Game Buttons
        guiButtons.get(GAME_BACK_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(GAME_BACK_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(GAME_QUIT_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(GAME_QUIT_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(START_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(START_BUTTON_TYPE).setEnabled(true);
        activateSpecialButtons();
        
        //ACTIVATE POPUP DIALOG AND CLOSE BUTTON
        guiDecor.get(GAME_POPUP_TYPE).setState(VISIBLE.toString());
        guiDecor.get(GAME_POPUP_TYPE).setEnabled(true);
        guiButtons.get(CLOSE_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(CLOSE_BUTTON_TYPE).setEnabled(true);
        
        //DEACTIVATE LEVEL SELECT BUTTONS
        guiButtons.get(BACK_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(BACK_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(QUIT_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(QUIT_BUTTON_TYPE).setEnabled(false);
        
        ArrayList<PathXLevelSprite> levelSprites = ((PathXDataModel)data).getLevelSprites();
        for (PathXLevelSprite ls : levelSprites)
            guiButtons.remove(ls.getName());
        
        screenState = GAME_SCREEN_STATE;
    }
    
    public void switchToMainMenu(){
        //PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(MENU_SCREEN_STATE);
        
        //DEACTIVATE POPUP DIALOG AND CLOSE BUTTON
        guiDecor.get(GAME_POPUP_TYPE).setState(INVISIBLE.toString());
        guiDecor.get(GAME_POPUP_TYPE).setEnabled(false);
        guiButtons.get(CLOSE_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(CLOSE_BUTTON_TYPE).setEnabled(false);
        
        //Activate Menu Buttons
        guiButtons.get(PLAY_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(RESET_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(SETTINGS_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(SETTINGS_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(HELP_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(QUIT_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(QUIT_BUTTON_TYPE).setEnabled(true);
        
        //If we are switching from the level select screen or settings screen.
        if (screenState.equals(LEVEL_SELECT_SCREEN_STATE)) {
            //Deactivate level select buttons
            guiButtons.get(BACK_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(BACK_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(UP_ARROW_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(UP_ARROW_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(DOWN_ARROW_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(DOWN_ARROW_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(RIGHT_ARROW_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(RIGHT_ARROW_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(LEFT_ARROW_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(LEFT_ARROW_BUTTON_TYPE).setEnabled(false);
            
            //Deactivate the level buttons
            ArrayList<PathXLevelSprite> levelSprites = ((PathXDataModel) data).getLevelSprites();
            for (PathXLevelSprite ls : levelSprites) {
                guiButtons.remove(ls.getName());
            }
        } else if (screenState.equals(SETTINGS_SCREEN_STATE)){
            //Deactivate settings screen buttons
            guiButtons.get(SOUND_TOGGLE_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(SOUND_TOGGLE_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(MUSIC_TOGGLE_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(MUSIC_TOGGLE_BUTTON_TYPE).setEnabled(false);
            guiButtons.get(BACK_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
            guiButtons.get(BACK_BUTTON_TYPE).setEnabled(false);
        }
        screenState = MENU_SCREEN_STATE;
    }
    
    public void switchToSettingsScreen(){
        //PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(SETTINGS_SCREEN_STATE);
        
        //DEACTIVATE POPUP DIALOG AND CLOSE BUTTON
        guiDecor.get(GAME_POPUP_TYPE).setState(INVISIBLE.toString());
        guiDecor.get(GAME_POPUP_TYPE).setEnabled(false);
        guiButtons.get(CLOSE_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(CLOSE_BUTTON_TYPE).setEnabled(false);
        
        //DEACTIVE MAIN MENU BUTTONS
        guiButtons.get(PLAY_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESET_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SETTINGS_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
        guiButtons.get(SETTINGS_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(HELP_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(false);
        
        //ACTIVATE SETTINGS BUTTONS
        guiButtons.get(SOUND_TOGGLE_BUTTON_TYPE).setState(PathXSpriteState.DISABLED.toString());
        guiButtons.get(SOUND_TOGGLE_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(MUSIC_TOGGLE_BUTTON_TYPE).setState(PathXSpriteState.DISABLED.toString());
        guiButtons.get(MUSIC_TOGGLE_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(BACK_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(BACK_BUTTON_TYPE).setEnabled(true);
        
        screenState = SETTINGS_SCREEN_STATE;
    }
    
    public void switchToHelpScreen() {
        
         // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(HELP_SCREEN_STATE);
        
        //DEACTIVATE POPUP DIALOG AND CLOSE BUTTON
        guiDecor.get(GAME_POPUP_TYPE).setState(INVISIBLE.toString());
        guiDecor.get(GAME_POPUP_TYPE).setEnabled(false);
        guiButtons.get(CLOSE_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(CLOSE_BUTTON_TYPE).setEnabled(false);
        
        //ACTIVATE QUIT AND BACK BUTTONS
        guiButtons.get(QUIT_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(QUIT_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(BACK_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(BACK_BUTTON_TYPE).setEnabled(true);
        
        //DEACTIVE MAIN MENU BUTTONS
        guiButtons.get(PLAY_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(RESET_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(SETTINGS_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
        guiButtons.get(SETTINGS_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(HELP_BUTTON_TYPE).setState(PathXSpriteState.INVISIBLE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(false);
        
        screenState = HELP_SCREEN_STATE;
    }
    
    @Override
    public void initData(){
        //Initialize error handlier
        errorHandler = new PathXErrorHandler(window);
        
        
        //Load a saved player record.
        //record = fileManager.loadRecord();
        
        //Initialize the data model.
        data = new PathXDataModel(this);
                
        //Initialize file manager.
        fileManager = new PathXFileManager(this);
        
        //Load the PathXLevels from the XML file.
//        fileManager.loadLevelDetails();
//        ((PathXDataModel)data).updateRecord();
    }

    @Override
    public void initAudioContent() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initGUIControls() {
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT;
        Sprite s;
        
        // FIRST PUT THE ICON IN THE WINDOW
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);        
        String windowIconFile = props.getProperty(PathXPropertyType.IMAGE_WINDOW_ICON);
        img = loadImage(imgPath + windowIconFile);
        window.setIconImage(img);
        
        // CONSTRUCT THE PANEL WHERE WE'LL DRAW EVERYTHING
        canvas = new PathXPanel(this, (PathXDataModel)data);
        
        // LOAD THE BACKGROUNDS, WHICH ARE GUI DECOR
        screenState = PathXConstants.MENU_SCREEN_STATE;
        
        sT = new SpriteType(BACKGROUND_TYPE);
        
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_MENU));
        sT.addState(MENU_SCREEN_STATE, img); 
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_LEVEL));
        sT.addState(LEVEL_SELECT_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_GAME));
        sT.addState(GAME_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_SETTINGS));
        sT.addState(SETTINGS_SCREEN_STATE, img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BACKGROUND_HELP));
        sT.addState(HELP_SCREEN_STATE, img);
        
        s = new Sprite(sT, 0, 0, 0, 0, MENU_SCREEN_STATE);
        guiDecor.put(BACKGROUND_TYPE, s);
        
        initMenuButtons();
        initLevelSelectButtons();
        initGameButtons();
        initSettingsButtons();
         
    }
    
    private void initMenuButtons(){
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT;
        Sprite s;
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
        
        //ADD MAIN MENU BUTTONS
        //PLAY BUTTON
        sT = new SpriteType(PLAY_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_PLAY));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_PLAY_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        s = new Sprite(sT, PLAY_BUTTON_X, PLAY_BUTTON_Y, 0, 0, VISIBLE.toString());
        guiButtons.put(PLAY_BUTTON_TYPE, s);
        
        //RESET BUTTON
        sT = new SpriteType(RESET_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_RESET));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_RESET_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PLAY_BUTTON_X + MENU_BUTTON_WIDTH + MENU_BUTTON_GAP;
        y = PLAY_BUTTON_Y;
        s = new Sprite(sT, x, y, 0, 0, VISIBLE.toString());
        guiButtons.put(RESET_BUTTON_TYPE, s);
        
        //SETTINGS BUTTON
        sT = new SpriteType(SETTINGS_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_SETTINGS));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_SETTINGS_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PLAY_BUTTON_X + (2 * MENU_BUTTON_WIDTH) + (2 * MENU_BUTTON_GAP);
        s = new Sprite(sT, x, y, 0, 0, VISIBLE.toString());
        guiButtons.put(SETTINGS_BUTTON_TYPE, s);
        
        //HELP BUTTON
        sT = new SpriteType(HELP_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_HELP));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_HELP_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PLAY_BUTTON_X + (3 * MENU_BUTTON_WIDTH) + (3 * MENU_BUTTON_GAP);
        s = new Sprite(sT, x, y, 0, 0, VISIBLE.toString());
        guiButtons.put(HELP_BUTTON_TYPE, s);
        
        //QUIT BUTTON
        sT = new SpriteType(QUIT_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_QUIT));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_QUIT_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = MAIN_QUIT_BUTTON_X;
        y = MAIN_QUIT_BUTTON_Y;
        s = new Sprite(sT, x, y, 0, 0, VISIBLE.toString());
        guiButtons.put(QUIT_BUTTON_TYPE, s);
    }
    
    private void initLevelSelectButtons(){
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT;
        Sprite s;
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
        
        //LEVEL SELECT SCREEN BUTTONS
        //BACK BUTTON
        sT = new SpriteType(BACK_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_BACK));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_BACK_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PathXConstants.LEVEL_SELECT_BACK_BUTTON_X - 60;
        y = PathXConstants.MAIN_QUIT_BUTTON_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(BACK_BUTTON_TYPE, s);
        
        //ARROW BUTTONS
        //UP ARROW
        sT = new SpriteType(UP_ARROW_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_UP_ARROW));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        x = UP_ARROW_X;
        y = UP_ARROW_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(UP_ARROW_BUTTON_TYPE, s);
        
        //DOWN ARROW
        sT = new SpriteType(DOWN_ARROW_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_DOWN_ARROW));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        s = new Sprite(sT, DOWN_ARROW_X, DOWN_ARROW_Y, 0, 0, INVISIBLE.toString());
        guiButtons.put(DOWN_ARROW_BUTTON_TYPE, s);
        
        //LEFT ARROW
        sT = new SpriteType(LEFT_ARROW_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_LEFT_ARROW));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        s = new Sprite(sT, LEFT_ARROW_X, LEFT_ARROW_Y, 0, 0, INVISIBLE.toString());
        guiButtons.put(LEFT_ARROW_BUTTON_TYPE, s);
        
        //RIGHT ARROW
        sT = new SpriteType(RIGHT_ARROW_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_RIGHT_ARROW));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        s = new Sprite(sT, RIGHT_ARROW_X, RIGHT_ARROW_Y, 0, 0, INVISIBLE.toString());
        guiButtons.put(RIGHT_ARROW_BUTTON_TYPE, s);
        
        //THE VIEWPORT
        initLevelSelectViewport();
        
        // KEY LISTENER - LET'S US PROVIDE CUSTOM RESPONSES
        this.setKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke)
            {   
                getEventHandler().respondToKeyPress(ke.getKeyCode());    
            }
        });
    }
    
    private void initGameButtons(){
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT;
        Sprite s;
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
        
        //GAME SCREEN BUTTONS
        //BACK BUTTON
        sT = new SpriteType(GAME_BACK_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_BACK));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_BACK_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PathXConstants.GAME_BACK_BUTTON_X;
        y = PathXConstants.GAME_BACK_BUTTON_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(GAME_BACK_BUTTON_TYPE, s);
        
        //QUIT BUTTON
        sT = new SpriteType(GAME_QUIT_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_QUIT));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_QUIT_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PathXConstants.GAME_QUIT_BUTTON_X;
        y = PathXConstants.GAME_QUIT_BUTTON_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(GAME_QUIT_BUTTON_TYPE, s);
        
        //START BUTTON
        sT = new SpriteType(START_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_START));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_START_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PathXConstants.GAME_START_BUTTON_X;
        y = PathXConstants.GAME_START_BUTTON_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(START_BUTTON_TYPE, s);
        
        //GAME SPECIALS GO HERE.
        //Make Light Green Button
        sT = new SpriteType(MAKE_GREEN_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_MAKE_GREEN));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_MAKE_GREEN_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X;
        y = PathXConstants.FIRST_SPECIAL_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(MAKE_GREEN_BUTTON_TYPE, s);
        
        //Make Light Red Button
        sT = new SpriteType(MAKE_RED_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_MAKE_RED));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_MAKE_RED_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + GAME_SPECIAL_WIDTH;
        y = PathXConstants.FIRST_SPECIAL_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(MAKE_RED_BUTTON_TYPE, s);
        
        //Freeze Time Button
        sT = new SpriteType(FREEZE_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_FREEZE_TIME));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_FREEZE_TIME_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH * 2);
        y = PathXConstants.FIRST_SPECIAL_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(FREEZE_BUTTON_TYPE, s);
        
        //INCREASE SPEED BUTTON
        sT = new SpriteType(INCREASE_SPEED_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_INCREASE_SPEED));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_INCREASE_SPEED_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH * 3);
        y = PathXConstants.FIRST_SPECIAL_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(INCREASE_SPEED_BUTTON_TYPE, s);
        
        //DECREASE SPEED BUTTON
        sT = new SpriteType(DECREASE_SPEED_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_DECREASE_SPEED));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_DECREASE_SPEED_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X;
        y = PathXConstants.FIRST_SPECIAL_Y + GAME_SPECIAL_HEIGHT;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(DECREASE_SPEED_BUTTON_TYPE, s);
        
        //INCREASE PLAYER SPEED BUTTON
        sT = new SpriteType(INCREASE_PLAYER_SPEED_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_INCREASE_PLAYER_SPEED));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + GAME_SPECIAL_WIDTH;
        y = PathXConstants.FIRST_SPECIAL_Y + GAME_SPECIAL_HEIGHT;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(INCREASE_PLAYER_SPEED_BUTTON_TYPE, s);
        
        //FLAT TIRE BUTTON
        sT = new SpriteType(FLAT_TIRE_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_FLAT_TIRE));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_FLAT_TIRE_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH * 2);
        y = PathXConstants.FIRST_SPECIAL_Y + GAME_SPECIAL_HEIGHT;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(FLAT_TIRE_BUTTON_TYPE, s);
        
        //EMPTY GAS BUTTON
        sT = new SpriteType(EMPTY_GAS_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_EMPTY_GAS));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_EMPTY_GAS_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH * 3);
        y = PathXConstants.FIRST_SPECIAL_Y + GAME_SPECIAL_HEIGHT;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(EMPTY_GAS_BUTTON_TYPE, s);
        
        //CLOSE ROAD BUTTON
        sT = new SpriteType(CLOSE_ROAD_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_CLOSE_ROAD));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_CLOSE_ROAD_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X;
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_HEIGHT * 2);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(CLOSE_ROAD_BUTTON_TYPE, s);
        
        //OPEN INTERSECTION BUTTON
        sT = new SpriteType(OPEN_INTERSECTION_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_OPEN_NODE));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_OPEN_NODE_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + GAME_SPECIAL_WIDTH;
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_WIDTH * 2);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(OPEN_INTERSECTION_BUTTON_TYPE, s);
        
        //CLOSE INTERSECTION BUTTON
        sT = new SpriteType(CLOSE_INTERSECTION_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_CLOSE_NODE));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_CLOSE_NODE_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH * 2);
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_WIDTH * 2);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(CLOSE_INTERSECTION_BUTTON_TYPE, s);
        
        //STEALING BUTTON
        sT = new SpriteType(STEAL_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_STEALING));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_STEALING_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH * 3);
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_WIDTH * 2);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(STEAL_BUTTON_TYPE, s);
        
        //MIND CONTROL BUTTON
        sT = new SpriteType(MIND_CONTROL_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_MIND_CONTROL));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_MIND_CONTROL_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X;
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_HEIGHT * 3);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(MIND_CONTROL_BUTTON_TYPE, s);
        
        //INTANGIBILITY BUTTON
        sT = new SpriteType(INTANGIBILITY_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_INTANGIBILITY));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_INTANGIBILITY_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH);
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_HEIGHT * 3);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(INTANGIBILITY_BUTTON_TYPE, s);
        
        //MINDLESS TERROR BUTTON
        sT = new SpriteType(MINDLESS_TERROR_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_MINDLESS_TERROR));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_MINDLESS_TERROR_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH * 2);
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_WIDTH * 3);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(MINDLESS_TERROR_BUTTON_TYPE, s);
        
        //FLYING BUTTON
        sT = new SpriteType(FLYING_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_FLYING));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_FLYING_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + (GAME_SPECIAL_WIDTH * 3);
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_WIDTH * 3);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(FLYING_BUTTON_TYPE, s);
        
        //INVINCIBILITY BUTTON
        sT = new SpriteType(GOD_MODE_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_GOD_MODE));
        sT.addState(VISIBLE.toString(), img);
        sT.addState(MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_GOD_MODE_ACTIVE));
        sT.addState(ENABLED.toString(), img);
        x = PathXConstants.FIRST_SPECIAL_X + GAME_SPECIAL_WIDTH + 15;
        y = PathXConstants.FIRST_SPECIAL_Y + (GAME_SPECIAL_HEIGHT * 4);
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(GOD_MODE_BUTTON_TYPE, s);
        
        //LEVEL SELECT ARROWS WILL BE USED FOR THE GAME SCREEN AS WELL.
        
        //POPUP OVERLAY DECOR
        sT  = new SpriteType(GAME_POPUP_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_POPUP_BACKGROUND));
        sT.addState(VISIBLE.toString(), img);
        x = PathXConstants.GAME_OVERLAY_X;
        y = PathXConstants.GAME_OVERLAY_Y;
        s = new Sprite(sT, x, y, 0, 0,INVISIBLE.toString());
        guiDecor.put(GAME_POPUP_TYPE, s);
        
        //POPUP CLOSE BUTTON
        sT = new SpriteType(CLOSE_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_CLOSE));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_CLOSE_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PathXConstants.OVERLAY_BUTTON_CLOSE_X;
        y = PathXConstants.OVERLAY_BUTTON_CLOSE_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(CLOSE_BUTTON_TYPE, s);
        
    }

    private void initSettingsButtons(){
        // WE'LL USE AND REUSE THESE FOR LOADING STUFF
        BufferedImage img;
        float x, y;
        SpriteType sT;
        Sprite s;
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imgPath = props.getProperty(PathXPropertyType.PATH_IMG);
        
        //Settings Sound Toggle
        sT = new SpriteType(SOUND_TOGGLE_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_TOGGLE_OFF));
        sT.addState(PathXSpriteState.DISABLED.toString(), img);
        sT.addState(PathXSpriteState.MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_TOGGLE_ON));
        sT.addState(PathXSpriteState.ENABLED.toString(), img);
        x = PathXConstants.SOUND_TOGGLE_X;
        y = PathXConstants.SOUND_TOGGLE_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(SOUND_TOGGLE_BUTTON_TYPE, s);
        
        //Settings Music Toggle
        sT = new SpriteType(MUSIC_TOGGLE_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_TOGGLE_OFF));
        sT.addState(PathXSpriteState.DISABLED.toString(), img);
        sT.addState(PathXSpriteState.MOUSE_OVER.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_TOGGLE_ON));
        sT.addState(PathXSpriteState.ENABLED.toString(), img);
        x = PathXConstants.MUSIC_TOGGLE_X;
        y = PathXConstants.MUSIC_TOGGLE_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(MUSIC_TOGGLE_BUTTON_TYPE, s);
    }
    @Override
    public void initGUIHandlers() {
         // WE'LL RELAY UI EVENTS TO THIS OBJECT FOR HANDLING
        eventHandler = new PathXEventHandler(this);
        
        // WE'LL HAVE A CUSTOM RESPONSE FOR WHEN THE USER CLOSES THE WINDOW
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we) 
            {   getEventHandler().respondToExitRequest(); }
        });
        
        initMainMenuHandlers();
        initLevelSelectHandlers();
        initGameScreenHandlers();
        initSettingsHandlers();
        fileManager.loadLevelDetails();
        ((PathXDataModel)data).updateRecord();
        
    }

    private void initMainMenuHandlers() {
        //Set play button resopnse
        Sprite playButton = guiButtons.get(PLAY_BUTTON_TYPE);
        playButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                { getEventHandler().switchToLevelSelectScreen();  }
        });
        
        //Set reset button response
        Sprite resetButton = guiButtons.get(RESET_BUTTON_TYPE);
        resetButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                { getEventHandler().resetRequest();  }
        });
        
        //Set settings button response
        Sprite settingsButton = guiButtons.get(SETTINGS_BUTTON_TYPE);
        settingsButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   getEventHandler().switchToSettingsMenu(); }
        });
        
        //Set help button response
        Sprite helpButton = guiButtons.get(HELP_BUTTON_TYPE);
        helpButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    getEventHandler().switchToHelpView();    
                }
        });
        
        //Set quit button response
        Sprite quitButton = guiButtons.get(QUIT_BUTTON_TYPE);
        quitButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   getEventHandler().quitGameRequest(); }
        });
    }
    
    private void initLevelSelectHandlers() {
        //Level select back button
        Sprite backButton = guiButtons.get(BACK_BUTTON_TYPE);
        backButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   getEventHandler().switchToMainMenu();    }
        });
        
        //Scroll up arrow button
        Sprite upArrow = guiButtons.get(UP_ARROW_BUTTON_TYPE);
        upArrow.setActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ae)
                {   getEventHandler().scrollUpRequest(); }
        });
        
        //Scroll down arrow button
        Sprite downArrow = guiButtons.get(DOWN_ARROW_BUTTON_TYPE);
        downArrow.setActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {   getEventHandler().scrollDownRequest();   }
        });
        
        //Scroll left arrow button
        Sprite leftArrow = guiButtons.get(LEFT_ARROW_BUTTON_TYPE);
        leftArrow.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    getEventHandler().scrollLeftRequest();   
                }
        });
        
        //Scroll right arrow button
        Sprite rightArrow = guiButtons.get(RIGHT_ARROW_BUTTON_TYPE);
        rightArrow.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    getEventHandler().scrollRightRequest();  
                }
        });
    }
    
    private void initGameScreenHandlers() {
        //Back button
        Sprite backButton = guiButtons.get(GAME_BACK_BUTTON_TYPE);
        backButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   getEventHandler().switchToLevelSelectScreen();   }
        });
        
        //Quit Button
        Sprite quitButton = guiButtons.get(GAME_QUIT_BUTTON_TYPE);
        quitButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   getEventHandler().quitGameRequest();  }
        });
        
        //START BUTTON
        Sprite startButton = guiButtons.get(START_BUTTON_TYPE);
        startButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   getEventHandler().startLevelRequest();   }
        });
        
        //CLOSE DIALOG BUTTON
        Sprite closeButton = guiButtons.get(CLOSE_BUTTON_TYPE);
        closeButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   getEventHandler().closeLevelDialog();   }
        });
        
        //SPECIALS EVENT HANDLERS GO HERE
        // This needs to happen after you select node.
        //To make green 
        Sprite greenButton = guiButtons.get(MAKE_GREEN_BUTTON_TYPE);
        greenButton.setActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent ae)
                { 
                    getEventHandler().makeLightGreen(null);
                }
        });
        
        //To make red
        Sprite redButton = guiButtons.get(MAKE_RED_BUTTON_TYPE);
        redButton.setActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                getEventHandler().makeLightRed(null);
            }
        });
        
        //To freeze time
        Sprite freezeButton = guiButtons.get(FREEZE_BUTTON_TYPE);
        freezeButton.setActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                getEventHandler().freezeTime();
            }
        });
        
        //To decrease road speed
        Sprite decreaseSpeedButton = guiButtons.get(DECREASE_SPEED_BUTTON_TYPE);
        decreaseSpeedButton.setActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                getEventHandler().decreaseRoadSpeed(null);
            }
        });
        
        //To increase road speed
        Sprite increaseSpeedButton = guiButtons.get(INCREASE_SPEED_BUTTON_TYPE);
        increaseSpeedButton.setActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                getEventHandler().increaseRoadSpeed(null);
            }
        });
        
        //To increase player speed
        Sprite increasePlayerSpeedButton = guiButtons.get(INCREASE_PLAYER_SPEED_BUTTON_TYPE);
        increasePlayerSpeedButton.setActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                getEventHandler().increasePlayerSpeed();
            }
        });
        
        //To flatten tires
        Sprite flattenTireButotn = guiButtons.get(FLAT_TIRE_BUTTON_TYPE);
        flattenTireButotn.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().flattenTires(null);
            }
        });
        
        //To empty gas tank
        Sprite emptyGasTankButton = guiButtons.get(EMPTY_GAS_BUTTON_TYPE);
        emptyGasTankButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().emptyGasTank(null);
            }
        });
        
        //To Close road
        Sprite closeRoadButton = guiButtons.get(CLOSE_ROAD_BUTTON_TYPE);
        closeRoadButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().closeRoad(null);
            }
        });
        
        //To close intersection
        Sprite closeIntersectionButton = guiButtons.get(CLOSE_INTERSECTION_BUTTON_TYPE);
        closeIntersectionButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().closeIntersection(null);
            }
        });
        
        //To open intersection
        Sprite openIntersectionButton = guiButtons.get(OPEN_INTERSECTION_BUTTON_TYPE);
        openIntersectionButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().openIntersection(null);
            }
        });
        
        //To steal
        Sprite stealButton = guiButtons.get(STEAL_BUTTON_TYPE);
        stealButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().steal();
            }
        });        
        
        //To mind control
        Sprite mindControlButton = guiButtons.get(MIND_CONTROL_BUTTON_TYPE);
        mindControlButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().mindControl(null);
            }
        });        
        
        //For intangibility 
        Sprite intangibilityButton = guiButtons.get(INTANGIBILITY_BUTTON_TYPE);
        intangibilityButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().intangibility();
            }
        });                
        
        //For mindless terror
        Sprite mindlessTerrorButton = guiButtons.get(MINDLESS_TERROR_BUTTON_TYPE);
        mindlessTerrorButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().mindlessTerror(null);
            }
        });        
        
        //Fly mode
        Sprite flyModeButton = guiButtons.get(FLYING_BUTTON_TYPE);
        flyModeButton.setActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                getEventHandler().fly();
            }
        });

        //God mode
        Sprite godModeButton = guiButtons.get(GOD_MODE_BUTTON_TYPE);
        godModeButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                getEventHandler().godMode();
            }
        });
        
    }
    
    private void initSettingsHandlers(){
        //Sound toggle
        Sprite soundToggle = guiButtons.get(SOUND_TOGGLE_BUTTON_TYPE);
        soundToggle.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    getEventHandler().toggleSoundRequest();  
                }
        });
        
        //Music Toggle
        Sprite musicToggle = guiButtons.get(MUSIC_TOGGLE_BUTTON_TYPE);
        musicToggle.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    getEventHandler().toggleMusicRequest();  
                }
        });
        
        //TODO Game Speed Slider
    }
    
    /**
     * Called when a game is started.
     *
     * @param game
     */
    @Override
    public void reset() {
        data.reset(this);
    }

    @Override
    public void updateGUI() {
        
        // GO THROUGH THE VISIBLE BUTTONS TO TRIGGER MOUSE OVERS
        Iterator<Sprite> buttonsIt = guiButtons.values().iterator();
        while (buttonsIt.hasNext())
        {
            Sprite button = buttonsIt.next();
            
            // ARE WE ENTERING A BUTTON?
            if (button.getState().equals(PathXSpriteState.VISIBLE.toString()))
            {
                if (button.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    button.setState(PathXSpriteState.MOUSE_OVER.toString());
                    
                    //Check if its a level on the level select screen
                    if(button.getSpriteType().getSpriteTypeID().indexOf("LEVEL_TYPE") >= 0){
                        
                        //Find out which level it is
                        Collection<PathXLevel> levels = ((PathXDataModel)data).getLevels().values();
                        for (PathXLevel level : levels){
                            if (level.getxPos() == (int)button.getX() + VIEWPORT_X - data.getViewport().getViewportX() &&
                                    level.getyPos() == (int)button.getY() + VIEWPORT_Y - data.getViewport().getViewportY()){
                                ((PathXPanel)canvas).renderLevelInfo(canvas.getGraphics(), level);
                                break;
                            }
                        }
                        
                    }
                }
            }
            // ARE WE EXITING A BUTTON?
            else if (button.getState().equals(PathXSpriteState.MOUSE_OVER.toString()))
            {
                 if (!button.containsPoint(data.getLastMouseX(), data.getLastMouseY()))
                {
                    button.setState(PathXSpriteState.VISIBLE.toString());
                }
            }
//            Is it a settings toggle?
//            else if (button.getState().equals(PathXSpriteState.DISABLED) || 
//                    button.getState().equals(PathXSpriteState.ENABLED))
        }
        
    }  

    private void initLevelSelectViewport() {
        //Create the viewport
        levelSelect = new Viewport();
        
        //Specify sizes and location for the viewport
        levelSelect.setScreenSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        levelSelect.setViewportSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        levelSelect.setGameWorldSize(MAP_WIDTH, MAP_HEIGHT);
        levelSelect.setNorthPanelHeight(LEVEL_SELECT_NORTH_PANEL_HEIGHT);
        //levelSelect.initViewportMargins();
        levelSelect.updateViewportBoundaries();
        //levelSelect.setViewportSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        
        data.setViewport(levelSelect);
    }

    public PathXEventHandler getEventHandler() {
        return eventHandler;
    }

    private void activateSpecialButtons() {
        guiButtons.get(MAKE_GREEN_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(MAKE_GREEN_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(MAKE_RED_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(MAKE_RED_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(FREEZE_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(FREEZE_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(INCREASE_SPEED_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(INCREASE_SPEED_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(DECREASE_SPEED_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(DECREASE_SPEED_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(INCREASE_PLAYER_SPEED_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(INCREASE_PLAYER_SPEED_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(FLAT_TIRE_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(FLAT_TIRE_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(EMPTY_GAS_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(EMPTY_GAS_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(CLOSE_ROAD_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(CLOSE_ROAD_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(OPEN_INTERSECTION_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(OPEN_INTERSECTION_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(CLOSE_INTERSECTION_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(CLOSE_INTERSECTION_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(STEAL_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(STEAL_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(MIND_CONTROL_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(MIND_CONTROL_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(INTANGIBILITY_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(INTANGIBILITY_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(MINDLESS_TERROR_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(MINDLESS_TERROR_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(FLYING_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(FLYING_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(GOD_MODE_BUTTON_TYPE).setState(VISIBLE.toString());
        guiButtons.get(GOD_MODE_BUTTON_TYPE).setEnabled(true);
    }

    private void deactivateSpecialButtons() {
        guiButtons.get(MAKE_GREEN_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(MAKE_GREEN_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(MAKE_RED_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(MAKE_RED_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(FREEZE_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(FREEZE_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(INCREASE_SPEED_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(INCREASE_SPEED_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(DECREASE_SPEED_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(DECREASE_SPEED_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(INCREASE_PLAYER_SPEED_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(INCREASE_PLAYER_SPEED_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(FLAT_TIRE_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(FLAT_TIRE_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(EMPTY_GAS_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(EMPTY_GAS_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(CLOSE_ROAD_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(CLOSE_ROAD_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(OPEN_INTERSECTION_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(OPEN_INTERSECTION_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(CLOSE_INTERSECTION_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(CLOSE_INTERSECTION_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(STEAL_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(STEAL_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(MIND_CONTROL_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(MIND_CONTROL_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(INTANGIBILITY_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(INTANGIBILITY_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(MINDLESS_TERROR_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(MINDLESS_TERROR_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(FLYING_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(FLYING_BUTTON_TYPE).setEnabled(false);
        guiButtons.get(GOD_MODE_BUTTON_TYPE).setState(INVISIBLE.toString());
        guiButtons.get(GOD_MODE_BUTTON_TYPE).setEnabled(false);
    }

}
