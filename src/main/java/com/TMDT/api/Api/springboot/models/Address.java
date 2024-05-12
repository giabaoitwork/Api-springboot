package com.TMDT.api.Api.springboot.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int customer_id;
    private int province_id;
    private String province_value;
    private int district_id;
    private String district_value;
    private int ward_id;
    private String ward_value;
    private String sub_address;
    private int status;

    public Address() {
    }
    public Address(int customer_id, int province_id, String province_value, int district_id, String district_value, int ward_id, String ward_value, String sub_address, int status) {
        this.customer_id = customer_id;
        this.province_id = province_id;
        this.province_value = province_value;
        this.district_id = district_id;
        this.district_value = district_value;
        this.ward_id = ward_id;
        this.ward_value = ward_value;
        this.sub_address = sub_address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer_id;
    }

    public void setCustomer(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getProvince_value() {
        return province_value;
    }

    public void setProvince_value(String province_value) {
        this.province_value = province_value;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_value() {
        return district_value;
    }

    public void setDistrict_value(String district_value) {
        this.district_value = district_value;
    }

    public int getWard_id() {
        return ward_id;
    }

    public void setWard_id(int ward_id) {
        this.ward_id = ward_id;
    }

    public String getWard_value() {
        return ward_value;
    }

    public void setWard_value(String ward_value) {
        this.ward_value = ward_value;
    }

    public String getSub_address() {
        return sub_address;
    }

    public void setSub_address(String sub_address) {
        this.sub_address = sub_address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
