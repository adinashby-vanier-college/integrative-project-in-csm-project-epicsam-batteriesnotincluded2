

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
<<<<<<< HEAD
        double actual=fc.addMagnitudeX();
        
        assertEquals(expected, actual);
        
=======
//        double actual=fc.combineMagnitudeX();

//        assertEquals(expected, actual);

>>>>>>> 56b2e52be7f26aafac269213c7df41b141af047a
    }

}