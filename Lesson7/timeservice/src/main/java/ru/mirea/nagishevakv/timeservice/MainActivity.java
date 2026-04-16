package ru.mirea.nagishevakv.timeservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.mirea.nagishevakv.timeservice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String host = "time.nist.gov"; // или time-a.nist.gov
    private final int port = 13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());
                executor.execute(() -> {
                    String result = "";
                    try {
                        Socket socket = new Socket(host, port);
                        BufferedReader reader = SocketUtils.getReader(socket);
                        reader.readLine();
                        result = reader.readLine();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String finalResult = result;
                    var arr = result.split(" ");
                    handler.post(() -> {
                        // UI work (onPostExecute equivalent)
                        binding.textView.setText(String.format("Date: %s \nTime: %s", arr[1], arr[2]));
                    });
                });
            }
        });
    }

}