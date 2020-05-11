package com.smart.smartcity.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable, Cloneable {
    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @SerializedName("lastName")
    private String lastName;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @Expose
    @SerializedName("gender")
    private String gender;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("town")
    private String town;

    @Expose
    @SerializedName("interests")
    private ArrayList<Integer> interests = new ArrayList<>();

    @Expose
    @SerializedName("services")
    private ArrayList<Integer> services = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password, String dateOfBirth, String gender, String town, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.town = town;
    }

    private User(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
        dateOfBirth = in.readString();
        gender = in.readString();
        address = in.readString();
        town = in.readString();
        in.readList(interests, null);
        in.readList(services, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public List<Integer> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Integer> interests) {
        this.interests = interests;
    }

    public List<Integer> getServices() {
        return services;
    }

    public void setServices(ArrayList<Integer> services) {
        this.services = services;
    }

    public boolean isServiceActivated(int id) {
        return services.contains(id);
    }

    public void activateService(int id) {
        if (! isServiceActivated(id)) {
            services.add(id);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        User userClone = (User) super.clone();

        userClone.services = (ArrayList<Integer>) services.clone();
        userClone.interests = (ArrayList<Integer>) interests.clone();

        return userClone;
    }

    public void desactivateService(int id) {
        if (isServiceActivated(id)) {
            services.remove(new Integer(id));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(dateOfBirth);
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(town);
        dest.writeList(interests);
        dest.writeList(services);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
