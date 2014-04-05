/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

/**
 * @author Andrew
 */

/**
 * There is a CarState for each state a Car Sprite may be in including whether 
 * or not it's moving and if any specials are affecting it.
 * 
 * @author Andrew
 */
public enum CarState {
    STOPPED,
    MOVING,
    FROZEN,
    FLAT_TIRES,
    EMPTY_GAS,
    STEALING,
    INTANGIBLE,
    MINDLESS,
    FLYING,
    GOD_MODE
}
