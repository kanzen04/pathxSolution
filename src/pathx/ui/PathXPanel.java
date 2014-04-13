/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.Collection;
import javax.swing.JPanel;
import mini_game.MiniGame;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import pathx.PathX;
import pathx.PathX.PathXPropertyType;
import static pathx.PathXConstants.*;
import pathx.data.PathXDataModel;
import properties_manager.PropertiesManager;

/**
 *
 * @author Andrew
 */
public class PathXPanel extends JPanel{
    
    private MiniGame game;
    
    private PathXDataModel dataModel;
    
    private NumberFormat numberFormatter;
    private PropertiesManager props = PropertiesManager.getPropertiesManager();
    
    private BufferedImage map;
    
    /**
     * This constructor stores the game and data references,
     * which we'll need for rendering.
     * 
     * @param initGame The pathX game that is using
     * this panel for rendering.
     * 
     * @param initData The pathX game data.
     */
    public PathXPanel(MiniGame initGame, PathXDataModel initData){
        
        game = initGame;
        dataModel = initData;
        numberFormatter = NumberFormat.getNumberInstance();
        numberFormatter.setMinimumFractionDigits(3);
        numberFormatter.setMaximumFractionDigits(3);
        map = game.loadImage(props.getProperty(PathXPropertyType.PATH_IMG)
                + props.getProperty(PathXPropertyType.IMAGE_MAP));
    }
    
    /**
     * This is where rendering starts. This method is called each frame, and the
     * entire game application is rendered here with the help of a number of
     * helper methods.
     * 
     * @param g The Graphics context for this panel.
     */
    @Override
    public void paintComponent(Graphics g){
        
        try{
            // MAKE SURE WE HAVE EXCLUSIVE ACCESS TO THE GAME DATA
            game.beginUsingData();
            
            // CLEAR THE PANEL
            super.paintComponent(g);
            
            // RENDER THE BACKGROUND, WHICHEVER SCREEN WE'RE ON
            renderBackground(g);
            
            //Render these things if the game as has started.
            if (!dataModel.notStarted()){
                
//                RENDER THE CARS IF THE LEVEL HASN'T BEEN WON YET
//                if (!dataModel.won())
//                    renderCars(g);
//                
//                RENDER THE GRAPH, INCLUDING THE ROADS AND NODES.
//                renderGraph();
//                
//                RENDER DIALOGS IF THERE ARE ANY.
//                renderDialogs(g);
//                
//                renderLevelName(g);
            
            
            }
            if (((PathXMiniGame) game).isCurrentScreenState(LEVEL_SELECT_SCREEN_STATE)) {
                renderMap(g);
            }
            
            //RENDER BUTTONS AND DECOR
            renderGUIControls(g);
            
//          renderStats(g);
            
            
        }
        finally {
            game.endUsingData();
        }
    }
    
    public void renderBackground(Graphics g){
        
        Sprite bg = game.getGUIDecor().get(BACKGROUND_TYPE);
        renderSprite(g, bg);
    }

    private void renderSprite(Graphics g, Sprite s) {
     
     // ONLY RENDER THE VISIBLE ONES
     if (!s.getState().equals(PathXSpriteState.INVISIBLE.toString())){
         SpriteType bgST = s.getSpriteType();
         Image img = bgST.getStateImage(s.getState());
         g.drawImage(img, (int)s.getX(), (int)s.getY(), bgST.getWidth(), bgST.getHeight(), null);
     }
        
    }

    private void renderGUIControls(Graphics g) {
        
        Collection<Sprite> decorSprites = game.getGUIDecor().values();
        for (Sprite s : decorSprites) 
        {
            if (!s.getSpriteType().getSpriteTypeID().equals(BACKGROUND_TYPE))
                renderSprite (g, s);
        
        }
        
        Collection<Sprite> buttonSprites = game.getGUIButtons().values();
        for (Sprite s : buttonSprites)
            renderSprite(g, s);
        
    }

    private void renderMap(Graphics g) {
        Viewport vp = dataModel.getViewport();
        int vpx = vp.getViewportX();
        int vpy = vp.getViewportY();
        g.drawImage(map, VIEWPORT_X, VIEWPORT_Y, VIEWPORT_X + VIEWPORT_WIDTH, VIEWPORT_Y + VIEWPORT_HEIGHT, vpx, vpy, vpx + VIEWPORT_WIDTH , vpy + VIEWPORT_HEIGHT, this);
    }
}
