package com.inolas.expenseregister;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by inolas on 8/11/17.
 */

public class addRecord extends Activity implements View.OnClickListener {

    private Button addTodoBtn, calendar;
    EditText dateEditText;
    private EditText descEditText;
    private EditText amountEditText;
    int month, day, year;

    static final  int DIALOG_ID = 0;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Record");
        setContentView(R.layout.add_expense);

        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        amountEditText = findViewById(R.id.amount_edittext);
        dateEditText = findViewById(R.id.date_edittext);
        descEditText = findViewById(R.id.description_edittext);
        addTodoBtn = findViewById(R.id.add_record);

        dateEditText.setText(day+"-"+(month+1)+"-"+year);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);

        addItemsOnSpinner();
        showDialogOnClick();
    }
    public void showDialogOnClick(){
        calendar = findViewById(R.id.calendar);
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
            year = year_dp;
            month = month_dp+1;
            day = day_dp;
            dateEditText.setText(day+"-"+month+"-"+year);
        }
    };

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        Spinner spinner1 = (Spinner) findViewById(R.id.spinnerPayMtd);
        List<String> list = new ArrayList<String>();
        list.add("Cash");
        list.add("Debit card");
        list.add("Credit card");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:
                try {
                    final String date = dateEditText.getText().toString();
                    final String desc = descEditText.getText().toString();
                    int des;
                    if(desc == null) des = Integer.parseInt(descEditText.getText().toString());
                    final int amt = Integer.parseInt(amountEditText.getText().toString());
                    dbManager.insert(date, amt, desc);
                    Intent main = new Intent(addRecord.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                }catch (NumberFormatException n){
                    Toast.makeText(addRecord.this, "Fields cannot be blank", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}