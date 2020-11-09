package com.unicauca.domifoods.modelsOrder;

public class OrderRequest {
    private double total_to_pay;
    private int estimated_time;
    private String current_address;
    private double longitude;
    private double latitude;
    private String observation;
    private String  state;
    private int client;

    public OrderRequest() {
    }

    public OrderRequest(double total_to_pay, int estimated_time, String current_address, double longitude, double latitude, String observation, int client) {
        this.total_to_pay = total_to_pay;
        this.estimated_time = estimated_time;
        this.current_address = current_address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.observation = observation;
        this.state = "Pendiente";
        this.client = client;
    }

    public double getTotal_to_pay() {
        return total_to_pay;
    }

    public void setTotal_to_pay(double total_to_pay) {
        this.total_to_pay = total_to_pay;
    }

    public int getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(int estimated_time) {
        this.estimated_time = estimated_time;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }
}
