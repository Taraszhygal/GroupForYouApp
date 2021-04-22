package com.groupforyouapp.ui.sign_out;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.groupforyouapp.LoginActivity;
import com.groupforyouapp.MainActivity;
import com.groupforyouapp.R;

public class SignOutFragment extends Fragment {

    Button yes_button,no_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signout, container, false);
        yes_button=root.findViewById(R.id.yes_button);
        no_button=root.findViewById(R.id.no_button);

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), LoginActivity.class);
                i.putExtra("finish", true);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                startActivity( i );
                FirebaseAuth.getInstance().signOut();
            }
        });
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction = myFragmentManager
//                        .beginTransaction();
//                fragmentTransaction.replace(R.layout., homeFragment);
//                fragmentTransaction.commit();
//               //
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return root;
    }
}