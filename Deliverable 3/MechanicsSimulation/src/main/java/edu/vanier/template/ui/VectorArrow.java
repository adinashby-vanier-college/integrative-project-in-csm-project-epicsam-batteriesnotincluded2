/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.template.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

/**
 *
 * @author ChiliWasp
 */
public class VectorArrow extends StackPane {
    final public static double width=111;
    final public static double height=26;
    double rotation=0;
    boolean selected=false;
    
    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
    
    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    
    public VectorArrow(double x, double y){
        VectorArrow.this.setWidth(186);
        VectorArrow.this.setHeight(88);
        
        VectorArrow.this.setLayoutX(x);
        VectorArrow.this.setLayoutY(y);
        
        VectorArrow.this.setAlignment(Pos.CENTER_RIGHT);
        Rectangle stem=new Rectangle(10, 0, width, height);
        stem.setTranslateX(-62);
        Polygon tip=new Polygon(0,28,66,-16,0,-60);
        tip.setScaleX(0.9);
        tip.setScaleY(0.7);
        
        String color="";
        String point0=".0";
        
        for (int i=0;i<6;i++){
            String hxdml=Double.toString((int)Math.rint(Math.random()*16));
            hxdml=hxdml.replaceAll(point0, "");
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
        color=color.replaceAll(point0, "");
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
