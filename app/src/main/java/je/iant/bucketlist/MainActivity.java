package je.iant.bucketlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private RecyclerView bucketlistRecycler;
    private BucketitemAdapter bucketitemAdapter;
    private List<Bucketitem> bucketitems = new ArrayList<>();

    private BucketitemDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();

    private GestureDetector gestureDetector;

    public final static int REQUEST_CODE = 1337;
    public final static String EXTRA_NAME = "name";
    public final static String EXTRA_DESC = "desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = BucketitemDatabase.getInstance(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        bucketitemAdapter = new BucketitemAdapter(bucketitems);
        bucketlistRecycler = findViewById(R.id.bucketitemRecycler);
        bucketlistRecycler.setAdapter(bucketitemAdapter);
        bucketlistRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        bucketlistRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        getAllItems();

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

                Log.d("Urgh", "Long press");

                View child = bucketlistRecycler.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    Log.d("Urgh", "Long press child not null");
                    int adapterPosition = bucketlistRecycler.getChildAdapterPosition(child);
                    deleteItem(bucketitems.get(adapterPosition));
                }
            }
        });

        bucketlistRecycler.addOnItemTouchListener(this);
    }

    private void updateUI() {
        bucketitemAdapter.notifyDataSetChanged();
    }

    private void updateUI(List<Bucketitem> shopitems) {
        this.bucketitems.addAll(shopitems);
        updateUI();
    }

    private void getAllItems() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final List<Bucketitem> bucketitems = db.bucketitemDao().getAllItems();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(bucketitems);
                    }
                });
            }
        });
    }

    private void insertItem(final Bucketitem bucketitem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketitemDao().insert(bucketitem);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bucketitems.add(bucketitem);
                        updateUI();
                    }
                });
            }
        });
    }

    private void deleteItem(final Bucketitem bucketitem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketitemDao().delete(bucketitem);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bucketitems.remove(bucketitem);
                        updateUI();
                    }
                });
            }
        });
    }

    private void deleteAllItems(final List<Bucketitem> localBucketItems) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketitemDao().delete(localBucketItems);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bucketitems.clear();
                        bucketitemAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_delete_item) {
            deleteAllItems(bucketitems);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Bucketitem bucketitem = new Bucketitem();
                bucketitem.name = data.getStringExtra(EXTRA_NAME);
                bucketitem.description = data.getStringExtra(EXTRA_DESC);
                bucketitem.checked = false;

                insertItem(bucketitem);
            }
        }
    }
}
