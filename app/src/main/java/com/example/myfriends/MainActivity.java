package com.example.myfriends;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfriends.Model.BEFriend;
import com.example.myfriends.Model.Friends;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    public static String TAG = "Friend2";

    private static final int FRIEND_DETAIL = 123;

    ArrayList<BEFriend> friends;

    private FriendsAdapter adapter;
    private IDataAccess<BEFriend> friendDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Friends v2");
        friendDao = DataAccessFactory.getInstance(this);
        friends = friendDao.readAll();
        if(friends.isEmpty())
        {
            Friends frnds = new Friends();
            List<BEFriend> all = frnds.getAll();
            for(BEFriend fr: all){
                friendDao.create(fr);
            }
            friends = friendDao.readAll();
        }
        refreshListView();
    }

    private void refreshListView()
    {
        //Create new adapter
        adapter = new FriendsAdapter(this,friends);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView parent, View v, int position,
                                long id) {

        Intent x = new Intent(this, DetailActivity.class);
        BEFriend friend = friends.get(position);
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
                    friends.set(position,friend);
                    friendDao.update(friend);
                    refreshListView();
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }

    }

    private class FriendsAdapter extends ArrayAdapter<BEFriend>{

        private ArrayList<BEFriend> friends;

        public FriendsAdapter(@NonNull Context context, @NonNull ArrayList<BEFriend> friends) {
            super(context, 0, friends);
            this.friends = friends;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

            if(view == null)
            {
                //Obtain inflater
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.list_view_record,null);
            }

            BEFriend friend= friends.get(position);

            TextView txtScore = view.findViewById(R.id.txtFriendName);
            txtScore.setText(friend.getName());

            ImageView iw = view.findViewById(R.id.imgFriend);
            if(friend.getPhoto() != null)
                iw.setImageBitmap(friend.getPhoto());
            else
                iw.setImageResource(R.drawable.froggy);

            return view;
        }
    }

}

