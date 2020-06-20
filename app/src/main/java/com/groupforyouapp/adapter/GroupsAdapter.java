package com.groupforyouapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.groupforyouapp.InfoActivity;
import com.groupforyouapp.Models.Groups;
import com.groupforyouapp.R;

import java.util.List;

public class GroupsAdapter extends BaseAdapter {

    private List<Groups> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public GroupsAdapter(Context context, List<Groups> list) {
        this.list = list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Groups getGroup(int position) {
        return (Groups) getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.group_item, parent, false);
        }

        final Groups grp = getGroup(position);

        TextView grpName = view.findViewById(R.id.group_name);
        TextView grpCategory = view.findViewById(R.id.group_category);
        TextView grpDesc = view.findViewById(R.id.group_description);
        ImageView grpIcon = view.findViewById(R.id.group_image);

        grpName.setText(grp.getNameGroup());
        grpCategory.setText(grp.getCategory());
        grpDesc.setText(grp.getDescription().length() > 76 ? grp.getDescription().substring(0, 76) + "..." : grp.getDescription());
        if (grp.getImage()!=null) {
            grpIcon.setBackground(null);
            Picasso.with(context)
                    .load(grp.getImage())
                    .fit()
                    .centerInside()
                    .into(grpIcon);
        }else {
            grpIcon.setImageResource(R.drawable.camera_icon);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoActivity(grp.getNameGroup(),
                        grp.getAuthor(),
                        grp.getDescription(),
                        grp.getUserEXP().toString(),
                        grp.getCategory(),
                        grp.getNumber(),
                        grp.getImage());//!=null? grp.getImage():
            }
        });

        return view;
    }

    //open detail activity
    private void openInfoActivity(String...detail){
        Intent intent= new Intent(context, InfoActivity.class);
        intent.putExtra("Group_name",detail[0]);
        intent.putExtra("Author",detail[1]);
        intent.putExtra("Description",detail[2]);
        intent.putExtra("Exp",detail[3]);
        intent.putExtra("Category",detail[4]);
        intent.putExtra("Number",detail[5]);
        intent.putExtra("Icon",detail[6]);

        context.startActivity(intent);
    }
}

