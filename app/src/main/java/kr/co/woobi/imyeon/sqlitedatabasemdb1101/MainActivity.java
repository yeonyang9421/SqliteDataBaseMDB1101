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

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyDBOpenHelper dbOpenHelper;
    SQLiteDatabase mdb;
    Button buttonInsert, buttonSelect, buttonUpdate, buttonDelete, buttonDelete_id, buttonSearch, buttonAddVisited;
    EditText editTextCountry, editTextCity;
    TextView textViewPKid,textViewCount;
    String strPkID;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOpenHelper=new MyDBOpenHelper(this, "awe.db",null, 2);
        mdb=dbOpenHelper.getWritableDatabase();
        editTextCountry=findViewById(R.id.editTextCountry);
        textViewPKid=findViewById(R.id.textViewPKid);
        textViewCount=findViewById(R.id.textViewCount);
        editTextCity=findViewById(R.id.editTextCity);
        buttonInsert=findViewById(R.id.buttonInsert);
        buttonSelect=findViewById(R.id. buttonSelect);
        buttonUpdate=findViewById(R.id. buttonUpdate);
        buttonDelete=findViewById(R.id.buttonDelete);
        buttonDelete_id=findViewById(R.id.buttonDelete_id);
        buttonSearch=findViewById(R.id.buttonSearch);
        buttonAddVisited=findViewById(R.id.buttonAddVisited);
        buttonSearch.setOnClickListener(this);
        buttonInsert.setOnClickListener(this);
        buttonSelect.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonDelete_id.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String country, city,query;
        TextView textViewShow;
        textViewShow=findViewById(R.id.textViewShow);
        EditText editTextCountry=findViewById(R.id.editTextCountry);
        country=editTextCountry.getText().toString();
        EditText textViewCity=findViewById(R.id.editTextCity);
        city=textViewCity.getText().toString();
        String inputcountry,inputcity,strPkID;
        int visitedTotal;
        switch (v.getId()){
            case R.id.buttonAddVisited:
                strPkID=textViewPKid.getText().toString();
                query="Insert into awe_country_visitiedcount values('" + strPkID + "')";
                mdb.execSQL(query);
                break;

            case R.id.buttonSearch:
                country=editTextCountry.getText().toString();
                query="Select pkid, country, city, count(fkid) visitedTotal "+
                        "From awe_country inner join awe_country_visitedcount " +
                        "on pkid=fkid and country ='" + country + "' ";
                cursor=mdb.rawQuery(query,null);
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    textViewCity.setText(city);
                    visitedTotal=cursor.getInt(cursor.getColumnIndex("visitedTotal"));
                    textViewCount.setText(String.valueOf(visitedTotal));
                }

            case  R.id.buttonInsert:
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String datetime = format.format(new Date());
                mdb.execSQL("INSERT INTO awe_country VALUES('"+datetime+"', '"+ country + "','"+city+"');");
                textViewShow.setText(dbRead());
                break;
            case R.id.buttonSelect:
                String str = dbRead();
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
        cursor=mdb.rawQuery(query,null);
        String str="";

        while(cursor.moveToNext()) {
            strPkID = cursor.getString(0);
            country = cursor.getString(cursor.getColumnIndex("country"));
            city = cursor.getString(2);
            str += (strPkID + " : " + country + " - " + city + "\n");
        }
        if(str.equals(""))
            str="no record";
        return str;
    }
}

