package merlin.merlin.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import merlin.merlin.dao.dao_object;
import merlin.merlin.objects.persistence_object;

/**
 * Created by walgom on 17/10/2017.
 */
@Database(entities = {persistence_object.class}, version = 17, exportSchema = false)
public abstract class Merlin_DB extends RoomDatabase{

    private static Merlin_DB INSTANCE;

    public abstract dao_object daoObject();

    public static Merlin_DB getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, Merlin_DB.class, "merlindatabase")
//Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            .allowMainThreadQueries()
                            // recreate the database if necessary
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
