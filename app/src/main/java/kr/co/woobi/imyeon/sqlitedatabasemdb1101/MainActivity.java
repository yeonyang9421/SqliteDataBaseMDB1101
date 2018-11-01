package kr.co.woobi.imyeon.sqlitedatabasemdb1101;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyDBOpenHelper dbOpenHelper;
    SQLiteDatabase mdb;
    Button buttonInsert, buttonSelect, buttonUpdate, buttonDelete, buttonDelete_id;
    EditText editTextCountry, editTextCity;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOpenHelper=new MyDBOpenHelper(this, "awe.db",null, 1);
        mdb=dbOpenHelper.getWritableDatabase();

        editTextCountry=findViewById(R.id.editTextCountry);
        editTextCity=findViewById(R.id.editTextCity);
        buttonInsert=findViewById(R.id.buttonInsert);
        buttonSelect=findViewById(R.id. buttonSelect);
        buttonUpdate=findViewById(R.id. buttonUpdate);
        buttonDelete=findViewById(R.id.buttonDelete);
        buttonDelete_id=findViewById(R.id.buttonDelete_id);
        buttonInsert.setOnClickListener(this);
        buttonSelect.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonDelete_id.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String country, city;
        TextView textViewShow;
        textViewShow=findViewById(R.id.textViewShow);
        EditText editTextCountry=findViewById(R.id.editTextCountry);
        country=editTextCountry.getText().toString();
        EditText editTextCity=findViewById(R.id.editTextCity);
        city=editTextCity.getText().toString();
        String inputcountry,inputcity;

        switch (v.getId()){
            case  R.id.buttonInsert:
                mdb.execSQL("INSERT INTO awe_country VALUES(null, '"+ country + "','"+city+"');");
                textViewShow.setText(dbRead());
                break;
            case R.id.buttonSelect:
                String str = dbRead();
                String query;
                Cursor cursor;
                      if(str.length()>0){
                          textViewShow.setText(str);
                      }
                break;
            case R.id.buttonUpdate:
                inputcountry = editTextCountry.getText().toString();
                inputcity=editTextCity.getText().toString();
                mdb.execSQL("Update awe_country set city='"+inputcity+"' where country='"+inputcountry+"'");
                textViewShow.setText(dbRead());
                break;
            case R.id.buttonDelete:
                inputcountry=editTextCountry.getText().toString();
                mdb.execSQL("Delete from awe_country where country='"+inputcountry+"' ");
                textViewShow.setText(dbRead());
                break;
            case R.id.buttonDelete_id:
                inputcountry = editTextCountry.getText().toString();
                query="Select _id From awe_country where country='"+inputcountry+"'";
                cursor=mdb.rawQuery(query,null);
                String idString = "";
                if(cursor.moveToNext()) idString = cursor.getString(0);
                mdb.execSQL("Delete from awe_country where _id=?", new Object[] {idString});
                textViewShow.setText(dbRead());
                break;
        }
    }

    @NonNull
    private String dbRead() {
        String country;
        String city;
        String query="Select * From awe_country";
        Cursor cursor=mdb.rawQuery(query,null);
        String str="";

        while(cursor.moveToNext()) {
            id = cursor.getInt(0);
            country = cursor.getString(cursor.getColumnIndex("country"));
            city = cursor.getString(2);
            str += (id + " : " + country + " - " + city + "\n");
        }
        if(str.equals(""))
            str="no record";
        return str;
    }
}
