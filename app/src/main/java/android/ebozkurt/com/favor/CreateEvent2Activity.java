package android.ebozkurt.com.favor;

import android.ebozkurt.com.favor.helpers.ActivityHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class CreateEvent2Activity extends AppCompatActivity {

    EditText description;
    TextView description_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
        ActivityHelper.initialize(this);

        description = (EditText) findViewById(R.id.activity_create_event2_description_edittext);
        description_counter = (TextView) findViewById(R.id.activity_create_event2_description_counter_textview);

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //todo add counter value changes when description text is updated
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
