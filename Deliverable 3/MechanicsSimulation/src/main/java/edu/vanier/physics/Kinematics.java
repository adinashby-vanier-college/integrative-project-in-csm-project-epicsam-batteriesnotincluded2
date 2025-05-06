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

    /**
     * Gets the value of launch angle
     * @return launchAngle
     */
    public double getLaunchAngle() {
        return launchAngle;
    }

    /**
     * Gets the value of acceleration
     * @return acceleration
     */
    public double getAcceleration() {
        return acceleration;
    }

    /**
     * Gets the value of launch height
     * @return launchHeight
     */
    public double getLaunchHeight() {
        return launchHeight;
    }

    /**
     * Gets the value of initial velocity
     * @return initialVelocity
     */
    public double getInitialVelocity() {
        return initialVelocity;
    }

    /**
     * Gets the value of initial position
     * @return initialPosition
     */
    public double getInitialPosition() {
        return initialPosition;
    }

    /**
     * Gets the value of time
     * @return time
     */
    public double getTime() {
        return time;
    }

    //Setters

    /**
     * Sets the launch angle
     * @param angle angle of launch
     */
    public void setLaunchAngle(double angle) {
        this.launchAngle = angle;
    }

    /**
     * Sets the acceleration
     * @param a acceleration
     */
    public void setAcceleration(double a) {
        this.acceleration = a;
    }

    /**
     * Sets the launch height
     * @param yi launch height
     */
    public void setLaunchHeight(double yi) {
        this.launchHeight = yi;
    }

    /**
     * Sets the initial velocity
     * @param vi initial velocity
     */
    public void setInitialVelocity(double vi) {
        this.initialVelocity = vi;
    }

    /**
     * Sets the initial position
     * @param xi initial position
     */
    public void setInitialPosition(double xi) {
        this.initialPosition = xi;
    }

    /**
     * Sets the time
     * @param t time
     */
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

    /**
     * Calculates the time taken in projectile motion
     * @return projectile time
     */
    public double proj_calcTime() {
        double rads = Math.toRadians(launchAngle);
        double viY = initialVelocity * Math.sin(rads);
        double discriminant = viY * viY + 2 * acceleration * launchHeight;

        if (discriminant < 0) return Double.NaN; // avoid sqrt of negative

        return (viY + Math.sqrt(discriminant)) / acceleration;
    }

    /**
     * Calculates the max height in projectile motion
     * @return projectile max height
     */
    public double proj_calcMaxHeight() {
        double rads = Math.toRadians(launchAngle);
        double viY = initialVelocity * Math.sin(rads);
        return (viY * viY) / (2 * acceleration);
    }

    /**
     * Calculates the distance of projectile motion
     * @return projectile distance
     */
    public double proj_calcDistance() {
        double rads = Math.toRadians(launchAngle);
        double viY = initialVelocity * Math.sin(rads);
        double viX = initialVelocity * Math.cos(rads);

        double time = proj_calcTime();
        if (Double.isNaN(time)) return Double.NaN;

        return viX * time;
    }

    /**
     * Calculates the horizontal position of projectile motion
     * @param time projectile time
     * @return projectile horizontal position
     */
    public double proj_calcHorizontalPosition(double time) {
        return viX*time;
    }

    /**
     * Calculates the vertical position of projectile motion
     * @param time projectile time
     * @return projectile vertical position
     */
    public double proj_calcVerticalPosition(double time) {
        return yi + viY * time + 0.5 * acceleration * time * time * -1; //-1 bc gravity is downward
    }

    /**
     * Calculates the horizontal velocity of projectile motion
     * @return projectile horizontal velocity
     */
    public double proj_calcHorizontalVelocity() {
        return viX;
    }

    /**
     * Calculates the vertical velocity of projectile motion
     * @param time projectile time
     * @return projectile vertical velocity
     */
    public double proj_calcVerticalVelocity(double time) {
        return viY + acceleration * time * -1; //-1 bc gravity again
    }


    //1D kinematics methods

    /**
     * Calculates final velocity of 1D kinematics
     * @return 1D kinematics final velocity
     */
    public double kinematic_calcFinalVelocity() {
        return initialVelocity + acceleration * time;
    }

    /**
     * Calculates final position of 1D kinematics
     * @return 1D kinematics final position
     */
    public double kinematic_calcFinalPosition() {
        return initialPosition + initialVelocity * time + 0.5 * acceleration * time * time;
    }

    /**
     * Calculates total distance of 1D kinematics
     * @return kinematics total distance
     */
    public double kinematic_calcTotalDistance() {
        return Math.abs(kinematic_calcFinalPosition() - initialPosition);
    }

    /**
     * Calculates what position the ball is in 1D kinematics
     * @param time time
     * @return position of ball
     */
    public double kinematic_calcWhatPosition(double time) {
        return initialPosition + initialVelocity * time + 0.5 * acceleration * time * time;
    }

    /**
     * Calculates what velocity the ball is in 1D kinematics
     * @param time time
     * @return velocity of ball
     */
    public double kinematic_calcWhatVelocity(double time) {
        return initialVelocity + acceleration * time;
    }
}
