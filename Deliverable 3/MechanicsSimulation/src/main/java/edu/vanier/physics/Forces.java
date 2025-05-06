/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physics;

/**
 *
 * @author ChiliWasp
 */
public class Forces {
    
    public double ForceX(double magnitude, double angle){
        angle=Math.toRadians(angle);
        return Math.cos(angle)*magnitude;
    }
    
    public double ForceY(double magnitude, double angle){
        angle=Math.toRadians(angle);
        return Math.sin(angle)*magnitude;
    }
    
}
