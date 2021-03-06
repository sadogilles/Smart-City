package com.smart.smartcity.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smart.smartcity.util.SubscriptionState;

import java.util.List;

public class Network implements Parcelable {
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("authorId")
    private int authorId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("imageUrl")
    private String imageUrl;

    @Expose
    @SerializedName("subscriptions")
    private List<Subscription> subscriptions;

    @Expose
    @SerializedName("private")
    private boolean privateAccess;

    private String localImageUri;

    private Bitmap imageBitmap;

    public Network(int authorId, String name, String description, String localImageUri, boolean privateAccess) {
        this.authorId = authorId;
        this.name = name;
        this.description = description;
        this.localImageUri = localImageUri;
        this.privateAccess = privateAccess;
    }

    protected Network(Parcel in) {
        id = in.readInt();
        authorId = in.readInt();
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        localImageUri = in.readString();
        imageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Network> CREATOR = new Creator<Network>() {
        @Override
        public Network createFromParcel(Parcel in) {
            return new Network(in);
        }

        @Override
        public Network[] newArray(int size) {
            return new Network[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocalImageUri() {
        return localImageUri;
    }

    public void setLocalImageUri(String localImageUri) {
        this.localImageUri = localImageUri;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public boolean isPrivateAccess() {
        return privateAccess;
    }

    public void setPrivateAccess(boolean privateAccess) {
        this.privateAccess = privateAccess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(authorId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(localImageUri);
        dest.writeParcelable(imageBitmap, flags);
    }

    public SubscriptionState subscription(int userId) {
        SubscriptionState state = SubscriptionState.NO_SUBSCRIPTION;

        for (Subscription subscription : getSubscriptions()) {
            if (subscription.getUserId() == userId) {
                if (subscription.getState().equals("pending")) {
                    state = SubscriptionState.PENDING;
                } else if (subscription.getState().equals("accepted")) {
                    state = SubscriptionState.ACCEPTED;
                }
            }
        }

        return state;
    }
}
