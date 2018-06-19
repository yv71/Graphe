/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author yv066840
 */
public abstract class State {
    
    public abstract Type_Action agir();
    public abstract State transition();
}
