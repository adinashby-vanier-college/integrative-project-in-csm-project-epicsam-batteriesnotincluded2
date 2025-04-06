package edu.vanier.physics;

public class Kinematics {
    private double launchAngle;
    private double acceleration;
    private double launchHeight;
    private double initialVelocity;
    private double initialPosition;
    private double time;

    public Kinematics(double launchAngle, double acceleration, double launchHeight, double initialVelocity, double initialPosition) {
        this.launchAngle = launchAngle;
        this.acceleration = acceleration;
        this.launchHeight = launchHeight;
        this.initialVelocity = initialVelocity;
        this.initialPosition = initialPosition;
    }

    //Getters
    public double getLaunchAngle() {
        return launchAngle;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getLaunchHeight() {
        return launchHeight;
    }

    public double getInitialVelocity() {
        return initialVelocity;
    }

    public double getInitialPosition() {
        return initialPosition;
    }

    public double getTime() {
        return time;
    }

    //Setters
    public void setLaunchAngle(double angle) {
        this.launchAngle = angle;
    }

    public void setAcceleration(double a) {
        this.acceleration = a;
    }

    public void setLaunchHeight(double yi) {
        this.launchHeight = yi;
    }

    public void setInitialVelocity(double vi) {
        this.initialVelocity = vi;
    }

    public void setInitialPosition(double xi) {
        this.initialPosition = xi;
    }

    public void setTime(double t) {
        this.time = t;
    }

    double angle = this.launchAngle;
    double a = this.acceleration;
    double vi = this.initialVelocity;
    double yi = this.launchHeight;
    double xi = this.initialPosition;
    double t = this.time;

    //Converting degrees to radians
    double rads = (Math.PI*angle)/180;
    //Calculating y-component of initial velocity
    double viY = vi*Math.sin(rads);
    //Calculating x-component of initial velocity
    double viX = vi*Math.cos(rads);

    //Kinematics methods

    //Projectile motion methods
    //Calculating time taken of projectile
    public double proj_calcTime() {
        //Calculating time taken of projectile and return :sunglasses:
        return (viY+(Math.sqrt(viY*viY+2*a*yi)))/a;
    }

    //Calculating max height of projectile
    public double proj_calcMaxHeight() {
        return (yi+(viY*viY))/(2*a);
    }

    //Calculating total distance projectile travelled
    public double proj_calcDistance() {
        return (viX*(viY+(Math.sqrt(viY*viY+2*a*yi))))/ a;
    }


    //1D kinematics methods
    //Calculating final velocity
    public double kinematic_calcFinalVelocity() {
        return vi+a*t;
    }

    //Calculating final position
    public double kinematic_calcFinalPosition() {
        return ((kinematic_calcFinalVelocity()+vi/2)/t)-xi;
    }

    //Calculating total distance
    public double kinematic_calcTotalDistance() {
        return kinematic_calcFinalPosition()-xi;
    }
}
