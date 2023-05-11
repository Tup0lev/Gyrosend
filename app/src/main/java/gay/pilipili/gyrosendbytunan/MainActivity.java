package gay.pilipili.gyrosendbytunan;

import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Thread.sleep;
import static java.security.KeyRep.Type.SECRET;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import gay.pilipili.gyrosendbytunan.databinding.ActivityMainBinding;


public class MainActivity extends Activity {

    TextView textX, textY, textZ;
    SensorManager sensorManager;
    Sensor sensor;

    EditText crapText;
    Button assTouch;
    PrintWriter damnWriter;
    NiceThread niceThread;
    private TextView mTextView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

       // crapText = findViewById(R.id.CrapText);
        assTouch = (Button) findViewById(R.id.ass);


        niceThread = new NiceThread();
        new Thread(niceThread).start();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        textZ = findViewById(R.id.textZ);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }

    public SensorEventListener gyroListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {
        }
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            textX.setText("X Acceleration: " +  x*57.2958 + " deg/s");
            textY.setText("Y Acceleration: " +  y*57.2958 + " deg/s");
            textZ.setText("Z Acceleration: " +  z*57.2958 + " deg/s " + "absolute valiue of Sum: " +  (abs((int)(x*57.2958+y*57.2958+z*57.2958)))  );

//            EditText IP = (EditText)findViewById(R.id.IP);
//            EditText PORT = (EditText)findViewById(R.id.port);
//            String ip = IP.getText().toString();
//            int port = Integer.parseInt(PORT.getText().toString());

            //String secret = String.valueOf(x*57.2958+y*57.2958+z*57.2958);
            if (X.set == true)
            {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int secret = abs((int) ((int) x*57.2958+y*57.2958+z*57.2958));
            niceThread.sendSecret(secret, X.pubip, X.pubport);}
        }
    };




private class NiceThread implements Runnable

    {
        //private volatile String superSecret;
        private volatile int superSecret;
        private volatile String sockIP;
        private volatile int sockPORT;
        Socket assSocket;
        DataOutputStream squirtOfDiarrhea;
        @Override
        public void run() {
            try {
                assSocket = new Socket(sockIP, sockPORT);
                squirtOfDiarrhea = new DataOutputStream(assSocket.getOutputStream());
                System.out.println(superSecret);
                squirtOfDiarrhea.writeInt(superSecret);
//                squirtOfDiarrhea.flush();
//                squirtOfDiarrhea.close();
              //  System.out.println("yay");

            } catch (IOException ignore) {

            }
        }
        public void sendSecret(int msg, String IP, int port){
            this.superSecret = msg;
            this.sockIP = IP;
            this.sockPORT = port;
            run();
        }




    }

    public void ClickSend(View v){
        System.out.println("asdf");

        EditText IP = (EditText)findViewById(R.id.IP);
        EditText PORT = (EditText)findViewById(R.id.port);
        String ip = IP.getText().toString();
        int port = Integer.parseInt(PORT.getText().toString());
        X.pubip = ip;
        X.pubport = port;
        X.set = true;

    }
}
