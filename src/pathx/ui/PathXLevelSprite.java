/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.TreeMap;
import mini_game.Sprite;
import mini_game.SpriteType;
import mini_game.Viewport;
import static pathx.PathXConstants.COMPLETE_LEVEL_TYPE;
import static pathx.PathXConstants.INCOMPLETE_LEVEL_TYPE;
import static pathx.PathXConstants.VIEWPORT_X;
import static pathx.PathXConstants.VIEWPORT_Y;
import pathx.data.PathXLevel;
import static pathx.ui.PathXSpriteState.MOUSE_OVER;
import static pathx.ui.PathXSpriteState.VISIBLE;
/**
 *
 * @author Andrew
 */
public class PathXLevelSprite {
    
    private String name;
    private Sprite s;
    private PathXLevel level;
    private int xPos, yPos;
    private PathXMiniGame game;
    private PathXEventHandler eventHandler;
    
    public PathXLevelSprite (PathXLevel level, int xPos, int yPos, PathXMiniGame game){
        this.level = level;
        eventHandler = game.getEventHandler();
        this.xPos = xPos;
        this.yPos = yPos;
        this.game = game;
        name = level.getLevelName();
        
        //Construct the sprite
        if (level.isCompleted()){
            SpriteType sT = new SpriteType(COMPLETE_LEVEL_TYPE);
            BufferedImage img = game.getLevelNodeImage(COMPLETE_LEVEL_TYPE);
            sT.addState(VISIBLE.toString(), img);
            sT.addState(MOUSE_OVER.toString(), img);
            s = new Sprite(sT, xPos, yPos, 0, 0, VISIBLE.toString());
            s.setActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {   eventHandler.switchToGameScreen();    }
            });
        }else{
            SpriteType sT = new SpriteType(INCOMPLETE_LEVEL_TYPE);
            BufferedImage img = game.getLevelNodeImage(INCOMPLETE_LEVEL_TYPE);
            sT.addState(VISIBLE.toString(), img);
            sT.addState(MOUSE_OVER.toString(), img);
            s = new Sprite(sT, xPos, yPos, 0, 0, VISIBLE.toString());
            s.setActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {   eventHandler.switchToGameScreen();    }
            });
        }
        
        
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return s;
    }

    public PathXLevel getLevel() {
        return level;
    }
    
    public void update(){
        TreeMap<String, Sprite> buttons = game.getGUIButtons();
        Viewport vp = game.getDataModel().getViewport();
        
        //Construct the new sprite
        if (level.isCompleted()){
            SpriteType sT = new SpriteType(COMPLETE_LEVEL_TYPE);
            BufferedImage img = game.getLevelNodeImage(COMPLETE_LEVEL_TYPE);
            sT.addState(VISIBLE.toString(), img);
            sT.addState(MOUSE_OVER.toString(), img);
            s = new Sprite(sT, VIEWPORT_X + xPos - vp.getViewportX(), VIEWPORT_Y + yPos - vp.getViewportY(), 0, 0, VISIBLE.toString());
            s.setActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {   eventHandler.switchToGameScreen();    }
            });
        }else{
            SpriteType sT = new SpriteType(INCOMPLETE_LEVEL_TYPE);
            BufferedImage img = game.getLevelNodeImage(INCOMPLETE_LEVEL_TYPE);
            sT.addState(VISIBLE.toString(), img);
            sT.addState(MOUSE_OVER.toString(), img);
            s = new Sprite(sT, VIEWPORT_X + xPos - vp.getViewportX(), VIEWPORT_Y + yPos - vp.getViewportY(), 0, 0, VISIBLE.toString());
            s.setActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae)
                {   eventHandler.switchToGameScreen();    }
            });
        }
        
        buttons.put(level.getLevelName(), s);
    }
}
