package cucei.mx.udg.proyectomedellin;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by MISAEL on 05/02/2016.
 */
public class NewsArrayAdapter extends ArrayAdapter<News> {
    public NewsArrayAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item,parent,false);
        }

        News item = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView textView = (TextView) convertView.findViewById(R.id.text_contenido);
        textView.setTextColor(Color.BLACK);

        if(item.getUrlImage().length() != 0) {
            Picasso.with(getContext()).load(item.getUrlImage()).placeholder(R.mipmap.news_icon).into(imageView);
        }
        else{
            imageView.setImageResource(R.mipmap.udg_uno);
        }
        //Toast.makeText(getContext(), item.getUrlImage(), Toast.LENGTH_SHORT).show();
        textView.setText(item.toString());

        return  convertView;
    }
}
