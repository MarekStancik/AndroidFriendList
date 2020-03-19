package com.example.myfriends;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.myfriends.Model.BEFriend;

import java.util.ArrayList;

public class SQLiteFriendsDAO implements IDataAccess<BEFriend> {

    private final static String DB_NAME = "friends.db";
    private final static String TABLE_NAME = "friends";
    private final static int DB_VER = 2;

    private SQLiteDatabase db;

    public SQLiteFriendsDAO(Context ctx){
        OpenHelper helper = new OpenHelper(ctx);
        db = helper.getWritableDatabase();
    }

    @Override
    public ArrayList<BEFriend> readAll() {
        ArrayList<BEFriend> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, new String[] { "id", "name","email","phone","url","isFav","img" },
                null, null, null, null, "name");
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                list.add(BEFriend.create(id,cursor.getString(1))
                    .withEmail(cursor.getString(2))
                        .withNumber(cursor.getString(3))
                            .withWebsite(cursor.getString(4))
                                .withPhoto(BitmapUtil.fromBytes(cursor.getBlob(6)))
                                    .asFriend(cursor.getInt(5) != 0));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }

    @Override
    public BEFriend create(BEFriend obj) {
        final String INSERT_STMT = "INSERT INTO " + TABLE_NAME +
                " (name,email,phone,url,isFav,img)" +
                " VALUES (?,?,?,?,?,?)";

        SQLiteStatement stmt = db.compileStatement(INSERT_STMT);

        int i = 1;
        stmt.bindString(i++,obj.getName());
        stmt.bindString(i++,obj.getEmail());
        stmt.bindString(i++,obj.getPhone());
        stmt.bindString(i++,obj.getUrl());
        stmt.bindLong(i++,obj.isFavorite() ? 1 : 0);
        if(obj.getPhoto() != null)
            stmt.bindBlob(i++,BitmapUtil.toBytes(obj.getPhoto()));
        else
            stmt.bindNull(i++);

        long id = stmt.executeInsert();

        return BEFriend.create(id,obj.getName())
                .withPhoto(obj.getPhoto())
                .withEmail(obj.getEmail())
                .withNumber(obj.getPhone())
                .withWebsite(obj.getUrl())
                .asFriend(obj.isFavorite());
    }

    @Override
    public void delete(int id) {
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ID=" + id);
    }

    @Override
    public void update(BEFriend obj) {
        String UPDATE_STMT = "UPDATE " + TABLE_NAME + " SET name=?,email=?,phone=?,isFav=?,url=?,img=? WHERE id=?";

        SQLiteStatement stmt = db.compileStatement(UPDATE_STMT);

        stmt.bindString(1,obj.getName());
        stmt.bindString(2,obj.getEmail());
        stmt.bindString(3,obj.getPhone());
        stmt.bindLong(4,obj.isFavorite() ? 1 : 0);
        stmt.bindString(5,obj.getUrl());
        if(obj.getPhoto() != null)
            stmt.bindBlob(6,BitmapUtil.toBytes(obj.getPhoto()));
        else
            stmt.bindNull(6);
        stmt.bindLong(7,obj.getId());

        stmt.executeUpdateDelete();
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context)
        {
            super(context, DB_NAME, null, DB_VER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY, name TEXT, email TEXT, phone TEXT,isFav BOOL,url TEXT,img BLOB)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
