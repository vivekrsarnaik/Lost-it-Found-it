package com.example.lostitfoundit;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.Date;

@Entity(tableName = "post",
        foreignKeys = {@ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "creator",
                onDelete = ForeignKey.CASCADE)
        })
public class Post implements Parcelable {

    enum STATUS {
        LOST,
        FOUND,
        CLAIMED,
        PENDING
    }

    @PrimaryKey(autoGenerate = true)
    public int pid;
    public int creator;
    @NonNull
    @ColumnInfo(name = "item_name")
    public String itemName;
    public String itemDescription;
    public String location;
    @NonNull
    public STATUS status;
    public String reportedDate;
    public String image;


    public Post(int creator, @NonNull String itemName, String itemDescription, String location, @NonNull STATUS status, String reportedDate, String image) {
        this.creator = creator;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.location = location;
        this.status = status;
        this.reportedDate = reportedDate;
        this.image = image;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    private STATUS convertToStatus(String string) {
        STATUS newStatus;
        if (string.toUpperCase().equals(String.valueOf(STATUS.FOUND))) {
            newStatus = STATUS.FOUND;
        } else if (string.toUpperCase().equals(String.valueOf(STATUS.CLAIMED))) {
            newStatus = STATUS.CLAIMED;
        } else if (string.toUpperCase().equals(String.valueOf(STATUS.LOST))) {
            newStatus = STATUS.LOST;
        } else {
            newStatus = STATUS.PENDING;
        }

        return newStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.pid);
        parcel.writeInt(this.creator);
        parcel.writeString(this.itemName);
        parcel.writeString(this.itemDescription);
        parcel.writeString(this.location);
        parcel.writeString(String.valueOf(this.status));
        parcel.writeString(this.reportedDate);
        parcel.writeString(this.image);
    }

    protected Post(Parcel in) {
        pid = in.readInt();
        creator = in.readInt();
        itemName = in.readString();
        itemDescription = in.readString();
        location = in.readString();
        status = convertToStatus(in.readString());
        reportedDate = in.readString();
        image = in.readString();
    }

    @NonNull
    @Override
    public String toString() {
        return "{ " + this.pid + " " + this.itemName + " was reported by UserID " + this.creator + " on " + this.reportedDate + " }";
    }
}
