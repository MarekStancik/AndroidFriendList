package com.example.myfriends;

import android.content.Context;

import com.example.myfriends.Model.BEFriend;

public class DataAccessFactory {

    static SQLiteFriendsDAO sqLiteFriendsDAO;

    public static IDataAccess<BEFriend> getInstance(Context ctx){
        if(sqLiteFriendsDAO == null)
            sqLiteFriendsDAO = new SQLiteFriendsDAO(ctx);
        return sqLiteFriendsDAO;
    }
}
