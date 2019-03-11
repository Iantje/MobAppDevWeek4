package je.iant.bucketlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BucketitemAdapter extends RecyclerView.Adapter<BucketitemView> {
    private List<Bucketitem> bucketitems;

    public BucketitemAdapter(List<Bucketitem> bucketitems) {
        this.bucketitems = bucketitems;
    }

    @NonNull
    @Override
    public BucketitemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bucket_item, null);

        return new BucketitemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BucketitemView bucketitemView, int i) {
        Bucketitem bucketitem = bucketitems.get(i);
        bucketitemView.updateFields(bucketitem.name, bucketitem.description);
    }

    @Override
    public int getItemCount() {
        return bucketitems.size();
    }
}
