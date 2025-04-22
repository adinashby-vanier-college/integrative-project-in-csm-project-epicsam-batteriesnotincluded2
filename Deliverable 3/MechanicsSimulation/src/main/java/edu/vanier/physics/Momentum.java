/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physics;

import edu.vanier.template.controllers.MomentumFXMLController;

/**
 *
 * @author theli
 */
public class Momentum extends Physics {
    
    double positionX;
    
    double positionY;
        
    double Pi; //initial momentum
    
    double Pf; //final momentum
    
    public Momentum(double positionX, double positionY, double mass, double vx, double vy) {
        super(vx, positionX, positionY, mass);
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = vx;
        this.velocityY = vy;
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
    
    public double getVelocityY(){
        return velocityY;
    }
    
    public double getVelocityX(){
        return velocityX;
    }

    
    public double getAngle(){//returns the angle of the ball from the positive x axis counter clockwise
        return Math.atan2(velocityY,velocityX);
    } 
    
    @Override
    public void setMass(double m){
        this.mass = m;
    }
    
    @Override
    public void setVelocity(double v){
        this.velocity = v;
    }

    public void setVelocityX(double vx){
        this.velocityX = vx;
    }
    
    public void setVelocityY(double vy){
        this.velocityY = vy;
    }
    
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
    
    public void setMoveSpeed(){
        
    }
    
    public double calcMomentum(){
       return mass*velocity;
    }
    
    public void setPi(double p){
       this.Pi = p;
    }
    
    public void setPf(double p){
       this.Pf = p;
    }
    
    public double getPi(){
       return Pi;
    }
    
    public double getPf(){
       return Pf;
    }
    
    public double calcImpulse(){
       return Pf-Pi;
    }
    
    public double calcEk(){
       return 0.5*mass*velocity*velocity;
    }

}
