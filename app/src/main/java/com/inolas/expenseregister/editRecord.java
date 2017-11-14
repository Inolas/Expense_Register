package com.inolas.expenseregister;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by inolas on 8/11/17.
 */

public class editRecord extends Activity implements View.OnClickListener {

    private EditText dateText;
    private Button updateBtn, deleteBtn, calendar;
    private EditText descText;
    private EditText amtText;

    int month, day, year;
    static final  int DIALOG_ID = 0;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.edit_record);

        dbManager = new DBManager(this);
        dbManager.open();

        dateText = (EditText) findViewById(R.id.datem_edittext);
        amtText = (EditText) findViewById(R.id.amountm_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);
        calendar = findViewById(R.id.calendarm);
        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String date = intent.getStringExtra("date");
        String amt = String.valueOf(intent.getIntExtra("amt",-1));
        String desc = intent.getStringExtra("desc");

        _id = Long.parseLong(id);

        dateText.setText(date);
        amtText.setText(amt);
        descText.setText(desc);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        showDialogOnClick();
    }
    public void showDialogOnClick(){

//        btn_save = findViewById(R.id.btn_save);
        calendar = findViewById(R.id.calendarm);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListner, year, month, day);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year_dp, int month_dp, int day_dp) {
            year_dp = year;
            month_dp = month+1;
            day_dp = day;
            dateText.setText(day_dp+"-"+month_dp+"-"+year_dp);
            Toast.makeText(editRecord.this, day_dp+"-"+month_dp+"-"+year_dp,Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                try {
                    String date = dateText.getText().toString();
                    int amt = Integer.parseInt(amtText.getText().toString());
                    String desc = descText.getText().toString();

                    dbManager.update(_id, date, amt, desc);
                    this.returnHome();
                }catch (NumberFormatException n){
                    Toast.makeText(editRecord.this, "Fields cannot be blank", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_delete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
