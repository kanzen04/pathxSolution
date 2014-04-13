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
import pathx.data.PathXRecord;
import static pathx.ui.PathXSpriteState.*;
import properties_manager.PropertiesManager;

/**
 *
 * @author Andrew
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
    //private PathXFileManager fileManager;
    
    //Indicates the current screen being displayed.
    String screenState;
    
    public PathXErrorHandler getErrorHandler(){
        return errorHandler;
    }
    
    public boolean isCurrentScreenState(String screenState){
        return this.screenState.equals(screenState);
    }
    
    public void switchToLevelSelectScreen(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(LEVEL_SELECT_SCREEN_STATE);
        
        //ACTIVE NORTH PANEL CONTROLS
        guiButtons.get(BACK_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        
        //ACTIVATE ARROW BUTTONS
        guiButtons.get(UP_ARROW_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(UP_ARROW_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(DOWN_ARROW_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(DOWN_ARROW_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(RIGHT_ARROW_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(RIGHT_ARROW_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(LEFT_ARROW_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(LEFT_ARROW_BUTTON_TYPE).setEnabled(true);
        
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
        
        //SONGS
    }
    
    public void switchToMainMenu(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(MENU_SCREEN_STATE);
        
        //Activate Menu Buttons
        guiButtons.get(PLAY_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(PLAY_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(RESET_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(RESET_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(SETTINGS_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(SETTINGS_BUTTON_TYPE).setEnabled(true);
        guiButtons.get(HELP_BUTTON_TYPE).setState(PathXSpriteState.VISIBLE.toString());
        guiButtons.get(HELP_BUTTON_TYPE).setEnabled(true);
        
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
        } else {
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
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // CHANGE THE BACKGROUND
        guiDecor.get(BACKGROUND_TYPE).setState(SETTINGS_SCREEN_STATE);
        
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
    @Override
    public void initData(){
        //Initialize error handlier
        errorHandler = new PathXErrorHandler(window);
        
        //Initialize file manager.
        //fileManager = new PathXFileManager();
        
        //Load a saved player record.
        //record = fileManager.loadRecord();
        
        //Initialize the data model.
        data = new PathXDataModel(this);
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
                eventHandler.respondToKeyPress(ke.getKeyCode());    
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
        sT = new SpriteType(POPUP_CLOSE_BUTTON_TYPE);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_POPUP_CLOSE));
        sT.addState(VISIBLE.toString(), img);
        img = loadImage(imgPath + props.getProperty(PathXPropertyType.IMAGE_BUTTON_POPUP_CLOSE_MOUSE_OVER));
        sT.addState(MOUSE_OVER.toString(), img);
        x = PathXConstants.OVERLAY_BUTTON_CLOSE_X;
        y = PathXConstants.OVERLAY_BUTTON_CLOSE_Y;
        s = new Sprite(sT, x, y, 0, 0, INVISIBLE.toString());
        guiButtons.put(POPUP_CLOSE_BUTTON_TYPE, s);
        
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
            { eventHandler.respondToExitRequest(); }
        });
        
        initMainMenuHandlers();
        initLevelSelectHandlers();
        initGameScreenHandlers();
        initSettingsHandlers();
    }

    private void initMainMenuHandlers() {
        //Set play button resopnse
        Sprite playButton = guiButtons.get(PLAY_BUTTON_TYPE);
        playButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                { eventHandler.switchToLevelSelectScreen();  }
        });
        
        //Set reset button response
        Sprite resetButton = guiButtons.get(RESET_BUTTON_TYPE);
        resetButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                { eventHandler.resetRequest();  }
        });
        
        //Set settings button response
        Sprite settingsButton = guiButtons.get(SETTINGS_BUTTON_TYPE);
        settingsButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   eventHandler.switchToSettingsMenu(); }
        });
        
        //Set help button response
        Sprite helpButton = guiButtons.get(HELP_BUTTON_TYPE);
        settingsButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    eventHandler.switchToSettingsMenu();    
                }
        });
        
        //Set quit button response
        Sprite quitButton = guiButtons.get(QUIT_BUTTON_TYPE);
        quitButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   eventHandler.quitGameRequest(); }
        });
    }
    
    private void initLevelSelectHandlers() {
        //Level select back button
        Sprite backButton = guiButtons.get(BACK_BUTTON_TYPE);
        backButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   eventHandler.switchToMainMenu();    }
        });
        
        //Scroll up arrow button
        Sprite upArrow = guiButtons.get(UP_ARROW_BUTTON_TYPE);
        upArrow.setActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent ae)
                {   eventHandler.scrollUpRequest(); }
        });
        
        //Scroll down arrow button
        Sprite downArrow = guiButtons.get(DOWN_ARROW_BUTTON_TYPE);
        downArrow.setActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {   eventHandler.scrollDownRequest();   }
        });
        
        //Scroll left arrow button
        Sprite leftArrow = guiButtons.get(LEFT_ARROW_BUTTON_TYPE);
        leftArrow.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    eventHandler.scrollLeftRequest();   
                }
        });
        
        //Scroll right arrow button
        Sprite rightArrow = guiButtons.get(RIGHT_ARROW_BUTTON_TYPE);
        rightArrow.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    eventHandler.scrollRightRequest();  
                }
        });
    }
    
    private void initGameScreenHandlers() {
        //Back button
        Sprite backButton = guiButtons.get(GAME_BACK_BUTTON_TYPE);
        backButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   eventHandler.switchToLevelSelectScreen();   }
        });
        
        //Quit Button
        Sprite quitButton = guiButtons.get(GAME_QUIT_BUTTON_TYPE);
        quitButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   eventHandler.quitGameRequest();  }
        });
        
        Sprite startButton = guiButtons.get(START_BUTTON_TYPE);
        startButton.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   eventHandler.startLevelRequest();   }
        });
        
        //SPECIALS EVENT HANDLERS GO HERE
    }
    
    private void initSettingsHandlers(){
        //Sound toggle
        Sprite soundToggle = guiButtons.get(SOUND_TOGGLE_BUTTON_TYPE);
        soundToggle.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    eventHandler.toggleSoundRequest();  
                }
        });
        
        //Music Toggle
        Sprite musicToggle = guiButtons.get(MUSIC_TOGGLE_BUTTON_TYPE);
        musicToggle.setActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
                {   
                    eventHandler.toggleMusicRequest();  
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

}
