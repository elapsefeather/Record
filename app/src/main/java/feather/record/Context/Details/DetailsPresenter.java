package feather.record.Context.Details;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

/**
 * Created by feather on 2017/6/26.
 */

public class DetailsPresenter {

    public DetailsActivity detilsActivity;
    public DetilsModel detilsModel;
    public DetailsAdapter detilsAdapter;

    public ArrayAdapter<String> yearAdapter;
    public ArrayAdapter<String> monthAdapter;

    public DetailsPresenter(DetailsActivity activity) {
        this.detilsActivity = activity;
        this.detilsModel = new DetilsModel(this);
        detilsAdapter = new DetailsAdapter(detilsActivity, detilsModel.list);

        yearAdapter = new ArrayAdapter<String>(detilsActivity, android.R.layout.simple_spinner_item, detilsModel.year);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        monthAdapter = new ArrayAdapter<String>(detilsActivity, android.R.layout.simple_spinner_item, detilsModel.month);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    }

    public void getYear() {
        Log.i("notify", "first");
        detilsModel.getYear();
    }

    public void notifyfirst() {
        Log.i("notify", "notifyfirst");
        yearAdapter.notifyDataSetChanged();
        monthAdapter.notifyDataSetChanged();
    }

    public void getData() {
        Log.i("notify", "getData");
        detilsModel.getData();
    }

    public void notifyData() {
        Log.i("notify", "notifyData");
        if(detilsModel.list.size()==0){
            detilsActivity.exlistview.setVisibility(View.INVISIBLE);
            detilsActivity.nodata.setVisibility(View.VISIBLE);

        }else
        {
            detilsActivity.exlistview.setVisibility(View.VISIBLE);
            detilsActivity.nodata.setVisibility(View.INVISIBLE);

            detilsAdapter.setdata(detilsModel.list);
            detilsAdapter.notifyDataSetChanged();
        }
    }
}
