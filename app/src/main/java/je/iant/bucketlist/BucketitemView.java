package je.iant.bucketlist;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class BucketitemView extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private CheckBox checkbox;

    public BucketitemView(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.bucketitemTitle);
        description = itemView.findViewById(R.id.bucketitemDesc);
        checkbox = itemView.findViewById(R.id.checkBox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    description.setPaintFlags(description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    title.setPaintFlags(title.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    description.setPaintFlags(description.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });
    }

    public void updateFields(String title, String description) {
        this.title.setText(title);
        this.description.setText(description);

        Log.d("Yup", "I've been here " + title + " " + description);
    }
}
