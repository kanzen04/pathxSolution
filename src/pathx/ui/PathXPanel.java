/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import java.awt.Graphics;
import java.text.NumberFormat;
import javax.swing.JPanel;
import mini_game.MiniGame;
import mini_game.Sprite;
import static pathx.PathXConstants.*;
import pathx.data.PathXDataModel;

/**
 *
 * @author Andrew
 */
public class PathXPanel extends JPanel{
    
    private MiniGame game;
    
    private PathXDataModel dataModel;
    
    private NumberFormat numberFormatter;
    
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
            
            renderBackground(g);
        }
    }
    
    public void renderBackground(Graphics g){
        Sprite bg = game.getGUIDecor().get(BACKGROUND_TYPE);
    }
}
