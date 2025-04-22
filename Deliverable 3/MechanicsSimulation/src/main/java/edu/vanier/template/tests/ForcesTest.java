package edu.vanier.template.tests;

import static org.junit.jupiter.api.Assertions.*;
import edu.vanier.template.ui.VectorArrow;
import edu.vanier.template.controllers.ForcesFXMLController;

public class ForcesTest {
//    public static void main(String[] args) {
//        getMagnitudeXTest1();
//    }
    
    //@Test
    public void getMagnitudeXTest1(){
        
        double expected=50*Math.cos(Math.PI/4);
        VectorArrow test=new VectorArrow(0, 0);
        ForcesFXMLController fc=new ForcesFXMLController();
        fc.megaPane.getChildren().add(test);
        double actual=fc.combineMagnitudeX();
        
        assertEquals(expected, actual);
        
    }

}