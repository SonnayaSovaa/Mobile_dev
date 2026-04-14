package ru.mirea.shutovks.mireaproject.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.shutovks.mireaproject.R;

public class ProfileFragment extends Fragment {
    private EditText editName, editEmail, editAge;
    private Button btnSave;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        editAge = view.findViewById(R.id.editAge);
        btnSave = view.findViewById(R.id.btnSave);
        prefs = requireContext().getSharedPreferences("profile_prefs", Context.MODE_PRIVATE);
        loadProfile();
        btnSave.setOnClickListener(v -> saveProfile());
        return view;
    }

    private void loadProfile() {
        editName.setText(prefs.getString("name", ""));
        editEmail.setText(prefs.getString("email", ""));
        editAge.setText(prefs.getString("age", ""));
    }

    private void saveProfile() {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String age = editAge.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(age)) {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }
        prefs.edit().putString("name", name).putString("email", email).putString("age", age).apply();
        Toast.makeText(getContext(), "Профиль сохранён", Toast.LENGTH_SHORT).show();
    }
} 