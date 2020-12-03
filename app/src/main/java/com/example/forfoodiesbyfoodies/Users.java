package com.example.forfoodiesbyfoodies;


import android.os.Parcel;
import android.os.Parcelable;

//Java class for handling Users objects
public class Users implements Parcelable {

 //Declaration
String fname, lname, email, password;

//Constructor of the class with its arguments for saving data into Firebase
    public Users(String fname, String lname, String email, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
    }

 //Empty constructor for reading the data from Firebase
    public Users() {
    }

 //Parcelable implementations
    protected Users(Parcel in) {
        fname = in.readString();
        lname = in.readString();
        email = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(email);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

 //Setters and getters
    public String getFname() { return fname; }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

}
