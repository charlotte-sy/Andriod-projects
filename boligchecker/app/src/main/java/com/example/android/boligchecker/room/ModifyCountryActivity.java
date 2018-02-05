package com.example.android.boligchecker.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.Activity;
import android.view.View.OnClickListener;


import com.example.android.boligchecker.R;

/**
 * Created by Seoyeon on 04/02/2018.
 */

public class ModifyCountryActivity extends Activity implements OnClickListener  {

    private Button updateBtn, deleteBtn;
    private EditText descText;

    private long _id;

    private CheckListDataManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Check List Item");

        setContentView(R.layout.activity_modify_record);

        dbManager = new CheckListDataManager(this);
        dbManager.open();

        descText = (EditText) findViewById(R.id.description_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String desc = intent.getStringExtra("desc");

        _id = Long.parseLong(id);


        descText.setText(desc);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:

                String desc = descText.getText().toString();

                dbManager.update(_id, desc);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), CheckList.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
