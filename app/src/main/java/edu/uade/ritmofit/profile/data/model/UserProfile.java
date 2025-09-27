package edu.uade.ritmofit.profile.data.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("id")
    private String userId;

    @SerializedName("nombre")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("foto")
    private  String foto;

    public UserProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
