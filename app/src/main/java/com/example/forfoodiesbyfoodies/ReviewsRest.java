package com.example.forfoodiesbyfoodies;

import android.os.Parcel;
import android.os.Parcelable;

//Java class for handling Restaurant Reviews objects
public class ReviewsRest implements Parcelable {

 //Declaration
    String nameR, reviewR, authorR;
    float ratingR;

 //Constructor of the class with its arguments for saving data into Firebase
    public ReviewsRest(String nameR, String reviewR, String authorR, float ratingR) {
        this.nameR = nameR;
        this.reviewR = reviewR;
        this.authorR = authorR;
        this.ratingR = ratingR;
    }

//Empty constructor for reading data fron Firebase
    public ReviewsRest() {
    }

 //Parcelable implementations
    protected ReviewsRest(Parcel in) {
        nameR = in.readString();
        reviewR = in.readString();
        authorR = in.readString();
        ratingR = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameR);
        dest.writeString(reviewR);
        dest.writeString(authorR);
        dest.writeFloat(ratingR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReviewsRest> CREATOR = new Creator<ReviewsRest>() {
        @Override
        public ReviewsRest createFromParcel(Parcel in) {
            return new ReviewsRest(in);
        }

        @Override
        public ReviewsRest[] newArray(int size) {
            return new ReviewsRest[size];
        }
    };

 //Setters and getters
    public String getNameR() {
        return nameR;
    }

    public void setNameR(String nameR) {
        this.nameR = nameR;
    }

    public String getReviewR() {
        return reviewR;
    }

    public void setReviewR(String reviewR) {
        this.reviewR = reviewR;
    }

    public String getAuthorR() {
        return authorR;
    }

    public void setAuthorR(String authorR) {
        this.authorR = authorR;
    }

    public float getRatingR() {
        return ratingR;
    }

    public void setRatingR(float ratingR) {
        this.ratingR = ratingR;
    }
}
