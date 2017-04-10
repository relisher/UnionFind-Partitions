/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cis262.hw7;

/**
 *
 * @author arelin
 */
public class Tuple {
    int _u;
    int _v;
    
    public Tuple(String u, String v) {
        _u = Integer.parseInt(u);
        _v = Integer.parseInt(v);
    }
    
    public Tuple(int u, int v) {
        _u = u;
        _v = v;
    }
    
    @Override
    public String toString() {
        return Integer.toString(_u) + " " + Integer.toString(_v);
    }
}
