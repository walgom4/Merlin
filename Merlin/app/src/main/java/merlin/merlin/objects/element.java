package merlin.merlin.objects;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * Created by walgom on 13/10/2017.
 */

public class element {
    private JSONObject jsonObject;
    private Bitmap img;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
