package br.com.igortice.rangeevolution;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSwipeAdapter extends PagerAdapter {
    ArrayList<Integer> images_res;

    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(ArrayList<Integer> images_res, Context context) {
        this.images_res = images_res;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images_res.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.imageView);
        TextView textView = (TextView) item_view.findViewById(R.id.imageTexto);
        imageView.setImageResource(images_res.get(position));
        textView.setText("Imagem " + position);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
