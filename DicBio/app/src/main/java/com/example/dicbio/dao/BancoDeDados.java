package com.example.dicbio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.dicbio.modelo.Dicionario;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {
    public static final String NOMEBD = "Dicionario.db";
    public static final String LOCALDB = "/data/data/com.example.dicbio/databases/";
    public static final int VERSION = 3;
    private SQLiteDatabase mSQlitedatabase;
    private Context mContext;

    public static String BuscaPalavra ="";


    public BancoDeDados(@Nullable Context context) {
        super(context, NOMEBD, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(NOMEBD).getPath();
        if (mSQlitedatabase != null && mSQlitedatabase.isOpen()) {
            return;
        }
        mSQlitedatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDataBase() {
        if (mSQlitedatabase != null) {
            mSQlitedatabase.close();
        }
    }

    public List<Dicionario> Letras(String Letra) {

        openDatabase();
        mSQlitedatabase = this.getWritableDatabase();
        List<Dicionario> listPalavraLetra = new ArrayList<Dicionario>();
        String Sql = "SELECT * FROM Dicionario ";
       Cursor cursor = mSQlitedatabase.rawQuery(Sql, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Dicionario P = new Dicionario();
                    P.setIdPalavra(cursor.getInt(0));
                    P.setPalavra(cursor.getString(1));
                    P.setOrigem(cursor.getString(2));
                    P.setDescricao(cursor.getString(3));
                    P.setLetra(cursor.getString(4));
                    listPalavraLetra.add(P);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        mSQlitedatabase.close();
        return listPalavraLetra;
    }




}