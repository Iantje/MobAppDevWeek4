package je.iant.bucketlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Bucketitem.class}, version = 1, exportSchema = false)
public abstract class BucketitemDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "bucketitem_database";
    public abstract BucketitemDao bucketitemDao();
    private static volatile BucketitemDatabase INSTANCE;

    public static BucketitemDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (BucketitemDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BucketitemDatabase.class, NAME_DATABASE).build();
                }
            }
        }

        return INSTANCE;
    }
}
