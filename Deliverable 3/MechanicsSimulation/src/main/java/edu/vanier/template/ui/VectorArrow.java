/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

/**
 * The class that controls the vector arrows. Holds a stem and a tip.
 * @author ChiliWasp
 */
public class VectorArrow extends StackPane {
    /**
     * the base width of the vector's stem
     */
    final public static double width=111;
    /**
     * the base height of the vector's stem
     */
    final public static double height=26;
    /**
     * the base magnitude of the vector's stem
     */
    double magnitude=50;
    double rotation=0;
    boolean selected=false;
    boolean net=false;
    boolean ignore=false;

    /**
     * purely used to make the net vector yellow.
     */
    public void setColorYellow(){
        ((Shape)this.getChildren().getFirst()).setFill(Color.GOLD);
        ((Shape)this.getChildren().getLast()).setFill(Color.GOLD);
    }
    
    public boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
        if (this.ignore==true) this.setOpacity(0.5);
        else this.setOpacity(1);
    }
    
    /**
     * sets if a vector is the net vector (in most cases, it is not.)
     * @param net if it is the net vector, yes or no.
     */
    public void setNet(boolean net){
        this.net=net;
    }
    
    /**
     *gets if this vector is the net vector. there should only be 1 ever. by default, this is false.
     * @return the net-ness of a vector.
     */
    public boolean getNet(){
        return net;
    }
    
    /**
     * gets the magnitude of the vector, in newtons.
     * @return the magnitude
     */
    public double getMagnitude() {
        return magnitude;
    }
    
    /**
     * sets the magnitude of the vector, in newtons. also updates the vector's length to correspond with the magnitude
     * @param magnitude the magnitude you wish to set it as
     */
    public void setMagnitude(double magnitude) {
        ((Rectangle) this.getChildren().get(0)).setWidth(magnitude*2.22);
        this.magnitude = magnitude;
    }
    
    /**
     * The mathematical angle with respect to the box in the center, not to be confused with the rotate property.
     * <p>
     * 0 degrees is flat on the right (pointing left), and the degree increases as the VectorArrow rotates counter-clockwise.
     * @return the angle rotated, in degrees
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Sets the angle of rotation of the vector. This is NOT the rotate property of the VectorArrow. This is for math.
     * @param rotation the angle of the VectorArrow, in degrees
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
    
    /**
     * See if this vector is selected or not.
     * @return the selected property
     */
    public boolean getSelected() {
        return selected;
    }
    
    /**
     * Sets the selected property of the VectorArrow
     * @param selected either true or false, if it is selected or not
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    /**
     * creates a vector arrow at a certain location with a default magnitude 
     * @param x the x spawn coordinate
     * @param y the y spawn coordinate
     */
    public VectorArrow(double x, double y){
        VectorArrow.this.setWidth(186);
        VectorArrow.this.setHeight(88);
        
        VectorArrow.this.setLayoutX(x);
        VectorArrow.this.setLayoutY(y);
        VectorArrow.this.setAlignment(Pos.CENTER_RIGHT);
        
        Rectangle stem=new Rectangle(10, 0, width, height);
        stem.setTranslateX(-62);
        stem.setStroke(Color.BLACK);
        stem.setStrokeWidth(0);
        Polygon tip=new Polygon(0,28,66,-16,0,-60);
        tip.setStroke(Color.BLACK);
        tip.setStrokeWidth(0);
        tip.setScaleX(0.9);
        tip.setScaleY(0.7);
        
        String color="";
        String pointZero=".0";
        
        for (int i=0;i<6;i++){
            String hxdml=Double.toString((int)Math.rint(Math.random()*16));
            hxdml=hxdml.replaceAll(pointZero, "");
            switch (hxdml){
                case "10": hxdml="a"; break;
                case "11": hxdml="b"; break;
                case "12": hxdml="c"; break;
                case "13": hxdml="d"; break;
                case "14": hxdml="e"; break;
                case "15": hxdml="f"; break;
                case "16": hxdml="f"; break;
                default: break;
            }
            color=color+hxdml;
        }
        color=color.replaceAll(pointZero, "");
//        while (color.contains(point0)){
//            if (color.substring(index, index+2).equals(point0))
//                color=color.substring(index-0,index)+color.substring(index+2);
//            
//            else index++;
//        }
        while (color.length()<6){
            color=color+"0";
        }
        color=color.trim();
       // System.out.println(color);
        stem.setFill(Paint.valueOf(color));
        tip.setFill(Paint.valueOf(color));
        
        VectorArrow.this.getChildren().addAll(stem,tip);
    }
}
