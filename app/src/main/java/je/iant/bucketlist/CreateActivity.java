package je.iant.bucketlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CreateActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        nameText = findViewById(R.id.itemName);
        descText = findViewById(R.id.itemDesc);

        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.EXTRA_NAME, nameText.getText());
                resultIntent.putExtra(MainActivity.EXTRA_DESC, descText.getText());
                Log.d("Nuhu", "Nullsssss" + nameText.getText() + " " + descText.getText());
                setResult(Activity.RESULT_OK, resultIntent);

                finish();
            }
        });
    }
}
