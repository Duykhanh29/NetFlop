package com.example.netflop.data.models.remote.movies;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class MovieVideos  implements Parcelable {
    private int id;
    private List<Video> results;

    protected MovieVideos(Parcel in) {
        id = in.readInt();
    }

    public static final Creator<MovieVideos> CREATOR = new Creator<MovieVideos>() {
        @Override
        public MovieVideos createFromParcel(Parcel in) {
            return new MovieVideos(in);
        }

        @Override
        public MovieVideos[] newArray(int size) {
            return new MovieVideos[size];
        }
    };

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }

    public List<Video> getResults() { return results; }
    public void setResults(List<Video> value) { this.results = value; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
    }
}
