package je.iant.bucketlist;

import android.app.usage.NetworkStats;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BucketitemDao {

    @Insert
    void insert(Bucketitem bucketitem);

    @Update
    void update(Bucketitem bucketitem);

    @Delete
    void delete(Bucketitem bucketitem);

    @Delete
    void delete(List<Bucketitem> bucketitems);

    @Query("SELECT * FROM bucketitem_table")
    List<Bucketitem> getAllItems();
}
