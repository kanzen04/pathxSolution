/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

/**
 *
 * @author Dawa Lama
 */
public enum PathXSpriteState {
    
    /**
     * There is a CarState for each state a Car Sprite may be in including whether 
     * or not it's moving and if any specials are affecting it.
     */
    STOPPED,
    MOVING,
    FROZEN,
    FLAT_TIRES,
    EMPTY_GAS,
    STEALING,
    INTANGIBLE,
    MINDLESS,
    FLYING,
    GOD_MODE,
    
    /**
     * There are only 3 states for each Node/Intersection
     * 
     * Green: Any car is allowed to pass through
     * Red: No car is allowed to pass through temporarily.
     * Closed: No car is allowed to pass through indefinitely.
     * 
     * It can be assumed that if the node is either green or red that it is also in
     * an "OPEN" state.
     * 
     *
     * A pathX Road can either be open or closed indefinitely.
     */
    //NODE & ROAD STATES
    GREEN,
    RED,
    OPEN,
    CLOSED,
    
    VISIBLE,
    INVISIBLE,
    MOUSE_OVER,
    
    ENABLED,
    DISABLED
}
