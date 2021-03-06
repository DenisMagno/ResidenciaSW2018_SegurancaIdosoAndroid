package com.scopus.ifsp.projetofinalteste.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scopus.ifsp.projetofinalteste.R;
import com.scopus.ifsp.projetofinalteste.bussiness.CronometroAsync;
import com.scopus.ifsp.projetofinalteste.bussiness.interfaces.ICronometroListener;
import com.scopus.ifsp.projetofinalteste.service.AlertaService;
import com.scopus.ifsp.projetofinalteste.service.EnvioMensagemService;

/**
 * @author Denis Magno
 */
public class AlertaQuedaActivity extends AppCompatActivity implements ICronometroListener {
    private String TAG = "Custom - " + this.getClass().getSimpleName();

    private Button btnSim;
    private Button btnNao;
    private TextView txtCronometro;

    private Intent servicoAlerta;
    private Intent servicoEnvioMensagem;
    private CronometroAsync cronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_alerta_queda);

        iniciarComponentesGraficos();

        //Inicia e executa cronometro definindo segundos a serem contados.
        this.cronometro = new CronometroAsync(this, txtCronometro);
        this.cronometro.execute(10);

        //Inicia e executa serviço de alerta (Sonoro e vibração)
        this.servicoAlerta = new Intent(this, AlertaService.class);
        //this.startService(this.servicoAlerta);

        //Inicia serviço envio de mensagens pelo whatsapp
        this.servicoEnvioMensagem = new Intent(this, EnvioMensagemService.class);
    }

    private void iniciarComponentesGraficos() {
        this.txtCronometro = (TextView) this.findViewById(R.id.txtCronometro);

        /**
         *  Informa que possível queda foi um falso positivo ou está tudo bem.
         */
        this.btnSim = (Button) findViewById(R.id.btnSim);
        this.btnSim.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(AlertaQuedaActivity.this, "Ok. Desculpe-nos pelo incomodo.", Toast.LENGTH_SHORT).show();
                finish();
                return false;
            }
        });

        /**
         *  Confirma que houve uma queda.
         */
        this.btnNao = (Button) findViewById(R.id.btnNao);
        this.btnNao.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(AlertaQuedaActivity.this, "Será enviado um alerta sobre sua condição.", Toast.LENGTH_SHORT).show();
                startService(servicoEnvioMensagem);
                finish();
                return false;
            }
        });
    }

    /**
     * É chamado quando o usuário não responde o alerta de queda depois de um período de tempo
     * estipulado no método '.execute()', em 'onCreate'.
     *
     * @author Denis Magno
     */
    @Override
    public void onTimeOut() {
        Toast.makeText(this, "Tempo esgotado!", Toast.LENGTH_SHORT).show();

        Intent in = new Intent(this, FichaMedicaActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(in);
        finish();
    }

    /**
     * Para cronômetro.
     *
     * @author Denis Magno
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.cronometro.cancel(true);
        Toast.makeText(this, "Cronômetro parado!", Toast.LENGTH_SHORT).show();
        this.stopService(this.servicoAlerta);
        this.stopService(this.servicoEnvioMensagem);
    }
}