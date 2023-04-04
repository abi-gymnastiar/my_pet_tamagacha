package Entity;

import Main.MainPanel;
import Physics.interfaces.Circle;
import org.joml.Vector2f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ball extends Entity implements Circle {
    private float radius;
    private Vector2f initialPosition;
    public Vector2f position;
    private Vector2f initialVelocity;
    public Vector2f velocity;
    public Vector2f acceleration;
    private float mass;
    public BufferedImage sprite;
    public boolean ballGrab;
    public Vector2f dampingForce;
    float damping;

    public Ball(MainPanel mp, float radius, Vector2f position, Vector2f velocity, float mass) {
        super(mp);
        this.radius = radius;
        this.initialPosition = new Vector2f(position);
        this.position = new Vector2f(position);
        this.initialVelocity = new Vector2f(velocity);
        this.velocity = new Vector2f(velocity);
        this.acceleration = new Vector2f(0, 0);
        this.mass = mass;
        setSprite();
    }

    public void setSprite() {
        File file = new File("src/main/java/Assets/balls/ball.png");
        try {
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(float dt, float screenWidth, float screenHeight) {
        // add gravity
        if (!ballGrab) {
            Vector2f gravity = new Vector2f(0, 0.01f);
            applyForce(gravity);
            this.velocity.add(this.acceleration.mul(dt));

            // update position based on velocity
            this.position.add(this.velocity.mul(dt));
        }


        // handle collision with walls
        if (this.position.x - this.radius < 0) {
            this.position.x = this.radius;
            this.velocity.x = (-this.velocity.x);
        } else if (this.position.x + this.radius > screenWidth) {
            this.position.x = screenWidth - this.radius;
            this.velocity.x = (-this.velocity.x);
        }

        // friction on the floor
        if (this.position.y >= 375) {
            this.velocity.x *= (1.0f - 0.01);
            this.velocity.y *= (1.0f - 0.01);
        }

        // handle collision with floor
        if (this.position.y - this.radius < 0) {
            this.position.y = this.radius;
            this.velocity.y = (-this.velocity.y * 0.9f);
        } else if (this.position.y + this.radius > screenHeight - 64) {
            this.position.y = screenHeight - this.radius - 64;
            this.velocity.y = (-this.velocity.y * 0.9f);
        }

        //System.out.println(velocity);
    }

    public void applyForce(Vector2f force) {
        // apply force to acceleration
        Vector2f acceleration = force.mul(1.0f / this.mass);
        this.acceleration.add(acceleration);;
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

    public void respawn() {
        this.position = new Vector2f(this.initialPosition);
        this.velocity = new Vector2f(this.initialVelocity);
        this.acceleration = new Vector2f(0, 0);
    }

}
