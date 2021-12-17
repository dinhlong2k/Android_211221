package com.example.android_71221.Model;

public class Address {
    private int id;
    private String province;
    private String district;
    private String detail;

    public Address(int id, String province, String district, String detail) {
        this.id = id;
        this.province = province;
        this.district = district;
        this.detail = detail;
    }

    public Address(String province, String district, String detail) {
        this.province = province;
        this.district = district;
        this.detail = detail;
    }

    public Address() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddressUser(){
        if(detail != null && district != null && province !=null){
            return detail + ", " +district +", " +province;
        }
        else return "";
    }
}
