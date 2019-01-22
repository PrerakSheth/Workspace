package com.konkr.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicAndVideo implements Parcelable {
   String songName;
   String artisName;
   String image;
   String uri;
   long duration_ms;

    public MusicAndVideo(String songName, String artisName, String image, String uri, long duration_ms) {
        this.songName = songName;
        this.artisName = artisName;
        this.image = image;
        this.uri = uri;
        this.duration_ms = duration_ms;
    }

    protected MusicAndVideo(Parcel in) {
        songName = in.readString();
        artisName = in.readString();
        image = in.readString();
        uri = in.readString();
        duration_ms = in.readLong();
    }

    public static final Creator<MusicAndVideo> CREATOR = new Creator<MusicAndVideo>() {
        @Override
        public MusicAndVideo createFromParcel(Parcel in) {
            return new MusicAndVideo(in);
        }

        @Override
        public MusicAndVideo[] newArray(int size) {
            return new MusicAndVideo[size];
        }
    };

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtisName() {
        return artisName;
    }

    public void setArtisName(String artisName) {
        this.artisName = artisName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(long duration_ms) {
        this.duration_ms = duration_ms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(songName);
        parcel.writeString(artisName);
        parcel.writeString(image);
        parcel.writeString(uri);
        parcel.writeLong(duration_ms);
    }
}
