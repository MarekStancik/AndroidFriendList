package com.example.myfriends;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.myfriends.Model.BEFriend;
import com.example.myfriends.Model.Friends;

public class MainActivity extends ListActivity {

    public static String TAG = "Friend2";

    private static final int FRIEND_DETAIL = 123;

    Friends m_friends;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Friends v2");
        m_friends = new Friends();
        setUpList();
    }

    private void setUpList(){
        String[] friends;

        friends = m_friends.getNames();

        ListAdapter adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        friends);

        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView parent, View v, int position,
                                long id) {

        Intent x = new Intent(this, DetailActivity.class);
        BEFriend friend = m_friends.getAll().get(position);
        x.putExtra("position",position);
        x.putExtra("friend",friend);
        startActivityForResult(x,FRIEND_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FRIEND_DETAIL)
        {
            switch (resultCode)
            {
                case RESULT_OK:
                    Bundle bundle = data.getExtras();
                    int position = bundle.getInt("position");
                    BEFriend friend = (BEFriend) data.getSerializableExtra("friend");
                    m_friends.getAll().set(position,friend);
                    setUpList();
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }

    }

}

