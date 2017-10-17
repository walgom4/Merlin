package merlin.merlin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import merlin.merlin.R;
import merlin.merlin.objects.element;

/**
 * Created by walgom on 16/10/2017.
 */

public class ListAdapter extends ArrayAdapter<element> {
    private final Context context;
    private List<element> elementList;


    public ListAdapter(Context context, List<element> elementList){
        super(context, R.layout.list_item, elementList);
        this.context= context;
        this.elementList= elementList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        TextView textViewCat =(TextView) rowView.findViewById(R.id.category);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        try {
            JSONObject data= new JSONObject(elementList.get(position).getJsonObject().getString("data"));
            String display_name= data.getString("display_name");
            String category = data.getString("audience_target");
            textView.setText(display_name);
            textViewCat.setText(category);
        } catch (JSONException e) {
            e.printStackTrace();
            textView.setText("Name not found");
            textViewCat.setText("Category not found");
        }
        if (elementList.get(position).getImg()!=null) {
            imageView.setImageBitmap(elementList.get(position).getImg());
        }

        return rowView;
    }
}
