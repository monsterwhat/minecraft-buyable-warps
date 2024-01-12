package com.playdeca.models;

public class warp {

    String warpName;
    Double x,y,z;
    String world;
    Float pitch, yaw;

    public warp(String warpName, Double x, Double y, Double z, String world, Float pitch, Float yaw){
        this.warpName = warpName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public String getWarpName() {
        return warpName;
    }

    public void setWarpName(String warpName) {
        this.warpName = warpName;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public Float getPitch() {
        return pitch;
    }

    public void setPitch(Float pitch) {
        this.pitch = pitch;
    }

    public Float getYaw() {
        return yaw;
    }

    public void setYaw(Float yaw) {
        this.yaw = yaw;
    }
}
