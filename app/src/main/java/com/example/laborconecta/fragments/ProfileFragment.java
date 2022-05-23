package com.example.laborconecta.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.laborconecta.LoginActivity;
import com.example.laborconecta.MainActivity;
import com.example.laborconecta.R;
import com.example.laborconecta.RegisterActivity;
import com.example.laborconecta.dbhelpers.SessionHelper;
import com.example.laborconecta.repositories.UserRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private final UserRepository userRepository;

    public ProfileFragment() {
        userRepository = UserRepository.getInstance(getContext());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView name = view.findViewById(R.id.profile_name);
        TextView email = view.findViewById(R.id.profile_email);

        if (userRepository.getUser() != null) {
            name.setText(userRepository.getUser().name);
            email.setText(userRepository.getUser().email);
        }

        Button logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            if (getActivity() == null || userRepository.getUser() == null) {
                return;
            }

            SessionHelper session = new SessionHelper(getContext());
            session.invalidate(userRepository.getUser().id);
            userRepository.setUser(null);
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}