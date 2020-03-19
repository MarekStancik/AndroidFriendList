package com.example.myfriends.Model;

import java.io.Serializable;

public class BEFriend implements Serializable {

    public static class FriendBuilder{
        private BEFriend obj;

        protected FriendBuilder(String name){
            obj = new BEFriend(name,"",false);
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

        public BEFriend asFriend(boolean favourite){
            obj.m_isFavorite = favourite;
            return obj;
        }
    }

    public static FriendBuilder create(String name) { return new FriendBuilder(name);}

    private String m_name;
    private String m_phone;
    private String m_email;
    private String m_url;
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

}

