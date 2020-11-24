package com.example.dicbio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.SearchView;

import android.widget.Toast;



import com.example.dicbio.dao.BancoDeDados;
import com.example.dicbio.modelo.Dicionario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;

import java.util.List;


public class TelaPesquisa extends Activity{



    private List<Dicionario> listDicionario = new ArrayList<Dicionario>();
    private  List<Dicionario> listDicionarioFiltrado = new ArrayList<Dicionario>();
    private ListView listProcura;
    private BancoDeDados mBancoDeDados;
    private ArrayAdapter<Dicionario> ArrayAdapaterDicionario;
    private String Letra= "#";
    private  SearchView pesquisaPrincipal ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InicializarBacodeDados();

        popularLista(Letra);

        inicializarComponentes();

        SearchView Pesquiaaqui = (SearchView) findViewById(R.id.Pesquiaaqui);
        Pesquiaaqui.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                apagalist();
               /* listProcura.clearTextFilter();
                listProcura.setVisibility(View.INVISIBLE);*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("Digitou: "+ s);
                procuraPalavra(s);
                return false;
            }

        });








        listProcura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int b, long l) {

                String Palavra = listDicionarioFiltrado.get(b).getPalavra();
                String Origem = listDicionarioFiltrado.get(b).getOrigem();
                String Descricao = listDicionarioFiltrado.get(b).getDescricao();
              /*  listProcura.clearTextFilter();
                listProcura.setVisibility(View.INVISIBLE);*/
                //String teste = Integer.toString(i);
                //Toast.makeText(getApplicationContext(),listDicionario.get(10).getPalavra,Toast.LENGTH_LONG).show();
                chamaTela(Palavra, Origem, Descricao);

                //apagalist();


            }
        });

    }

    public void apagalist(){

        listDicionarioFiltrado.clear();
        ArrayAdapaterDicionario = new ArrayAdapter<Dicionario>(this, android.R.layout.simple_expandable_list_item_1, listDicionarioFiltrado);
        listProcura.setAdapter(ArrayAdapaterDicionario);
        return;
    }

    public void procuraPalavra(String palavra){
        listDicionarioFiltrado.clear();
        listProcura.setVisibility(View.VISIBLE);
        for (Dicionario pa : listDicionario ){
            if (pa.getPalavra().toLowerCase().contains(palavra.toLowerCase())){
                listDicionarioFiltrado.add(pa);
                ArrayAdapaterDicionario = new ArrayAdapter<Dicionario>(this,android.R.layout.simple_expandable_list_item_1,listDicionarioFiltrado);
                listProcura.setAdapter(ArrayAdapaterDicionario);
            }
        }
        listProcura.invalidateViews();
      /*  listProcura.clearTextFilter();
        listProcura.setVisibility(View.INVISIBLE);*/
    }

    private void InicializarBacodeDados() {
        mBancoDeDados = new BancoDeDados(this);

        File database = getApplicationContext().getDatabasePath(BancoDeDados.NOMEBD);
        if(database.exists() == false){
            mBancoDeDados.getReadableDatabase();
            if(copiaBanco(this)){
                alert("banco copiado com sucesso");
            }else{
                alert("Erro ao copiar o banco");
            }
        }
    }

    private void alert(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    private boolean copiaBanco(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(BancoDeDados.NOMEBD);
            String outFlie = BancoDeDados.LOCALDB + BancoDeDados.NOMEBD;
            OutputStream outputStream = new FileOutputStream(outFlie);
            byte[] buff = new byte[1024];
            int legth = 0;
            while((legth = inputStream.read(buff))>0){
                outputStream.write(buff,0,legth);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void popularLista(String Letra) {
        mBancoDeDados = new BancoDeDados(this);
        listDicionario.clear();
        listDicionario = mBancoDeDados.Letras(Letra);

    }


    public void inicializarComponentes() {

        listProcura = (ListView) findViewById(R.id.listProcura);
    }


    private void chamaTela(String Palavra,String Origem,String Descricao) {
        Intent intent = new Intent(this,MostraSignivicado.class);
        intent.putExtra("Palavra", Palavra);
        intent.putExtra("Origem", Origem);
        intent.putExtra("Descricao", Descricao);
        startActivity(intent);
    }

}

























