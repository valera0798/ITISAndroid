package ru.itis.android.lesson_18_09_17.activities.contactlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.lesson_18_09_17.R;
import ru.itis.android.lesson_18_09_17.activities.contactpager.ContactPagerActivity;
import ru.itis.android.lesson_18_09_17.models.Contact;
import ru.itis.android.lesson_18_09_17.models.PhoneNumber;

// MVP - Model View Presenter. Производный от MVC. Для пользовательского интерфейса.

public class ContactsListActivity extends AppCompatActivity implements OnItemClickListener{
    public static final int TEST_DATA_NUMBER = 10;


    private List<Contact> contacts;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter<ContactViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        contacts = initTestData();
        layoutManager = new LinearLayoutManager(this);
        adapter = new ContactListAdapter(contacts, this);

        recyclerView = ((RecyclerView) findViewById(R.id.recycler_view));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<Contact> initTestData() {
        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < TEST_DATA_NUMBER; i++) {
            contacts.add(new Contact(i, "Valera №" + i, R.drawable.contact_photo,
                    new ArrayList<PhoneNumber>() {
                        {
                            add(new PhoneNumber("+79876543210", "Description №0"));
                            add(new PhoneNumber("+79876543211", "Description №1"));
                            add(new PhoneNumber("+79876543212", "Description №2"));
                        }
                    }));
        }
        return contacts;
    }

    @Override
    public void onClick(int position) {
        startActivity(ContactPagerActivity.makeIntent(this, position, contacts));
    }
}
