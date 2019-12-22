package fr.WarzouMc.SkyExpanderInternalPlugin.utils.graphics.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class RotateAroundVector {

    private double cosX;
    private double cosY;
    private double sinX;
    private double sinY;

    private double radius;
    private Particle particle;
    private double time = 0;

    public RotateAroundVector(double radius, Particle particle){
        this.radius = radius;
        this.particle = particle;
    }

    private void calculateDeviations(Vector direction) {
        Location directionalLoc = direction.toLocation(null).setDirection(direction);

        double yaw = Math.toRadians(directionalLoc.getYaw());
        double pitch = Math.toRadians(directionalLoc.getPitch()+90);

        cosX = Math.cos(pitch);
        sinX = Math.sin(pitch);
        cosY = Math.cos(-yaw);
        sinY = Math.sin(-yaw);
    }

    public void spawn(Location location, Vector direction) {
        calculateDeviations(direction);
        double theta = 0;
        while (theta <= 15){
            time++;
            theta += Math.PI/8;
            Location spawnLoc = location.clone().add(offsetFromCenter(theta + (time * 10)));
            spawnLoc.getWorld().spawnParticle(particle, spawnLoc, 1, 0, 0, 0, 0);
        }
    }

    private Vector offsetFromCenter(double degrees) {
        double radians = Math.toRadians(degrees);
        double x = Math.cos(radians)*radius;
        double z = Math.sin(radians)*radius;

        Vector vector = new Vector(x, 0, z);

        rotateAroundAxisX(vector, cosX, sinX);
        rotateAroundAxisY(vector, cosY, sinY);

        return vector;
    }


    private Vector rotateAroundAxisX(Vector vector, double cos, double sin) {
        double y = vector.getY() * cos - vector.getZ() * sin;
        double z = vector.getY() * sin + vector.getZ() * cos;
        return vector.setY(y).setZ(z);
    }

    private Vector rotateAroundAxisY(Vector vector, double cos, double sin) {
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;
        return vector.setX(x).setZ(z);
    }

}
