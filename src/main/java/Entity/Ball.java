package Entity;

import Main.MainPanel;
import Physics.interfaces.Circle;
import org.joml.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
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
    private float angularVelocity;
    private float torque;
    private float momentOfInertia;
    public float rotation;
    public int xMouseGrab, yMouseGrab;
    public boolean isRotating;
    private float angularAcceleration, angle;

    public Ball(MainPanel mp, float radius, Vector2f position, Vector2f velocity, float mass) {
        super(mp);
        this.radius = radius;
        this.initialPosition = new Vector2f(position);
        this.position = new Vector2f(position);
        this.initialVelocity = new Vector2f(velocity);
        this.velocity = new Vector2f(velocity);
        this.acceleration = new Vector2f(0, 0);
        this.mass = mass;
        this.angularVelocity = 0.0f;
        this.torque = 0.0f;
        //this.momentOfInertia = 0.7f * mass * radius * radius;
        momentOfInertia = 0.5f * mass * radius * radius;
        this.rotation = 0; // Initialize rotation angle to 0
        this.isRotating = true;
        setSprite();
    }

    public void setSprite() {
        File file = new File("src/main/java/Assets/balls/Yarn_Ball_2.png");
        try {
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(float dt, float screenWidth, float screenHeight) {
        System.out.println(torque);
        angularAcceleration = torque / momentOfInertia;
        //System.out.println(ballGrab + "\n" + "ballpos" + position);
        // add gravity
        if (!ballGrab) {
            Vector2f gravity = new Vector2f(0, 0.01f);
            applyForce(gravity);
            updateAngularVelocity(dt);
            this.velocity.add(this.acceleration.mul(dt));
            this.angularVelocity -= this.angularVelocity * dt;
            // update position based on velocity
            this.position.add(this.velocity.mul(dt));
            updateRotation(dt);

            if (this.position.y >= 375) {
                applyTorque(this.velocity.x * radius);
            } else {
                applyTorque(this.velocity.x * 10);
            }
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
        if (this.position.y >= 410) {
            this.velocity.x *= (1.0f - 0.01);
            this.velocity.y *= (1.0f - 0.01);
            this.angularVelocity *= (1.0f - 0.01);
        }

        // handle collision with floor
        if (this.position.y - this.radius < 0) {
            this.position.y = this.radius;
            this.velocity.y = (-this.velocity.y * 0.9f);
        } else if (this.position.y + this.radius > screenHeight - 15) {
            this.position.y = screenHeight - this.radius - 15;
            this.velocity.y = (-this.velocity.y * 0.9f);
        }

        //System.out.println(velocity);
    }
    public void draw(Graphics2D g2) {

        if (ballGrab) {
            position.x = xMouseGrab;
            position.y = yMouseGrab;
        }

        // Save the current transform
        AffineTransform savedTransform = g2.getTransform();

        // Apply the translation to the position of the ball
        g2.translate(position.x, position.y);

        // Apply the rotation to the graphics context
        g2.rotate(angle);

        // Draw the ball
        g2.drawImage(sprite, -sprite.getWidth() / 2, -sprite.getHeight() / 2, null);

        // Restore the previous transform
        g2.setTransform(savedTransform);
    }


    public void applyForce(Vector2f force) {
        // apply force to acceleration
        Vector2f acceleration = force.mul(1.0f / this.mass);
        this.acceleration.add(acceleration);;
    }
    public void applyForce(Vector2f force, float torque) {

    }

    public void updateRotation(float dt) {
        // calculate angular acceleration based on current torque and moment of inertia
        angularAcceleration = torque / momentOfInertia;
        // update angular velocity based on current acceleration
        angularVelocity += angularAcceleration * dt;
        // update rotation angle based on current angular velocity
        angle += angularVelocity * dt;
        // reset torque to 0 to avoid accumulating it over time
        torque = 0;
    }

    @Override
    public void applyTorque(float torque) {
        this.torque += torque;
    }

    @Override
    public void updateAngularVelocity(float dt) {
        angularVelocity += angularAcceleration * dt;
        //torque = 0.0f;
    }

    @Override
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public void setTorque(float torque) {
        this.torque = torque;
    }

    @Override
    public float getTorque() {
        return torque;
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
