package edu.vanier.template.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MomentumTest {

    double mass, velocity;
    
    public static void main (String[]args){
      //assertEquals(20, calcP(4,5));
    }
    
    @Test
    double calcP(double mass, double velocity){
        System.out.println("Testing momentum calculation");
        return mass*velocity;
    }
    
    @Test
    void calcJ(){
        System.out.println("Testing impulse calculation");
    }
    
    @Test
    void calcEk(){
        System.out.println("Testing kinetic energy calculation");
    }
}