package merlin.merlin.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Blob;

/**
 * Created by walgom on 17/10/2017.
 */
@Entity
public class persistence_object {

    @PrimaryKey (autoGenerate = true)
    public int id;
    public String json;
    public byte[] img;

    public persistence_object( String json, byte[] img) {
        this.json = json;
        this.img = img;
    }
}
