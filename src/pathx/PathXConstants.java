/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx;

/**
 *
 * @author Andrew
 */
public class PathXConstants {
    // WE NEED THESE CONSTANTS JUST TO GET STARTED
    // LOADING SETTINGS FROM OUR XML FILES
    //public static String PROPERTY_TYPES_LIST = "property_types.txt";
    public static String PROPERTIES_FILE_NAME = "properties.xml";
    public static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";    
    public static String PATH_DATA = "./data/";
    
    // THESE ARE THE TYPES OF CONTROLS, WE USE THESE CONSTANTS BECAUSE WE'LL
    // STORE THEM BY TYPE, SO THESE WILL PROVIDE A MEANS OF IDENTIFYING THEM
    
    // EACH SCREEN HAS ITS OWN BACKGROUND TYPE
    public static final String BACKGROUND_TYPE = "BACKGROUND_TYPE";
    
    //IN-GAME UI CONTROL TYPES
    //Main Menu
    public static final String PLAY_GAME_BUTTON_TYPE = "PLAY_GAME_BUTTON_TYPE";
    public static final String RESET_BUTTON_TYPE = "RESET_GAME_BUTTON_TYPE";
    public static final String SETTINGS_BUTTON_TYPE = "SETTINGS_GAME_BUTTON_TYPE";
    public static final String HELP_BUTTON_TYPE = "HELP_GAME_BUTTON_TYPE";
    
    //Level Select Screen
    public static final String HOME_BUTTON_TYPE = "HOME_BUTTON_TYPE";
    public static final String QUIT_BUTTON_TYPE = "QUIT_BUTTON_TYPE";
    public static final String UP_ARROW_BUTTON_TYPE = "UP_ARROW_BUTTON_TYPE";
    public static final String DOWN_ARROW_BUTTON_TYPE = "DOWN_ARROW_BUTTON_TYPE";
    public static final String RIGHT_ARROW_BUTTON_TYPE = "RIGHT_ARROW_BUTTON_TYPE";
    public static final String LEFT_ARROW_BUTTON_TYPE = "LEFT_ARROW_BUTTON_TYPE";
    public static final String MAP_TYPE = "MAP_TYPE";
    public static final String CLOSE_BUTTON_TYPE = "CLOSE_BUTTON_TYPE";
    
    //USED FOR THE SETTINGS SCREEN.
    public static final String TOGGLE_BUTTON_TYPE = "TOGGLE_BUTTON_TYPE";
    
    //Game Screen
    public static final String START_BUTTON_TYPE = "START_BUTTON_TYPE";
    public static final String MAKE_GREEN_BUTTON_TYPE = "MAKE_GREEN_BUTTON_TYPE";
    public static final String MAKE_RED_BUTTON_TYPE = "MAKE_RED_BUTTON_TYPE";
    public static final String FREEZE_BUTTON_TYPE = "FREEZE_BUTTON_TYPE";
    public static final String INCREASE_SPEED_BUTTON_TYPE = "INCREASE_SPEED_BUTTON_TYPE";
    public static final String DECREASE_SPEED_BUTTON_TYPE = "DECREASE_SPEED_BUTTON_TYPE";
    public static final String INCREASE_PLAYER_SPEED_BUTTON_TYPE = "INCREASE_PLAYER_SPEED_BUTTON_TYPE";
    public static final String EMPTY_GAS_BUTTON_TYPE = "EMPTY_GAS_BUTTON_TYPE";
    public static final String FLAT_TIRE_BUTTON_TYPE = "FLAT_TIRE_BUTTON_TYPE";
    public static final String CLOSE_ROAD_BUTTON_TYPE = "CLOSE_ROAD_BUTTON_TYPE";
    public static final String CLOSE_INTERSECTION_BUTTON_TYPE = "CLOSE_INTERSECTION_BUTTON_TYPE";
    public static final String OPEN_INTERSECTION_BUTTON_TYPE = "OPEN_INTERSECTION_BUTTON_TYPE";
    public static final String STEAL_BUTTON_TYPE = "STEAL_BUTTON_TYPE";
    public static final String MIND_CONTROL_BUTTON_TYPE = "MIND_CONTROL_BUTTON_TYPE";
    public static final String INTANGIBILITY_BUTTON_TYPE = "INTANGIBILITY_BUTTON_TYPE";
    public static final String MINDLESS_TERROR_BUTTON_TYPE = "MINDLESS_TERROR_BUTTON_TYPE";
    public static final String FLYING_BUTTON_TYPE = "FLYING_BUTTON_TYPE";
    public static final String GOD_MODE_BUTTON_TYPE = "GOD_MODE_BUTTON_TYPE";
    
    public static final String LEVEL_FAIL_DIALOG_TYPE = "LEVEL_FAIL_DIALOG_TYPE";
    
    public static final String MENU_SCREEN_STATE = "MENU_SCREEN_STATE";
    public static final String LEVEL_SELECT_SCREEN_STATE = "LEVEL_SELECT_SCREEN_STATE";
    public static final String SETTINGS_SCREEN_STATE = "SETTINGS_SCREEN_STATE";
    public static final String GAME_SCREEN_STATE = "GAME_SCREEN_STATE";
    
    // ANIMATION SPEED
    public static final int FPS = 50;
    // UI CONTROL SIZE AND POSITION SETTINGS
    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;
    
    //TODO MENU BUTTON SIZES
    
    public static final int MIN_CAR_VELOCITY = 5;
    public static final int MAX_CAR_VELOCITY = 20;
    
    // UI CONTROLS POSITIONS IN THE GAME SCREEN
    public static final int MAIN_MENU_BUTTON_WIDTH = 100;
    public static final int MAIN_MENU_BUTTON_GAP = 50;
    public static final int PLAY_BUTTON_X = 50;
    public static final int PLAY_BUTTON_Y = 350;
    public static final int MAIN_QUIT_BUTTON_X = 550;
    public static final int MAIN_QUIT_BUTTON_Y = 50;
    
}
