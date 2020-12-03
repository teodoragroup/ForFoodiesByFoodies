package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

//Java class for handling Stall objects
public class Stall implements Parcelable {

 //Declaration
    String nameS;
    String locationS;
    String descS;
    String addedByS;
    String urlS;

 //Constructor of the class with its arguments for saving data into Firebase
    public Stall(String nameS, String locationS, String descS, String addedByS, String urlS) {
        this.nameS = nameS;
        this.locationS = locationS;
        this.descS = descS;
        this.addedByS = addedByS;
        this.urlS = urlS;
    }

 //Empty constructor for reading the data from Firebase
    public Stall() {
    }

 //Parcelable implementations
    protected Stall(Parcel in) {
        nameS = in.readString();
        locationS = in.readString();
        descS = in.readString();
        addedByS = in.readString();
        urlS = in.readString();
    }

    public static final Creator<Stall> CREATOR = new Creator<Stall>() {
        @Override
        public Stall createFromParcel(Parcel in) {
            return new Stall(in);
        }

        @Override
        public Stall[] newArray(int size) {
            return new Stall[size];
        }
    };

 //Setters and getters
    public String getNameS() {
        return nameS;
    }

    public void setNameS(String nameS) {
        this.nameS = nameS;
    }

    public String getLocationS() {
        return locationS;
    }

    public void setLocationS(String locationS) {
        this.locationS = locationS;
    }

    public String getDescS() {
        return descS;
    }

    public void setDescS(String descS) {
        this.descS = descS;
    }

    public String getAddedByS() {
        return addedByS;
    }

    public void setAddedByS(String addedByS) {
        this.addedByS = addedByS;
    }

    public String getUrlS() {
        return urlS;
    }

    public void setUrlS(String urlS) {
        this.urlS = urlS;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameS);
        dest.writeString(locationS);
        dest.writeString(descS);
        dest.writeString(addedByS);
        dest.writeString(urlS);
    }
}
