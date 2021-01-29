package com.example.uberclone.Models;

public class Driver {

    String id;
    String name;
    String email;
    String VehicleBrand;
    String VehiclePlate;

    public Driver(String id, String name, String email,String vehicleBrand, String vehiclePlate) {
        this.id = id;
        this.name = name;
        this.email = email;
        VehicleBrand = vehicleBrand;
        VehiclePlate = vehiclePlate;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicleBrand() {
        return VehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        VehicleBrand = vehicleBrand;
    }

    public String getVehiclePlate() {
        return VehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        VehiclePlate = vehiclePlate;
    }
}
