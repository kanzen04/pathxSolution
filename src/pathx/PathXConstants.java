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
    public static final String PLAY_GAME_BUTTON_TYPE = "PLAY_GAME_BUTTON_TYPE";
    public static final String RESET_GAME_BUTTON_TYPE = "RESET_GAME_BUTTON_TYPE";
    
}
