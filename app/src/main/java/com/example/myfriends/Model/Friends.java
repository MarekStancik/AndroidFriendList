package com.example.myfriends.Model;
import java.util.ArrayList;

public class Friends {

    ArrayList<BEFriend> m_friends;

    public Friends()
    {
        m_friends = new ArrayList<BEFriend>();
        m_friends.add(new BEFriend("Alex", "123456", true));
        m_friends.add(new BEFriend("Anders", "234567"));
        m_friends.add(new BEFriend("Andreas", "126256", true));
        m_friends.add(new BEFriend("Asvør", "234567"));
        m_friends.add(new BEFriend("Casper", "123456", true));
        m_friends.add(new BEFriend("Christian", "994567"));
        m_friends.add(new BEFriend("Daniel", "127426"));
        m_friends.add(new BEFriend("David", "204587", true));
        m_friends.add(new BEFriend("Grzegorz", "123456"));
        m_friends.add(new BEFriend("Henrik", "234567", true));
        m_friends.add(new BEFriend("Huseen", "123456"));
        m_friends.add(new BEFriend("Jakub", "234567"));
        m_friends.add(new BEFriend("Jan", "123456"));
        m_friends.add(new BEFriend("Jørgen", "234567", true));
        m_friends.add(new BEFriend("Kasper", "123456"));
        m_friends.add(new BEFriend("Kristian", "234567"));
        m_friends.add(new BEFriend("Mads", "123456", true));
        m_friends.add(new BEFriend("Mark", "234567", true));
        m_friends.add(new BEFriend("Marek", "123456"));
        m_friends.add(new BEFriend("Martin", "234567", true));
        m_friends.add(new BEFriend("Mate", "123456"));
        m_friends.add(new BEFriend("Mathias", "234567", true));
        m_friends.add(new BEFriend("Nedas", "234567"));
        m_friends.add(new BEFriend("Nijas", "123456"));
        m_friends.add(new BEFriend("Niklas", "234567", true));
        m_friends.add(new BEFriend("Philip", "123456"));
        m_friends.add(new BEFriend("Simon", "234567"));
        m_friends.add(new BEFriend("Szymon", "234567", true));
        m_friends.add(new BEFriend("Theis", "123456"));
        m_friends.add(new BEFriend("Thorbjørn", "234567"));
    }

    public ArrayList<BEFriend> getAll()
    { return m_friends; }

    public String[] getNames()
    {
        String[] res = new String[m_friends.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = m_friends.get(i).getName();
        }
        return res;
    }

    public void update(BEFriend f, int position) {
        m_friends.set(position, f);
    }

}
