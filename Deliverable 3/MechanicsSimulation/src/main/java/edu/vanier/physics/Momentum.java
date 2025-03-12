/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physics;

/**
 *
 * @author theli
 */
public class Momentum extends Physics {
    
    double positionX;
    
    double positionY;
        
    double Pi; //initial momentum
    
    double Pf; //final momentum
    
    public Momentum(double positionX, double positionY, double mass, double velocity) {
        super(velocity, positionX, positionY, mass);
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
    
    public double calclMomentum(){
       return mass*velocity;
    }
    
    public double calcImpulse() {
       return Pf-Pi;
    }
}
