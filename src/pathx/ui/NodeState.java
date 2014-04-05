/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pathx.ui;

/**
 * There are only 3 states for each Node/Intersection
 * 
 * Green: Any car is allowed to pass through
 * Red: No car is allowed to pass through temporarily.
 * Closed: No car is allowed to pass through indefinitely.
 * 
 * It can be assumed that if the node is either green or red that it is also in
 * an "OPEN" state.
 * @author Andrew
 */
public enum NodeState {
    GREEN,
    RED,
    CLOSED
}
