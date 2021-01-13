package in.hawknetwork.services1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    //Project contains "services" & "telephony" concepts

    Button btn,btn1;

    EditText e1,e2,e3;
    Button buttonSMS,buttonREADSMS,intent;

    List<String> phoneNumbers = new ArrayList<String>();
    List<String> messages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 =  findViewById(R.id.et1);
        e2 =  findViewById(R.id.et2);
        e3 =  findViewById(R.id.et3);


        btn = findViewById(R.id.play);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this,MyService.class);
                startService(i);
            }
        });

        btn1 = findViewById(R.id.stop);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this,MyService.class);
                stopService(i);
            }
        });


        //permission SEND SMS
       if(ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED)
       {
           //open dialogue to ask permission from user
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
       }


        buttonSMS = findViewById(R.id.smsBtn);
        buttonSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = e1.getText().toString();
                String msg = e2.getText().toString();

                //give a reference of OS SMS service
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone,null,msg,null,null);

                Toast.makeText(MainActivity.this,"sms sent",Toast.LENGTH_SHORT).show();
            }
        });


        //permission READ SMS
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_SMS)!=PackageManager.PERMISSION_GRANTED)
        {
            //open dialogue to ask permission from user
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},1);
        }

        buttonREADSMS = findViewById(R.id.readBtn);
        buttonREADSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String readSMS = e3.getText().toString();

                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null,null);

                if(cursor.moveToFirst()) //point first row to find data & return true if data is found.
                {
                  do
                      {
                        String msgContent="";
                        String msgContent1="";
                        String columnName;


                       for (int a=0;a<cursor.getColumnCount();a++)  //count the no of columns
                        {
                           columnName = cursor.getColumnName(a); //give the name of columns at a position(a)

                           if(columnName.equals("address"))
                           {
                               msgContent =  "Phone no -> " +cursor.getString(a); //this index value will be stored in msg
                           }

                           if(columnName.equals("body"))
                           {
                                msgContent1 = "Message ->" +cursor.getString(a);
                           }

                        }
                          //listview assignment
                         // phoneNumbers = new String[]{msgContent};
                         // messages = new String[]{msgContent1};
                          phoneNumbers.add(msgContent);
                          messages.add(msgContent1);
                          e3.append("\n" +msgContent +"\n" +msgContent1);
                      }

                  while (cursor.moveToNext()); //will run until it move to next
                }
            }
        });

        intent =findViewById(R.id.intent);
        intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Custom_List.class);
              //  i.putExtra("phone_key",phoneNumbers);
             //   i.putExtra("message_key",messages);
                i.putStringArrayListExtra("phone_key", (ArrayList<String>) phoneNumbers);
                i.putStringArrayListExtra("message_key",(ArrayList<String>)messages);
                startActivity(i);
            }
        });


    }
}
