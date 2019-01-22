package com.konkr.Models;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SpotifyModel {
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


public static class Tracks implements Parcelable {

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeValue(this.total);
    }

    public Tracks() {
    }

    protected Tracks(Parcel in) {
        this.href = in.readString();
        this.total = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Tracks> CREATOR = new Parcelable.Creator<Tracks>() {
        @Override
        public Tracks createFromParcel(Parcel source) {
            return new Tracks(source);
        }

        @Override
        public Tracks[] newArray(int size) {
            return new Tracks[size];
        }
    };
}
    public static class Owner implements Parcelable {

        @SerializedName("external_urls")
        @Expose
        private ExternalUrls_ externalUrls;
        @SerializedName("href")
        @Expose
        private String href;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("uri")
        @Expose
        private String uri;

        public ExternalUrls_ getExternalUrls() {
            return externalUrls;
        }

        public void setExternalUrls(ExternalUrls_ externalUrls) {
            this.externalUrls = externalUrls;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.externalUrls, flags);
            dest.writeString(this.href);
            dest.writeString(this.id);
            dest.writeString(this.type);
            dest.writeString(this.uri);
        }

        public Owner() {
        }

        protected Owner(Parcel in) {
            this.externalUrls = in.readParcelable(ExternalUrls_.class.getClassLoader());
            this.href = in.readString();
            this.id = in.readString();
            this.type = in.readString();
            this.uri = in.readString();
        }

        public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
            @Override
            public Owner createFromParcel(Parcel source) {
                return new Owner(source);
            }

            @Override
            public Owner[] newArray(int size) {
                return new Owner[size];
            }
        };
    }
    public static class Item implements Parcelable {

        @SerializedName("collaborative")
        @Expose
        private Boolean collaborative;
        @SerializedName("external_urls")
        @Expose
        private ExternalUrls externalUrls;
        @SerializedName("href")
        @Expose
        private String href;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("images")
        @Expose
        private List<Images> images ;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("owner")
        @Expose
        private Owner owner;
        @SerializedName("public")
        @Expose
        private Boolean _public;
        @SerializedName("snapshot_id")
        @Expose
        private String snapshotId;
        @SerializedName("tracks")
        @Expose
        private Tracks tracks;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("uri")
        @Expose
        private String uri;

        public Boolean getCollaborative() {
            return collaborative;
        }

        public void setCollaborative(Boolean collaborative) {
            this.collaborative = collaborative;
        }

        public ExternalUrls getExternalUrls() {
            return externalUrls;
        }

        public void setExternalUrls(ExternalUrls externalUrls) {
            this.externalUrls = externalUrls;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Images> getImages() {
            return images;
        }



        public void setImages(List<Images> images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Boolean getPublic() {
            return _public;
        }

        public void setPublic(Boolean _public) {
            this._public = _public;
        }

        public String getSnapshotId() {
            return snapshotId;
        }

        public void setSnapshotId(String snapshotId) {
            this.snapshotId = snapshotId;
        }

        public Tracks getTracks() {
            return tracks;
        }

        public void setTracks(Tracks tracks) {
            this.tracks = tracks;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Item() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.collaborative);
            dest.writeParcelable(this.externalUrls, flags);
            dest.writeString(this.href);
            dest.writeString(this.id);
            dest.writeTypedList(this.images);
            dest.writeString(this.name);
            dest.writeParcelable(this.owner, flags);
            dest.writeValue(this._public);
            dest.writeString(this.snapshotId);
            dest.writeParcelable(this.tracks, flags);
            dest.writeString(this.type);
            dest.writeString(this.uri);
        }

        protected Item(Parcel in) {
            this.collaborative = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.externalUrls = in.readParcelable(ExternalUrls.class.getClassLoader());
            this.href = in.readString();
            this.id = in.readString();
            this.images = in.createTypedArrayList(Images.CREATOR);
            this.name = in.readString();
            this.owner = in.readParcelable(Owner.class.getClassLoader());
            this._public = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.snapshotId = in.readString();
            this.tracks = in.readParcelable(Tracks.class.getClassLoader());
            this.type = in.readString();
            this.uri = in.readString();
        }

        public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel source) {
                return new Item(source);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };
    }
    public static class ExternalUrls_ implements Parcelable {

        @SerializedName("spotify")
        @Expose
        private String spotify;

        public String getSpotify() {
            return spotify;
        }

        public void setSpotify(String spotify) {
            this.spotify = spotify;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.spotify);
        }

        public ExternalUrls_() {
        }

        protected ExternalUrls_(Parcel in) {
            this.spotify = in.readString();
        }

        public static final Parcelable.Creator<ExternalUrls_> CREATOR = new Parcelable.Creator<ExternalUrls_>() {
            @Override
            public ExternalUrls_ createFromParcel(Parcel source) {
                return new ExternalUrls_(source);
            }

            @Override
            public ExternalUrls_[] newArray(int size) {
                return new ExternalUrls_[size];
            }
        };
    }
public static class Images implements Parcelable {
        private int height;
        private  String url;
        private  int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.height);
        dest.writeString(this.url);
        dest.writeInt(this.width);
    }

    public Images() {
    }

    protected Images(Parcel in) {
        this.height = in.readInt();
        this.url = in.readString();
        this.width = in.readInt();
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
    public static  class ExternalUrls implements Parcelable {

        @SerializedName("spotify")
        @Expose
        private String spotify;

        public String getSpotify() {
            return spotify;
        }

        public void setSpotify(String spotify) {
            this.spotify = spotify;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.spotify);
        }

        public ExternalUrls() {
        }

        protected ExternalUrls(Parcel in) {
            this.spotify = in.readString();
        }

        public static final Parcelable.Creator<ExternalUrls> CREATOR = new Parcelable.Creator<ExternalUrls>() {
            @Override
            public ExternalUrls createFromParcel(Parcel source) {
                return new ExternalUrls(source);
            }

            @Override
            public ExternalUrls[] newArray(int size) {
                return new ExternalUrls[size];
            }
        };
    }
}