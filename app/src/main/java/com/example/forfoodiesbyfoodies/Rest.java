package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

//Java class for handling Restaurant objects
public class Rest implements Parcelable {

//Declaration
    String nameR;
    String locationR;
    String descR;
    String addedByR;
    String urlR;

 //Constructor of the class with its arguments for saving data into Firebase
    public Rest(String nameR, String locationR, String descR, String addedByR, String urlR) {
        this.nameR = nameR;
        this.locationR = locationR;
        this.descR = descR;
        this.addedByR = addedByR;
        this.urlR = urlR;
    }
//Empty constructor for reading data fron Firebase
    public Rest() {
    }

//Parcelable implementations
    protected Rest(Parcel in) {
        nameR = in.readString();
        locationR = in.readString();
        descR = in.readString();
        addedByR = in.readString();
        urlR = in.readString();
    }

    public static final Creator<Rest> CREATOR = new Creator<Rest>() {
        @Override
        public Rest createFromParcel(Parcel in) {
            return new Rest(in);
        }

        @Override
        public Rest[] newArray(int size) {
            return new Rest[size];
        }
    };

 //Setters and getters
    public String getNameR() {
        return nameR;
    }

    public void setNameR(String nameR) {
        this.nameR = nameR;
    }

    public String getLocationR() {
        return locationR;
    }

    public void setLocationR(String locationR) {
        this.locationR = locationR;
    }

    public String getDescR() {
        return descR;
    }

    public void setDescR(String descR) {
        this.descR = descR;
    }

    public String getAddedByR() {
        return addedByR;
    }

    public void setAddedByR(String addedByR) {
        this.addedByR = addedByR;
    }

    public String getUrlR() {
        return urlR;
    }

    public void setUrlR(String urlR) {
        this.urlR = urlR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameR);
        dest.writeString(locationR);
        dest.writeString(descR);
        dest.writeString(addedByR);
        dest.writeString(urlR);
    }
}
