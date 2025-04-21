package edu.vanier.physics;

public class Kinematics {
    private double launchAngle;
    private double acceleration;
    private double launchHeight;
    private double initialVelocity;
    private double initialPosition;
    private double time;

    public Kinematics(double launchAngle, double acceleration, double launchHeight, double initialVelocity, double initialPosition, double time) {
        this.launchAngle = launchAngle;
        this.acceleration = acceleration;
        this.launchHeight = launchHeight;
        this.initialVelocity = initialVelocity;
        this.initialPosition = initialPosition;
        this.time = time;
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
    public double proj_calcTime() {
        double rads = Math.toRadians(launchAngle);
        double viY = initialVelocity * Math.sin(rads);
        double discriminant = viY * viY + 2 * acceleration * launchHeight;

        if (discriminant < 0) return Double.NaN; // avoid sqrt of negative

        return (viY + Math.sqrt(discriminant)) / acceleration;
    }

    public double proj_calcMaxHeight() {
        double rads = Math.toRadians(launchAngle);
        double viY = initialVelocity * Math.sin(rads);
        return (viY * viY) / (2 * acceleration);
    }

    public double proj_calcDistance() {
        double rads = Math.toRadians(launchAngle);
        double viY = initialVelocity * Math.sin(rads);
        double viX = initialVelocity * Math.cos(rads);

        double time = proj_calcTime();
        if (Double.isNaN(time)) return Double.NaN;

        return viX * time;
    }

    public double proj_calcHorizontalPosition(double time) {
        return viX*time;
    }

    public double proj_calcVerticalPosition(double time) {
        return yi + viY * time + 0.5 * acceleration * time * time * -1; //-1 bc gravity is downward
    }

    public double proj_calHorizontalVelocity() {
        return viX;
    }

    public double proj_calcVerticalVelocity(double time) {
        return viY + acceleration * time * -1; //-1 bc gravity again
    }


    //1D kinematics methods
    //Calculating final velocity
    public double kinematic_calcFinalVelocity() {
        return initialVelocity + acceleration * time;
    }

    //Calculating final position
    public double kinematic_calcFinalPosition() {
        return initialPosition + initialVelocity * time + 0.5 * acceleration * time * time;
    }

    //Calculating total distance
    public double kinematic_calcTotalDistance() {
        return Math.abs(kinematic_calcFinalPosition() - initialPosition);
    }

    //Calculating the position of the ball during something time
    public double kinematic_calcWhatPosition(double time) {
        return initialPosition + initialVelocity * time + 0.5 * acceleration * time * time;
    }

    //Calculating the velocity of the ball during something time
    public double kinematic_calcWhatVelocity(double time) {
        return initialVelocity + acceleration * time;
    }
}
