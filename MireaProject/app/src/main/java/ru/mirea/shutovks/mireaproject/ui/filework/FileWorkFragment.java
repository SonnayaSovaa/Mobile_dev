package ru.mirea.shutovks.mireaproject.ui.filework;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.mirea.shutovks.mireaproject.R;

public class FileWorkFragment extends Fragment {
    private TextView resultText;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_work, container, false);
        resultText = view.findViewById(R.id.resultText);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> showInputDialog());
        return view;
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Обработка файла (Base64)");
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_file_action, null);
        EditText input = dialogView.findViewById(R.id.inputText);
        builder.setView(dialogView);
        builder.setPositiveButton("Кодировать", (dialog, which) -> {
            String text = input.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(getContext(), "Введите текст", Toast.LENGTH_SHORT).show();
                return;
            }
            String encoded = Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
            resultText.setText("Base64: " + encoded);
        });
        builder.setNegativeButton("Декодировать", (dialog, which) -> {
            String text = input.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(getContext(), "Введите Base64", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                String decoded = new String(Base64.decode(text, Base64.DEFAULT));
                resultText.setText("Декодировано: " + decoded);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Ошибка декодирования", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("Отмена", null);
        builder.show();
    }
} 