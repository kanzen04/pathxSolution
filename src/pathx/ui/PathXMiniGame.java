/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import java.awt.image.BufferedImage;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import pathx.PathX.PathXPropertyType;
import pathx.data.PathXDataModel;
import pathx.data.PathXRecord;
import properties_manager.PropertiesManager;

/**
 *
 * @author Andrew
 */
public class PathXMiniGame extends MiniGame{
    
    //Manages game data including the player's record, different cars in-game,
    private PathXDataModel dataModel;
    
    //Holds the stats for the current player including balance, unlocked levels,
    //and unlocked specials.
    private PathXRecord record;
    
    //Handles mostly UI events, namely special activations and screen switching.
    private PathXEventHandler eventHandler;
    
    //Handles errors that will probably occur when loading from property XML
    //files images and other things.
    private PathXErrorHandler errorHandler;
    
    //Loads and saves player records.
    private PathXFileManager fileManager;
    
    //Indicates the current screen being displayed.
    String screenState;
    
    @Override
    public void initData(){
        //Initialize error handlier
        errorHandler = new PathXErrorHandler(window);
        
        //Initialize file manager.
        //fileManager = new PathXFileManager();
        
        //Load a saved player record.
        //record = fileManager.loadRecord();
        
        //Initialize the data model.
        dataModel = new PathXDataModel(this);
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
    }

    @Override
    public void initGUIHandlers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateGUI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
