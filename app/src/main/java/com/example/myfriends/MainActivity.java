package com.example.myfriends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfriends.Model.BEFriend;
import com.example.myfriends.Model.Friends;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "Friend2";

    private static final int FRIEND_DETAIL = 123;
    private static final int FRIEND_DETAIL_ADD = 124;

    ArrayList<BEFriend> friends;

    private ListView lvFriends;
    private IDataAccess<BEFriend> friendDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Friends v2");

        //Load friends from db
        friendDao = DataAccessFactory.getInstance(this);
        friends = friendDao.readAll();

        //If db is empty, seed database
        if(friends.isEmpty())
        {
            Friends frnds = new Friends();
            List<BEFriend> all = frnds.getAll();
            for(BEFriend fr: all){
                friendDao.create(fr);
            }
            friends = friendDao.readAll();
        }

        //Set up list view
        lvFriends = findViewById(R.id.lvMain);
        lvFriends.setOnItemClickListener((parent, view, position, id) -> {
            Intent x = new Intent(this, DetailActivity.class);
            BEFriend friend = friends.get(position);
            x.putExtra("position",position);
            x.putExtra("friend",friend);
            startActivityForResult(x,FRIEND_DETAIL);
        });
        refreshListView();
    }

    private void refreshListView()
    {
        //Create new adapter
        FriendsAdapter adapter = new FriendsAdapter(this,friends);
        lvFriends.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case FRIEND_DETAIL:
            {
                if(resultCode == RESULT_OK)
                {
                    Bundle bundle = data.getExtras();
                    int position = bundle.getInt("position");
                    BEFriend friend = (BEFriend) data.getSerializableExtra("friend");
                    friends.set(position,friend);
                    friendDao.update(friend);
                    refreshListView();
                    break;
                }
            }
            case FRIEND_DETAIL_ADD:
            {
                if(resultCode == RESULT_OK)
                {
                    BEFriend friend = (BEFriend) data.getSerializableExtra("friend");
                    friend = friendDao.create(friend);
                    friends.add(friend);
                    refreshListView();
                    break;
                }
            }
            default:
                break;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itAddFriend:
                addNewFriend();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void addNewFriend(){
        Intent x = new Intent(this, DetailActivity.class);
        startActivityForResult(x,FRIEND_DETAIL_ADD);
    }

}

