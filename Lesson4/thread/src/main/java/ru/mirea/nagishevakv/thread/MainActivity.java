package ru.mirea.nagishevakv.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

import ru.mirea.nagishevakv.thread.databinding.ActivityMainBinding;

public	class	MainActivity extends AppCompatActivity	{
    private ActivityMainBinding binding;
    private	int	counter	= 0;

    @Override
    protected void onCreate(Bundle	savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        int numberThread = counter++;
                        Log.d("ThreadProject", String.format("---Запущен поток № %d студентом группы	№ %s номер по списку № %d ---", numberThread, "БСБО-09-23", 16));
                        long endTime = System.currentTimeMillis() + 2 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            Log.d("---ThreadProject", "Выполнен поток № ---" + numberThread);
                        }
                    }
                }).start();
            }
        });
    }
}


/*




        Thread mainThread = Thread.currentThread();
        binding.tv.setText("Имя текущего потока: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)	{
                long endTime = System.currentTimeMillis() + 5 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime - System.currentTimeMillis());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                mainThread.setName("МОЙ НОМЕР ГРУППЫ: 09, НОМЕР ПО СПИСКУ: 16, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Трасса60");
                binding.tv.append("\n Новое имя потока: " + mainThread.getName());
                Log.d(MainActivity.class.getSimpleName(),"Stack: " + Arrays.toString(mainThread.getStackTrace()));
            }
        });
    }



 */