package je.iant.bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bucketitem_table")
public class Bucketitem {

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "checked")
    public boolean checked = false;
}
