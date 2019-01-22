package com.konkr.Models;

/**
 * Created by Android on 6/29/2018.
 */

public class Data {

    public String index;
    private MyPlayList person;
    private boolean showIndex;

    public Data(String index, MyPlayList person, boolean showIndex) {
        this.index = index;
        this.person = person;
        this.showIndex = showIndex;
    }

    @Override
    public String toString() {
        return "Data{" +
                "index='" + index + '\'' +
                ", person=" + person +
                ", showIndex=" + showIndex +
                '}';
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public MyPlayList getPerson() {
        return person;
    }

    public void setPerson(MyPlayList person) {
        this.person = person;
    }

    public boolean isShowIndex() {
        return showIndex;
    }

    public void setShowIndex(boolean showIndex) {
        this.showIndex = showIndex;
    }

    public static class MyPlayList {

        String songName;
        String artisName;
        String image;
        String uri;
        boolean isPlaying;
        long duration_ms;

        public MyPlayList(String songName, String artisName, String image, String uri, long duration_ms) {
            this.songName = songName;
            this.artisName = artisName;
            this.image = image;
            this.uri = uri;
            this.duration_ms = duration_ms;
        }

        public long getDuration_ms() {
            return duration_ms;
        }

        public void setDuration_ms(long duration_ms) {
            this.duration_ms = duration_ms;
        }

        public boolean isPlaying() {
            return isPlaying;
        }

        public void setPlaying(boolean playing) {
            isPlaying = playing;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

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
        //        public MyPlayList(int userPhoto, String songTitle, String artistName, int duration) {
//            this.userPhoto = userPhoto;
//            this.songTitle = songTitle;
//            this.artistName = artistName;
//            this.duration = duration;
//        }
//
//        public int getUserPhoto() {
//            return userPhoto;
//        }
//
//        public void setUserPhoto(int userPhoto) {
//            this.userPhoto = userPhoto;
//        }
//
//        public String getSongTitle() {
//            return songTitle;
//        }
//
//        public void setSongTitle(String songTitle) {
//            this.songTitle = songTitle;
//        }
//
//        public String getArtistName() {
//            return artistName;
//        }
//
//        public void setArtistName(String artistName) {
//            this.artistName = artistName;
//        }
//
//        public int getDuration() {
//            return duration;
//        }
//
//        public void setDuration(int duration) {
//            this.duration = duration;
//        }
    }


}
