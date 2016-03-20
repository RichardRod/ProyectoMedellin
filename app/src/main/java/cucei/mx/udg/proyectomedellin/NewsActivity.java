package cucei.mx.udg.proyectomedellin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);//

        final Bundle bundle = getIntent().getExtras();

        ImageView imageView = (ImageView) findViewById(R.id.imageNewActivity);
        TextView textView = (TextView) findViewById(R.id.txtNewActivity);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), bundle.getString("TEXT"),Toast.LENGTH_LONG).show();
            }
        });

        String url = bundle.getString("URL");

        if(url.length() == 0){
            imageView.setImageResource(R.mipmap.udg_uno);
        }
        else{
            Picasso.with(getApplicationContext()).load(url).placeholder(R.mipmap.news_icon).into(imageView);
        }
        textView.setText(bundle.getString("TEXT"));
    }
}
