package merlin.merlin;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import merlin.merlin.objects.object;

public class DetailActivity extends Activity {

    private object obj;
    private TextView detailTV, detailTVDesc;
    private ImageView detailIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailTV=(TextView) findViewById(R.id.tvDetail);
        detailTVDesc=(TextView) findViewById(R.id.tvDetailDesc);
        detailIV=(ImageView) findViewById(R.id.detailIV);
        this.obj=(object) getApplicationContext();

        //detailTV.setText(this.obj.getElement().getJsonObject().toString());
        try {
            JSONObject data= new JSONObject(this.obj.getElement().getJsonObject().getString("data"));
            String display_name= data.getString("display_name");
            String description= data.getString("public_description");
            detailTV.setText(display_name);
            detailTVDesc.setText(description);
            detailIV.setImageBitmap(this.obj.getElement().getImg());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
