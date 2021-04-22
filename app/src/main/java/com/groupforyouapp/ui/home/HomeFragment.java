package com.groupforyouapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.groupforyouapp.Models.Groups;
import com.groupforyouapp.R;
import com.groupforyouapp.adapter.GroupsAdapter;
import com.groupforyouapp.db.FirebasedbHelper;
import com.groupforyouapp.db.MyCallback;

import java.util.List;

public class HomeFragment extends Fragment {

    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) throws NullPointerException{

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listView = root.findViewById(R.id.list_View);
        new FirebasedbHelper().getGroups(new MyCallback() {
            @Override
            public void onSuccess(List<?> list) {
                GroupsAdapter groupsAdapter = new GroupsAdapter(getContext(), (List<Groups>) list);
                listView.setAdapter(groupsAdapter);
            }
        });

//        Query query = FirebaseDatabase.getInstance().getReference().child("Groups");
//        FirebaseListOptions<Groups> options = new FirebaseListOptions.Builder<Groups>()
//                .setLayout(R.layout.group_item)
//                .setLifecycleOwner(HomeFragment.this)
//                .setQuery(query, Groups.class)
//                .build();
//        try {
//            adapter = new FirebaseListAdapter(options) {
//                @Override
//                protected void populateView(View v, Object model, int position) {
//                    TextView grpName = (TextView) v.findViewById(R.id.group_name);
//                    TextView grpCategory = (TextView) v.findViewById(R.id.group_category);
//                    TextView grpDesc = (TextView) v.findViewById(R.id.group_description);
//
//                    Groups grp = (Groups) model;
//                    grpName.setText(grp.getNameGroup().toString());
//                    grpCategory.setText(grp.getCategory().toString());
//                    grpDesc.setText(grp.getDescription().length() > 76 ? grp.getDescription().substring(0, 76) + "..." : grp.getDescription());
//                }
//            };
//        }catch (Exception e){
//
//        }
//        listView.setAdapter(adapter);
        return root;
    }
}