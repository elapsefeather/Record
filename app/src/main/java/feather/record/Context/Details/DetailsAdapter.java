package feather.record.Context.Details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import feather.record.Data.EnterInfo;
import feather.record.R;

/**
 * Created by feather on 2017/6/26.
 */

public class DetailsAdapter extends BaseExpandableListAdapter {

    ArrayList<EnterInfo> list;
    Context context;
    LayoutInflater inflater;

    public DetailsAdapter(Context context, ArrayList<EnterInfo> list) {

        this.context = context;
        this.list = list;
        try {
            inflater = LayoutInflater.from(context);
        } catch (Exception e) {
        }
    }

    public void setdata(ArrayList<EnterInfo> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return i;
    }

    @Override
    public Object getChild(int i, int i1) {
        return i1;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup parent) {
        groupView group;

        if (view == null) {
            view = inflater.inflate(R.layout.item_details_group, parent, false);
            group = new groupView(view);
            view.setTag(group);
        } else {
            group = (groupView) view.getTag();
        }

        group.date.setText(getDate(i));
        group.item.setText(getItem(i));
        group.money.setText(getMoney(i));
        switch (getItem(i)) {
            case "earn":
                group.money.setTextColor(context.getResources().getColor(R.color.details_item_earn));
                group.item.setTextColor(context.getResources().getColor(R.color.details_item_earn));
                break;
            case "cost":
                group.money.setTextColor(context.getResources().getColor(R.color.details_item_cost));
                group.item.setTextColor(context.getResources().getColor(R.color.details_item_cost));
                break;
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup parent) {
        childView child;

        if (view == null) {
            view = inflater.inflate(R.layout.item_details_child, parent, false);
            child = new childView(view);
            view.setTag(child);
        } else {
            child = (childView) view.getTag();
        }

        child.name.setText(getName(i));
        child.option.setText(getOption(i));
        child.note.setText(getNote(i));

        return view;
    }

    private class groupView {
        TextView date, money,item;

        public groupView(View view) {
            date = (TextView) view.findViewById(R.id.details_item_group_date);
            item = (TextView) view.findViewById(R.id.details_item_group_item);
            money = (TextView) view.findViewById(R.id.details_item_group_money);
        }
    }

    private class childView {
        TextView name, option, note;

        public childView(View view) {

            name = (TextView) view.findViewById(R.id.details_item_child_name);
            option = (TextView) view.findViewById(R.id.details_item_child_option);
            note = (TextView) view.findViewById(R.id.details_item_child_note);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public String getItem(int i) {
        return list.get(i).getItem();
    }

    public String getDate(int i) {
        return list.get(i).getDate();
    }

    public String getName(int i) {
        return list.get(i).getName();
    }

    public String getOption(int i) {
        return list.get(i).getOption();
    }

    public String getMoney(int i) {
        return list.get(i).getMoney();
    }

    public String getNote(int i) {
        return list.get(i).getNote();
    }
}
