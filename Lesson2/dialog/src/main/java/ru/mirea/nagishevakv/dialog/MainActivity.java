package ru.mirea.nagishevakv.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickShowDialog (View view) {
        AlertDialogFrament dialogFragment = new AlertDialogFrament();
        dialogFragment.show(getSupportFragmentManager(), "meow");
    }

    public void onOkClicked(String variantText) {
        Toast.makeText(getApplicationContext(), String.format("Вы выбрали кнопку \"%s\"!", variantText),
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked(String variantText) {
        Toast.makeText(getApplicationContext(), String.format("Вы выбрали кнопку \"%s\"!", variantText),
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked(String variantText) {
        Toast.makeText(getApplicationContext(), String.format("Вы выбрали кнопку \"%s\"!", variantText),
                Toast.LENGTH_LONG).show();
    }
}