/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cis262.hw7;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arelin
 */
public class Components {
    
    List<Integer> states;
    
    public Components(String [] items) {
        states = new ArrayList<>();
        for(String s : items) {
            states.add(Integer.parseInt(s));
        }
    }
    
    public int getTransition(int i){
        return states.get(i);
    }
    
    public int size() {
        return states.size();
    }
    
}
