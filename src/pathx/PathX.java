/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx;

import properties_manager.PropertiesManager;
import static pathx.PathXConstants.*;
/**
 *
 * @author Andrew
 */
public class PathX {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            //Load the app settings and properties.
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PathXConstants.PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            
            // THEN WE'LL LOAD THE GAME FLAVOR AS SPECIFIED BY THE PROPERTIES FILE
            String gameFlavorFile = props.getProperty(PathXPropertyType.FILE_GAME_PROPERTIES);
            props.loadProperties(gameFlavorFile, PROPERTIES_SCHEMA_FILE_NAME);
            
            // NOW WE CAN LOAD THE UI, WHICH WILL USE ALL THE FLAVORED CONTENT
            String appTitle = props.getProperty(SortingHatPropertyType.TEXT_TITLE_BAR_GAME);
            miniGame.initMiniGame(appTitle, FPS, WINDOW_WIDTH, WINDOW_HEIGHT);
        }
    }
    public enum PathXPropertyType {
        // LOADED FROM properties.xml

        /* SETUP FILE NAMES */
        FILE_GAME_PROPERTIES,
        FILE_PLAYER_RECORD,
        /* DIRECTORY PATHS FOR FILE LOADING */
        PATH_AUDIO,
        PATH_IMG,
        // LOADED FROM THE GAME FLAVOR PROPERTIES XML FILE
        
        // sorting_hat_properties.xml

        /* IMAGE FILE NAMES */
        IMAGE_QUIT_BUTTON,
        IMAGE_QUIT_BUTTON_MOUSE_OVER,
        
        //Splash images
        IMAGE_SPLASH_BACKGROUND,
        IMAGE_BUTTON_NEW,
        IMAGE_BUTTON_NEW_MOUSE_OVER,
        IMAGE_BUTTON_RESET,
        IMAGE_BUTTON_RESET_MOUSE_OVER,
        IMAGE_BUTTON_SETTINGS,
        IMAGE_BUTTON_SETTINGS_MOUSE_OVER,
        IMAGE_BUTTON_HELP,
        IMAGE_BUTTON_HELP_MOUSE_OVER,
        //Level select images
        IMAGE_LEVEL_BACKGROUND,
        IMAGE_BUTTON_HOME,
        IMAGE_UP_ARROW,
        IMAGE_UP_ARROW_MOUSE_OVER,
        IMAGE_DOWN_ARROW,
        IMAGE_DOWN_ARROW_MOUSE_OVER,
        IMAGE_RIGHT_ARROW,
        IMAGE_RIGHT_ARROW_MOUSE_OVER,
        IMAGE_LEFT_ARROW,
        IMAGE_LEFT_ARROW_MOUSE_OVER,
        IMAGE_MAP,
        //In-game images
        IMAGE_GAME_BACKGROUND,
        IMAGE_BUTTON_START,
        IMAGE_BANK_ICON,
        IMAGE_MAKE_GREEN,
        IMAGE_MAKE_RED,
        IMAGE_FREEZE_TIME,
        IMAGE_DECREASE_SPEED,
        IMAGE_INCREASE_SPEED,
        IMAGE_INCREASE_PLAYER_SPEED,
        IMAGE_FLAT_TIRE,
        IMAGE_EMPTY_GAS,
        IMAGE_CLOSE_ROAD,
        IMAGE_CLOSE_NODE,
        IMAGE_OPEN_NODE,
        IMAGE_STEALING,
        IMAGE_MIND_CONTROL,
        IMAGE_INTANGIBILITY,
        IMAGE_MINDLESS_TERROR,
        IMAGE_FLYING,
        IMAGE_GOD_MODE,
        
        //GAME TEXT
        TEXT_ERROR_LOADING_AUDIO,
        TEXT_ERROR_LOADING_LEVEL,
        TEXT_ERROR_LOADING_RECORD,
        TEXT_ERROR_LOADING_XML_FILE,
        TEXT_ERROR_SAVING_RECORD,
        TEXT_TITLE_BAR,
        TEXT_CLOSE_LEVEL_PROMPT,
        TEXT_RETRY_LEVEL,
        TEXT_EXIT_LEVEL,
        TEXT_LEVEL_FAIL,
        
        //AUDIO CUES
        AUDIO_CUE_ACTIVATE_SPECIAL,
        AUDIO_CUE_LOW_CASH,
        AUDIO_CUE_WIN,
        AUDIO_CUE_FAIL,
        SONG_CUE_GAME_SCREEN,
        SONG_CUE_MENU_SCREEN,
        
        

    }
}    

