package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.style.DrawableMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.ArrayList;
import java.util.List;

import com.hs.userportal.R;
import com.hs.userportal.VaccineDetails;

import ui.DashBoardActivity;

/**
 * Created by ayaz on 29/3/17.
 */

public class DashboardActivityAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<String> mList = new ArrayList<>();
    private List<Integer> mImageList = new ArrayList<>();

    public DashboardActivityAdapter(DashBoardActivity dashBoardActivity) {
        mActivity = dashBoardActivity;

        mList.add("Vital 1");
        mList.add("Vital 2");
        mList.add("Discounts");
        mList.add("Coach");
        mList.add("Alerts");

        mImageList.add(R.drawable.vital1_144);
        mImageList.add(R.drawable.vital2_144);
        mImageList.add(R.drawable.discounts_144);
        mImageList.add(R.drawable.coach_144);
        mImageList.add(R.drawable.alerts_144);
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView name;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.dashboard_grid_view, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.image_text_view);
            holder.image = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(mList.get(position));
        holder.image.setImageResource(mImageList.get(position));
        return convertView;
    }
}
