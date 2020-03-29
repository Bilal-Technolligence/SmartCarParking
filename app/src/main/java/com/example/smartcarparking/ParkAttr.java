package com.example.smartcarparking;

public class ParkAttr {
    String id;
    String admin;
    String name;
    String length;
    String width;
    String latitude;
    String longitude;
    String price;
    String pic;
    String status;

    public ParkAttr(String id, String admin, String name, String length, String width, String latitude, String longitude, String price, String pic, String status) {
        this.id = id;
        this.admin = admin;
        this.name = name;
        this.length = length;
        this.width = width;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.pic = pic;
        this.status = status;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ParkAttr() {
    }
}
