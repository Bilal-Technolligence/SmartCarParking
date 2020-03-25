package com.example.smartcarparking;

public class UserAttr {
    String Id;
    String Name;
    String Email;
    String Contact;
    String Age;
    String Address;
    String ImageUrl;
    String ParkingName;
    String ParkingSpace;
    String Category;
    int Status;

    public UserAttr() {
    }

    public UserAttr(String id, String name, String email, String contact, String age, String address, String imageUrl, String parkingName, String parkingSpace, String category, int status) {
        Id = id;
        Name = name;
        Email = email;
        Contact = contact;
        Age = age;
        Address = address;
        ImageUrl = imageUrl;
        ParkingName = parkingName;
        ParkingSpace = parkingSpace;
        Category = category;
        Status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
    public String getCategory() {
        return Category;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getParkingName() {
        return ParkingName;
    }

    public void setParkingName(String parkingName) {
        ParkingName = parkingName;
    }

    public String getParkingSpace() {
        return ParkingSpace;
    }

    public void setParkingSpace(String parkingSpace) {
        ParkingSpace = parkingSpace;
    }
}
