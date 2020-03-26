package com.example.myfriends.Model;

import android.graphics.Bitmap;

import com.example.myfriends.Utils.SerializableBitmap;

import java.io.Serializable;
import java.util.Date;

public class BEFriend implements Serializable {

    public static class FriendBuilder{
        private BEFriend obj;

        protected FriendBuilder(long id,String name){
            obj = new BEFriend(name,"",false);
            obj.id = id;
        }

        public FriendBuilder withPhoneNumber(String phone){
            obj.phone = phone;
            return this;
        }

        public FriendBuilder birthAt(Date birthDate){
            obj.birthday = birthDate;
            return this;
        }

        public FriendBuilder livesAt(String address){
            obj.address = address;
            return this;
        }

        public FriendBuilder withEmail(String email){
            obj.email = email;
            return this;
        }

        public FriendBuilder withWebsite(String url){
            obj.url = url;
            return this;
        }

        public FriendBuilder withPhoto(Bitmap photo){
            if(photo != null)
                obj.photo = new SerializableBitmap(photo);
            return this;
        }

        public BEFriend asFriend(boolean favourite){
            obj.isFavorite = favourite;
            return obj;
        }
    }

    public static FriendBuilder create(long id,String name) { return new FriendBuilder(id,name);}

    private long id;
    private String name;
    private String phone;
    private String email;
    private String url;
    private String address;
    private String location;
    private Date birthday;
    private SerializableBitmap photo;
    private Boolean isFavorite;

    public BEFriend(String name, String phone) {
        this(name, phone, false);
    }

    public BEFriend(String name, String phone, Boolean isFavorite) {
        this.name = name;
        this.phone = phone;
        this.isFavorite = isFavorite;
        email = "";
        url = "";
        address = "";
        birthday = new Date();
    }

    public String getPhone() {
        return phone;
    }


    public String getName() {
        return name;
    }

    public Boolean isFavorite() { return isFavorite; }

    public String getEmail() { return email; }

    public String getUrl() { return url;}

    public Bitmap getPhoto(){
        return photo != null ? photo.getBitmap() : null;
    }

    public long getId() {
        return id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }
}

