package com.example.dicbio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {


    private  List<Dicionario> listDicionario = new ArrayList<Dicionario>();
    private  List<Dicionario> listDicionarioFiltrado = new ArrayList<Dicionario>();
    private ListView listProcura;
    private BancoDeDados mBancoDeDados;
    private ArrayAdapter<Dicionario> ArrayAdapaterDicionario;
    private String Letra= "#";
    private  SearchView pesquisaPrincipal ;
    int i=0;
    int iDia=0;
    int bloq=0;
    int bloqDia=0;



    private Button botaosorteador, botaomostrar, botaoDia, teste;
    private TextView palavraAleatoria,palavraDia;

    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
    Date data = new Date();
    String dataFormatada = formataData.format(data);
    String dataHj = dataFormatada;
    String dataIn = "07-07-2020";
    String diaPalavra ="";
    String diaOrigem = "";
    String diaDescricao = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InicializarBacodeDados();

        popularLista(Letra);

        inicializarComponentes();

        SearchView pesquisaPrincipal = (SearchView) findViewById(R.id.pesquisaPrincipal);
        pesquisaPrincipal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

       /* @Override
        public boolean onCreateOptionsMenu(menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.options_menu, menu);

            //return true;
        }*/

        teste=(Button) findViewById(R.id.teste) ;
        palavraAleatoria = (TextView) findViewById(R.id.txtAle);
        botaosorteador = (Button) findViewById(R.id.buttonSorteador);
        botaomostrar = (Button) findViewById(R.id.botaoSigAle);
        botaomostrar.setOnClickListener(this);
        botaosorteador.setOnClickListener(this);
        botaomostrar.setVisibility(View.INVISIBLE);
        teste.setOnClickListener(this);

        palavraDia =(TextView) findViewById(R.id.txtDia);
        Random random = new Random();
        /*if(bloqDia==1) {
            if (dataIn.equals(dataHj)) {
                bloqDia = 0;
            }
        }*/

        if(bloqDia == 0){
            int lista = listDicionario.size();
            iDia = random.nextInt(lista );
            palavraDia.setText(listDicionario.get(iDia).getPalavra());
             diaPalavra = listDicionario.get(iDia).getPalavra();
             diaOrigem = listDicionario.get(iDia).getOrigem();
             diaDescricao = listDicionario.get(iDia).getDescricao();
            bloqDia=1;
        }




        botaoDia = (Button) findViewById(R.id.botaoDia);
        botaoDia.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.buttonSorteador){
            Random random = new Random();
            int f = listDicionario.size();
            i = random.nextInt(f );
            palavraAleatoria.setText(listDicionario.get(i).getPalavra());
            botaomostrar.setVisibility(View.VISIBLE);
            bloq = 1;


        }
        if(view.getId() == R.id.botaoSigAle) {
            if (bloq == 1) {

                String Palavra = listDicionario.get(i).getPalavra();
                String Origem = listDicionario.get(i).getOrigem();
                String Descricao = listDicionario.get(i).getDescricao();
                chamaTela(Palavra, Origem, Descricao);

            }
        }

        if(view.getId() ==R.id.botaoDia){

            chamaTela(diaPalavra, diaOrigem, diaDescricao);

        }

        if(view.getId() ==R.id.teste){
            Intent pesquisa = new Intent(this,TelaPesquisa.class);
            startActivity(pesquisa);
        }


    }


}