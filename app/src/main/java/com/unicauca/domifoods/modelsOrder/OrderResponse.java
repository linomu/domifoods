package com.unicauca.domifoods.modelsOrder;

public class OrderResponse {
    private int id;
    private double total_to_pay;
    private double estimated_time;
    private String current_address;
    private double longitude;
    private double latitude;
    private String observation;
    private String state;
    private String created_at;
    private String updated_at;
    private int  client;

    public OrderResponse() {
    }

    public OrderResponse(int id, double total_to_pay, double estimated_time, String current_address, double longitude, double latitude, String observation, String state, String created_at, String updated_at, int client) {
        this.id = id;
        this.total_to_pay = total_to_pay;
        this.estimated_time = estimated_time;
        this.current_address = current_address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.observation = observation;
        this.state = state;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal_to_pay() {
        return total_to_pay;
    }

    public void setTotal_to_pay(double total_to_pay) {
        this.total_to_pay = total_to_pay;
    }

    public double getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(double estimated_time) {
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", total_to_pay=" + total_to_pay +
                ", estimated_time=" + estimated_time +
                ", current_address='" + current_address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", observation='" + observation + '\'' +
                ", state='" + state + '\'' +
                ", client=" + client +
                '}';
    }
}
