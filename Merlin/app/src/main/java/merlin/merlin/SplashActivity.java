package merlin.merlin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    protected boolean active = true;
    protected int maxTime = 4000; // timeout 10s
    protected int ctrlTime = 1000; // control time, 100ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // close splash activity
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < maxTime)){
                        sleep(ctrlTime);
                        if (active){
                            waited += ctrlTime;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        };
        splashThread.start();

    }
}
