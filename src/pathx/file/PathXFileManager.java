/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import pathx.data.PathXDataModel;
import pathx.data.PathXRecord;
import pathx.ui.PathXMiniGame;
import properties_manager.PropertiesManager;
import pathx.PathX.PathXPropertyType;
import pathx.data.PathXLevel;
import pathx.ui.PathXLevelSprite;

/**
 *
 * @author Andrew
 */
public class PathXFileManager {
    
    private PathXMiniGame game;
    private PathXDataModel data;
    
    public PathXFileManager(PathXMiniGame miniGame){
        game = miniGame;
        data = (PathXDataModel)game.getDataModel();
    }
    
    public void saveRecord(PathXRecord record){
        //@TODO Implement game stats saving.
    }
    
    public void loadRecord(){
        //@TODO Implement game record loading.
    }
    
    public void loadLevelDetails(){
        //The Level details will be saved in an XML file
        //Details will include it's location on the level select map, as well
        //as its name and reward.
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //Create a HashMap to store the newly created PathXLevels
        HashMap<String, PathXLevel> levels = new HashMap();
        ArrayList<PathXLevelSprite> levelSprites = new ArrayList();
        
        //Iterate through the XML file to find details on each level available.
        //These details are loaded into a new PathXLevel and then put into the 
        //levels HashMap.
        //Another ArrayList of PathXLevelSprites will also be filled. These sprites
        //are used for level select map rendering.
        ArrayList<String> levelDetails = props.getPropertyOptionsList(PathXPropertyType.LEVEL_OPTIONS);
        StringTokenizer st;
        for (String s : levelDetails){
            st = new StringTokenizer(s, ",", false);
            String levelName = st.nextToken().trim();
            int reward = Integer.parseInt(st.nextToken().trim());
            String levelPath = st.nextToken().trim();
            int xPos = Integer.parseInt(st.nextToken().trim());
            int yPos = Integer.parseInt(st.nextToken().trim());
            PathXLevel level = new PathXLevel(levelName, reward, xPos, yPos, false, data);
            levels.put(levelName, level);
            
            PathXLevelSprite levelSprite = new PathXLevelSprite(level, xPos, yPos, game);
            levelSprites.add(levelSprite);
        }
        
        //Give the HashMap of levels to the DataModel.
        data.setLevels(levels);
        //Give the ArrayList of the associated sprites to the DataModel
        data.setLevelSprites(levelSprites);
    }
}
