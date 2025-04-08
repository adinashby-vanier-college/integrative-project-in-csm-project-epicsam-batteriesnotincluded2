/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physics;

/**
 *
 * @author theli
 * this is the base physics class for all our simulations
 */
public class Physics {
    
    double velocity;

    double velocityX;
    
    double velocityY;
    
    double mass;
    
    public Physics (double velocity, double positionX, double positionY, double mass){
          this.velocity = velocity;
          this.mass = mass;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    
    
    
}
