package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

//Java class for handling Stall Reviews objects
public class ReviewsStall implements Parcelable {

//Declaration
    String nameS, reviewS, authorS;
    float ratingS;

 //Constructor of the class with its arguments for saving data into Firebase
    public ReviewsStall(String nameS, String reviewS, String authorS, float ratingS) {
        this.nameS = nameS;
        this.reviewS = reviewS;
        this.authorS = authorS;
        this.ratingS = ratingS;
    }

 //Empty constructor for reading data from Firebase
    public ReviewsStall() {
    }

 //Parcelable implementations
    protected ReviewsStall(Parcel in) {
        nameS = in.readString();
        reviewS = in.readString();
        authorS = in.readString();
        ratingS = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameS);
        dest.writeString(reviewS);
        dest.writeString(authorS);
        dest.writeFloat(ratingS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReviewsStall> CREATOR = new Creator<ReviewsStall>() {
        @Override
        public ReviewsStall createFromParcel(Parcel in) {
            return new ReviewsStall(in);
        }

        @Override
        public ReviewsStall[] newArray(int size) {
            return new ReviewsStall[size];
        }
    };

  //Setters and getters
    public String getNameS() {
        return nameS;
    }

    public void setNameS(String nameS) {
        this.nameS = nameS;
    }

    public String getReviewS() {
        return reviewS;
    }

    public void setReviewS(String reviewS) {
        this.reviewS = reviewS;
    }

    public String getAuthorS() {
        return authorS;
    }

    public void setAuthorS(String authorS) {
        this.authorS = authorS;
    }

    public float getRatingS() {
        return ratingS;
    }

    public void setRatingS(float ratingS) {
        this.ratingS = ratingS;
    }
}
