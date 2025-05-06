/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physics;

public class Energy {

    public static double calculateKineticEnergy(double mass, double velocity) {
        return 0.5 * mass * velocity * velocity;
    }

    public static double calculatePotentialEnergy(double mass, double gravity, double height) {
        return mass * gravity * height;
    }

    public static double calculateFrictionalEnergy(double frictionForce, double distance) {
        return frictionForce * distance;
    }

    public static double calculateTotalEnergy(double kinetic, double potential, double frictional) {
        return kinetic + potential + frictional;
    }
}
