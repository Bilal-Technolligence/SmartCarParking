package com.example.smartcarparking;

public class ParkAttr {
    String id;
    String admin;
    String name;
    String slots;
    String latitude;
    String longitude;
    String price;
    String pic;
    String available;
    String address;
    String title;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ParkAttr(String id, String admin, String name, String slots, String latitude, String longitude, String price, String pic, String available, String address, String title, String status) {
        this.id = id;
        this.admin = admin;
        this.name = name;
        this.slots = slots;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.pic = pic;
        this.available = available;
        this.address = address;
        this.title = title;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public ParkAttr() {
    }
}
