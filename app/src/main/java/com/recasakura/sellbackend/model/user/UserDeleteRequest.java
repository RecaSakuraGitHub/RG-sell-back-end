package com.recasakura.sellbackend.model.user;

public class UserDeleteRequest {
    private String email;
    private String phone;

    public UserDeleteRequest() {}

    public String getEmail() { return this.email; }
    public String getPhone() { return this.phone; }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
