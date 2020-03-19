package com.example.myfriends.Model;

import android.graphics.Bitmap;

import com.example.myfriends.SerializableBitmap;

import java.io.Serializable;

public class BEFriend implements Serializable {

    public static class FriendBuilder{
        private BEFriend obj;

        protected FriendBuilder(long id,String name){
            obj = new BEFriend(name,"",false);
            obj.id = id;
        }

        public FriendBuilder withNumber(String phone){
            obj.m_phone = phone;
            return this;
        }

        public FriendBuilder withEmail(String email){
            obj.m_email = email;
            return this;
        }

        public FriendBuilder withWebsite(String url){
            obj.m_url = url;
            return this;
        }

        public FriendBuilder withPhoto(Bitmap photo){
            if(photo != null)
                obj.m_photo = new SerializableBitmap(photo);
            return this;
        }

        public BEFriend asFriend(boolean favourite){
            obj.m_isFavorite = favourite;
            return obj;
        }
    }

    public static FriendBuilder create(long id,String name) { return new FriendBuilder(id,name);}

    private long id;
    private String m_name;
    private String m_phone;
    private String m_email;
    private String m_url;
    private SerializableBitmap m_photo;
    private Boolean m_isFavorite;

    public BEFriend(String name, String phone) {
        this(name, phone, false);
    }

    public BEFriend(String name, String phone, Boolean isFavorite) {
        m_name = name;
        m_phone = phone;
        m_isFavorite = isFavorite;
        m_email = "";
        m_url = "";
    }

    public String getPhone() {
        return m_phone;
    }


    public String getName() {
        return m_name;
    }

    public Boolean isFavorite() { return m_isFavorite; }

    public String getEmail() { return m_email; }

    public String getUrl() { return m_url;}

    public Bitmap getPhoto(){
        return m_photo != null ? m_photo.getBitmap() : null;
    }

    public long getId() {
        return id;
    }
}

