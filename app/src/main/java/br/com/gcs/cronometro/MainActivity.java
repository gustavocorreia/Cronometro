package br.com.gcs.cronometro;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int segundos = 0;
    private boolean executando;
    private boolean estavaExecutando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            segundos = savedInstanceState.getInt("segundos");
            executando = savedInstanceState.getBoolean("executando");
            estavaExecutando = savedInstanceState.getBoolean("estavaExecutando", estavaExecutando);
        }

        executarCronometro();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("segundos", segundos);
        savedInstanceState.putBoolean("executando", executando);
        savedInstanceState.putBoolean("estavaExecutando", estavaExecutando);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(estavaExecutando){
            executando = true;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        estavaExecutando = executando;
        executando = false;
    }

    @Override
    protected  void onPause(){
        super.onPause();
        estavaExecutando = executando;
        executando = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(estavaExecutando){
            executando = true;
        }
    }

    public void onClickIniciar(View view) {
        executando = true;
    }

    public void onClickParar(View view) {
        executando = false;
    }

    public void onClickZerar(View view) {
        executando = false;
        segundos = 0;
    }

    private void executarCronometro(){
        final TextView txvCronometro = (TextView)findViewById(R.id.txvCronometro);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int horas = segundos / 3600;
                int minutos = (segundos % 3600) / 60;
                int segs = segundos % 60;

                String tempo = String.format("%d:%02d:%02d", horas, minutos, segs);
                txvCronometro.setText(tempo);
                if(executando){
                    segundos++;
                }

                handler.postDelayed(this, 1000);
            }
        });


    }
}
