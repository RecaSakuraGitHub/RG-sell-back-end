package com.recasakura.sellbackend.model.user;

public class UserCreateRequest {
    private String name;
    private String email;
    private String phone;

    public UserCreateRequest(){}

    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public String getPhone() { return this.phone; }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
