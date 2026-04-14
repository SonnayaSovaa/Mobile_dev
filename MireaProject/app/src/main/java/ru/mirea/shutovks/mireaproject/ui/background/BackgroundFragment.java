package ru.mirea.shutovks.mireaproject.ui.background;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import ru.mirea.shutovks.mireaproject.databinding.FragmentBackgrounBinding;

public class BackgroundFragment extends Fragment {
    private FragmentBackgrounBinding binding;
    private OneTimeWorkRequest workRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBackgrounBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        workRequest = new OneTimeWorkRequest.Builder(BackgroundWorker.class)
                .build();

        binding.buttonStartWork.setOnClickListener(v -> {
            WorkManager.getInstance(requireContext())
                    .enqueue(workRequest);
            binding.textWorkStatus.setText("Status: enqueued");
            Toast.makeText(requireContext(),
                    "Work enqueued", Toast.LENGTH_SHORT).show();
        });

        WorkManager.getInstance(requireContext())
                .getWorkInfoByIdLiveData(workRequest.getId())
                .observe(getViewLifecycleOwner(), info -> {
                    if (info == null) return;
                    WorkInfo.State state = info.getState();
                    binding.textWorkStatus.setText("Status: " + state.name());
                    if (state.isFinished()) {
                        Toast.makeText(requireContext(),
                                "Work finished", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
