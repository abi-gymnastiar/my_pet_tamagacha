package Physics.primitives;

import org.joml.Vector2f;

public abstract class Circle {
    private float radius;
    private Vector2f position;
    private Vector2f velocity;
    private Vector2f acceleration;
    private float mass;
    private float angularVelocity;
    private float torque;
    private float momentOfInertia;

    public Circle(float r, Vector2f pos, float mass) {
        this.radius = r;
        this.velocity = new Vector2f(0, 0);
        this.acceleration = new Vector2f(0, 0);
        this.mass = mass;
        this.angularVelocity = 0.0f;
        this.setTorque(0.0f);
        this.momentOfInertia = 0.5f * mass * radius * radius;
    }

    public abstract void update(float dt, float screenWidth, float screenHeight);

    public void applyForce(Vector2f force) {
        acceleration.add(force.mul(1/mass));
    }

    public void applyTorque(float torque) {
        this.setTorque(this.getTorque() + torque);
    }
//
//    public void updateTorque(float dt) {
//
//    }

    public void updateAngularVelocity(float dt) {
        float angularAcceleration = getTorque() / momentOfInertia;
        angularVelocity += angularAcceleration * dt;
        setTorque(0.0f);
    }

    public float getRadius() {
        return this.radius;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getVelocity() {
        return this.velocity;
    }

    public Vector2f getAcceleration() {
        return this.acceleration;
    }

    public float getAngularVelocity() {
        return this.angularVelocity;
    }

    public float getTorque() {
        return torque;
    }

    public void setTorque(float torque) {
        this.torque = torque;
    }
}
