package com.groupforyouapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.groupforyouapp.Models.Groups;
import com.groupforyouapp.R;
import com.groupforyouapp.adapter.GroupsAdapter;
import com.groupforyouapp.db.FirebasedbHelper;
import com.groupforyouapp.db.MyCallback;

import java.util.List;

public class SearchFragment extends Fragment {
    private EditText searchField;
    private ImageButton searchButton;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchField = root.findViewById(R.id.search_view );
        searchButton = root.findViewById(R.id.search_Button );
        listView = root.findViewById(R.id.list_View);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String searchText = searchField.getText().toString();
             groupSearch(searchText);

            }
        });


        return root;
    }

    private void groupSearch(String searchText) {
        new FirebasedbHelper().searchGroups(searchText, new MyCallback() {
            @Override
            public void onSuccess(List<?> list) {
                GroupsAdapter groupsAdapter = new GroupsAdapter(getContext(), (List<Groups>) list);
                listView.setAdapter(groupsAdapter);
            }
        });
    }
}