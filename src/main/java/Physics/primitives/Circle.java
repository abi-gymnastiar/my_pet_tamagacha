package Physics.primitives;

import org.joml.Vector2f;

public abstract class Circle {
    private float radius;
    private Vector2f position;
    private Vector2f velocity;
    private Vector2f acceleration;
    private float mass;

    public Circle(float r, Vector2f pos, float mass) {
        this.radius = r;
        this.velocity = new Vector2f(0, 0);
        this.acceleration = new Vector2f(0, 0);
        this.mass = mass;
    }

    public abstract void update(float dt, float screenWidth, float screenHeight);

    public void applyForce(Vector2f force) {
        // apply force to acceleration
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
}
