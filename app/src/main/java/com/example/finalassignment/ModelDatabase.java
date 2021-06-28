package com.example.finalassignment;

public class ModelDatabase {
    String district,thana,address,floor,area,details,amount,advance,phone;

    public ModelDatabase(){}

    public ModelDatabase(String district, String thana, String address, String floor, String area, String details, String amount, String advance, String phone) {
        this.district = district;
        this.thana = thana;
        this.address = address;
        this.floor = floor;
        this.area = area;
        this.details = details;
        this.amount = amount;
        this.advance = advance;
        this.phone = phone;

    }

    public String getDistrict() {
        return district;
    }

    public String getThana() {
        return thana;
    }

    public String getAddress() {
        return address;
    }

    public String getFloor() { return floor; }

    public String getArea() {
        return area;
    }

    public String getDetails() {
        return details;
    }

    public String getAmount() {
        return amount;
    }

    public String getAdvance() {
        return advance;
    }

    public String getPhone() {
        return phone;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
