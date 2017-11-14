package com.inolas.expenseregister;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.DATE, DatabaseHelper.AMT, DatabaseHelper.DESC };

    final int[] to = new int[] { R.id.id, R.id.date, R.id.view_amount, R.id.desc };

    TextView montlyexpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_fragment);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        Calendar cal = Calendar.getInstance();
        int mthExpense = dbManager.amtsum(cal.get(Calendar.MONTH));
        montlyexpense = (TextView) findViewById(R.id.monthlyexpense_textview);
//        Toast.makeText(MainActivity.this, mthExpense)
        montlyexpense.setText(mthExpense+"");

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView dateTextView = (TextView) view.findViewById(R.id.date);
                TextView amountTextView = (TextView) view.findViewById(R.id.view_amount);
                TextView descTextView = (TextView) view.findViewById(R.id.desc);

                String id = idTextView.getText().toString();
                String date = dateTextView.getText().toString();
                int amt = Integer.parseInt(amountTextView.getText().toString());
                String desc = descTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), editRecord.class);
                modify_intent.putExtra("date", date);
                modify_intent.putExtra("amt", amt);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });

        montlyexpense = (TextView) findViewById(R.id.monthlyexpense_textview);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {

            Intent add_mem = new Intent(this, addRecord.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }
}