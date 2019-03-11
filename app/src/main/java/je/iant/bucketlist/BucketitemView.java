package je.iant.bucketlist;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BucketitemView extends RecyclerView.ViewHolder {

    private int id;
    private TextView title;
    private TextView description;
    private CheckBox checkbox;

    private View view;

    private Executor executor = Executors.newSingleThreadExecutor();

    public BucketitemView(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.bucketitemTitle);
        description = itemView.findViewById(R.id.bucketitemDesc);
        checkbox = itemView.findViewById(R.id.checkBox);
        view = itemView;

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    description.setPaintFlags(description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    updateDatabase();
                } else {
                    title.setPaintFlags(title.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    description.setPaintFlags(description.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

                    updateDatabase();
                }
            }
        });
    }

    public void updateDatabase() {
        final Bucketitem item = new Bucketitem();
        item.id = id;
        item.name = title.getText().toString();
        item.description = description.getText().toString();
        item.checked = checkbox.isChecked();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                BucketitemDatabase.getInstance(view.getContext().getApplicationContext()).bucketitemDao().update(item);
            }
        });
    }

    public void updateFields(int id, String title, String description, boolean checked) {
        this.id = id;
        this.title.setText(title);
        this.description.setText(description);
        checkbox.setChecked(checked);

        Log.d("Yup", "I've been here " + title + " " + description);
    }
}
