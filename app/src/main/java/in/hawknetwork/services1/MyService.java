package in.hawknetwork.services1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service
{
    MediaPlayer mediaPlayer;

    //binder binds our service with the main activity
    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
       // throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate()  //store the crated reference of mediaPlayer
    {
        mediaPlayer = MediaPlayer.create(this,R.raw.dollar);
        Toast.makeText(this,"Service Created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        mediaPlayer.start();
        Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy()
    {
        mediaPlayer.stop();
        Toast.makeText(this,"Service Destroy",Toast.LENGTH_SHORT).show();
    }
}
