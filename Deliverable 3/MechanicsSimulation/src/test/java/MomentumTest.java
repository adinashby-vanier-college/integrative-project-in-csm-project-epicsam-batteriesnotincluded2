

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MomentumTest {    
    
    @Test
    void testCalcP(){
        System.out.println("Testing momentum calculation");
        double m = 5;
        double v = 5;
        double val = m*v;       
        assertEquals(val,calcP(m,v));
    }
    
    @Test
    void testCalcJ(){
        System.out.println("Testing impulse calculation");
        double pf = 5;
        double pi = 5;
        double val = pf-pi;       
        assertEquals(val,calcJ(pf,pi));
    }
    
    @Test
    void testCalcEk(){
        System.out.println("Testing kinetic energy calculation");
        double m = 5;
        double v = 5;
        double val = 0.5*m*v*v;       
        assertEquals(val,calcEk(m,v));
    }
    
    double calcP(double mass, double velocity){
        return mass*velocity;
    }
    
    
    double calcJ(double pf, double pi){
        return pf-pi;
    }
    
    double calcEk(double m, double v){
        return 0.5*m*v*v;
    }
}