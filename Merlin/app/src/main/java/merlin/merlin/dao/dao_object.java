package merlin.merlin.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import merlin.merlin.objects.persistence_object;

/**
 * Created by walgom on 17/10/2017.
 */
@Dao
public interface dao_object {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addObject(persistence_object persistenceObject);

    @Query("select * from persistence_object")
    public List<persistence_object> getAllObjects();

    @Query("select * from persistence_object where id = :userId")
    public List<persistence_object> getObject(long userId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateObject(persistence_object persistenceObject);

    @Query("delete from persistence_object")
    void removeAllObjects();
}
