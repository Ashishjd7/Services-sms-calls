package in.hawknetwork.services1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class Custom_List extends AppCompatActivity {

    ListView listView;

    List<String>  phoneNumbers;
    List<String>  messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom__list);

        listView = findViewById(R.id.listsms);

        //Intent data
        Bundle data = getIntent().getExtras();
        assert data != null;
        //phoneNumbers = data.getStringArray("phone_key");
        // messages = data.getStringArray("message_key");

        phoneNumbers = data.getStringArrayList("phone_key");
        messages = data.getStringArrayList("message_key");


         DataList dd = new DataList(phoneNumbers,messages);

         listView.setAdapter(dd);
    }
}


class DataList extends BaseAdapter
{
    List<String> phoneNumbers;
    List<String> messages;

    public DataList(List<String> phoneNumbers, List<String> messages )
    {
        this.phoneNumbers = phoneNumbers;
        this.messages = messages;
    }

    @Override
    public int getCount()
    {
        return phoneNumbers.size();
    }

    @Override
    public Object getItem(int i) {
        return phoneNumbers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_menu,null);
        EditText number = v.findViewById(R.id.numberShow);
        EditText sms = v.findViewById(R.id.smsShow);

        number.setText(phoneNumbers.get(i));
        sms.setText(messages.get(i));

        return v;
    }
}
