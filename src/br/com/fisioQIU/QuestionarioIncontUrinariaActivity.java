package br.com.fisioQIU;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import br.com.fisioQIU.GerenciadorBancoDadosIU;

public class QuestionarioIncontUrinariaActivity extends Activity 
{
	GerenciadorBancoDadosIU gerenciador;
    /** Called when the activity is first created. */
    @Override
    public void onBackPressed() 
    {
    	ExibirMensagemOK("AVISO!", "ESTE BOTÃO FOI CONFIGURADO PARA NÃO VOLTAR PARA A QUESTÃO ANTERIOR E NÃO ENCERRAR ESTA APLICAÇÃO!");
 	   	return;
 	}
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);        
        gerenciador = new GerenciadorBancoDadosIU(this);
        chamaMenu();        
    }
          
    public void chamaMenu()

    {
    	setContentView(R.layout.menu);
    	final QuestionarioIU questionario = new QuestionarioIU();    	
    	questionario.resetQuestionario();
    	Button menu_btIniciar = (Button) findViewById(R.id.menu_btIniciar), menu_btSair = (Button) findViewById(R.id.menu_btSair), menu_btGerarCSV = (Button) findViewById(R.id.menu_btGerarCSV);   	
    	menu_btIniciar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				chamaQuest1(questionario);				
			}
		});
    	
    	menu_btSair.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();				
			}
		});
    	menu_btGerarCSV.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {	
				Vector<String> dados = gerenciador.SalvaBDString();
				if(GravaSD.grava("BkpBancoDadosQIU.csv", dados))
					ExibirMensagemOK("AVISO!", "DADOS EXPORTADOS COM SUCESSO PARA A PASTA RAIZ DO CARTÃO DE MEMORIA!");
				else
					ExibirMensagemOK("ERRO!", "O ARQUIVO NÃO PODE SER GRAVADO!");
			}
		});
    	
    }
    public void chamaQuest1(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest1);
    	    	    	
    	final EditText quest1_etQuest = (EditText) findViewById(R.id.quest1_etQuest);    	    	   	
    	Button quest1_btProximo = (Button) findViewById(R.id.quest1_btProximo), quest1_btMenu = (Button) findViewById(R.id.quest1_btMenu);
    	
    	if(questionario.quest1_idade != -1)
    	{
    		quest1_etQuest.setEnabled(true);
    		quest1_etQuest.setText(String.valueOf(questionario.quest1_idade));
    	}
    	
    	quest1_btProximo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(quest1_etQuest.getText().toString().length() == 0){
					ExibirMensagemOK("ERRO!", "O CAMPO IDADE DEVE ESTAR PREENCHIDO PARA PROSSEGUIR!");					
				}
				else{
					if(Integer.parseInt(quest1_etQuest.getText().toString()) < 1 || Integer.parseInt(quest1_etQuest.getText().toString()) > 150){
						quest1_etQuest.setText("");
						ExibirMensagemOK("ERRO!", "A IDADE DEVE SER ENTRE 1 E 150");						
					}
					else{						
						questionario.quest1_idade = Integer.parseInt(quest1_etQuest.getText().toString());
						chamaQuest2(questionario);
					}
				}
			}
    	});
    	
    	quest1_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest1_etQuest.getText().toString().length() == 0){
					questionario.quest1_idade = -1;
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 1!");
					chamaQuest2(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!", "VOCÊ SÓ PODE USAR ESTE COMANDO COM O CAMPO IDADE VAZIO!");
					questionario.resetQuest1();
					chamaQuest1(questionario);
					return true;
				}					
			}
		});
    	quest1_btMenu.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				chamaMenu();				
			}
		});
    }    
    public void chamaQuest2(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest2);
    	
    	RadioGroup quest2_rg = (RadioGroup) findViewById(R.id.quest2_rg);
    	final RadioButton quest2_rS = (RadioButton) findViewById(R.id.quest2_rS), quest2_rN = (RadioButton) findViewById(R.id.quest2_rN);
    	final EditText quest2_etQuantos = (EditText) findViewById(R.id.quest2_etQuantos);  
    	Button quest2_btProximo = (Button) findViewById(R.id.quest2_btProximo), quest2_btAnterior = (Button) findViewById(R.id.quest2_btAnterior);
    	
    	if(questionario.quest2_menopausa == 1)
    	{
    		quest2_rg.check(R.id.quest2_rS);
	    	if(questionario.quest2_tempo != -1){
    			quest2_etQuantos.setEnabled(true);
	    		quest2_etQuantos.setText(String.valueOf(questionario.quest2_tempo));
	    	}
	    	else quest2_etQuantos.setEnabled(false);
    	}
    	else if(questionario.quest2_menopausa == 0){
    		quest2_rg.check(R.id.quest2_rN);
    		quest2_etQuantos.setEnabled(false);
    	}
    	else quest2_etQuantos.setEnabled(false);
    	
    	
    	quest2_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
						    	
				boolean sim = R.id.quest2_rS == checkedId;
		    	boolean nao = R.id.quest2_rN == checkedId;
		    	
		    	if(sim){		    		
		    		quest2_etQuantos.setEnabled(true);
		    	}
		    	else if(nao){
		    		quest2_etQuantos.setText("");
		    		quest2_etQuantos.setEnabled(false);		    	
		    	}
		    }
		});
    	quest2_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest2_rS.isChecked()==false && quest2_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest2(questionario);					
				}
				else if(quest2_rS.isChecked() && quest2_etQuantos.isEnabled() == false){
					questionario.quest2_menopausa = 1;
					chamaQuest3(questionario);
				}
				if(quest2_rN.isChecked()){
					questionario.quest2_menopausa = 0;
					chamaQuest3(questionario);
				}
				else{
					questionario.quest2_menopausa = 1;
					if(quest2_etQuantos.isEnabled())
						if(quest2_etQuantos.getText().toString().length() == 0)
							ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, há quantos anos?) DEVE ESTAR PREENCHIDO PARA PROSSEGUIR!");
					else{
						if(Integer.parseInt(quest2_etQuantos.getText().toString()) < 0 || Integer.parseInt(quest2_etQuantos.getText().toString()) > 150){
							quest2_etQuantos.setText("");
							ExibirMensagemOK("ERRO!", "O VALOR DO CAMPO (Se SIM, há quantos anos?) DEVE ESTAR ENTRE 0 E 150!");
						}
						else{
							questionario.quest2_tempo = Integer.parseInt(quest2_etQuantos.getText().toString());
							chamaQuest3(questionario);
						}
					}
				}					
			}
		});
    	quest2_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest2_rS.isChecked()==false && quest2_rN.isChecked()==false){
					questionario.quest2_menopausa = -1;
					questionario.quest2_tempo = -1;
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 2!");
					chamaQuest3(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest2();
					chamaQuest2(questionario);
					return true;
				}
			}
		});
    	
    	quest2_etQuantos.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				quest2_etQuantos.setText("");
				quest2_etQuantos.setEnabled(false);
				questionario.quest2_tempo = -1;
				ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUANTO TEMPO PASSOU PELA MENOPAUSA!");
				return true;
			}
		});
    	quest2_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				chamaQuest1(questionario);				
			}
		});
    	
    }    
    public void chamaQuest3(final QuestionarioIU questionario)
    {    	
      	setContentView(R.layout.quest3);
    	   	 	
    	RadioGroup quest3_rg = (RadioGroup) findViewById(R.id.quest3_rg);
    	final RadioButton quest3_rS = (RadioButton) findViewById(R.id.quest3_rS), quest3_rN = (RadioButton) findViewById(R.id.quest3_rN);
    	final EditText quest3_etQuantos = (EditText) findViewById(R.id.quest3_etQuantos);  
    	Button quest3_btProximo = (Button) findViewById(R.id.quest3_btProximo), quest3_btAnterior = (Button) findViewById(R.id.quest3_btAnterior);	
    	
    	if(questionario.quest3_cirurgia == 1)
    	{
    		quest3_rg.check(R.id.quest3_rS);
	    	if(questionario.quest3_tempo != -1){
    			quest3_etQuantos.setEnabled(true);
	    		quest3_etQuantos.setText(String.valueOf(questionario.quest3_tempo));
	    	}
	    	else quest3_etQuantos.setEnabled(false);
    	}
    	else if(questionario.quest3_cirurgia == 0){
    		quest3_rg.check(R.id.quest3_rN);
    		quest3_etQuantos.setEnabled(false);
    	}
    	else quest3_etQuantos.setEnabled(false);    	
    	
    	quest3_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
						    	
				boolean sim = R.id.quest3_rS == checkedId;
		    	boolean nao = R.id.quest3_rN == checkedId;
		    	
		    	if(sim){		    		
		    		quest3_etQuantos.setEnabled(true);
		    	}
		    	else if(nao){
		    		quest3_etQuantos.setText("");
		    		quest3_etQuantos.setEnabled(false);		    	
		    	}
		    }
		});
    	quest3_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest3_rS.isChecked()==false && quest3_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest3(questionario);					
				}
				if(quest3_rN.isChecked()){
					questionario.quest3_cirurgia = 0;
					ExibirMensagemOK("AVISO!", "A QUESTÃO 4 FOI PULADA, POIS ELA SÓ DEVE SER RESPONDIDA SE A RESPOSTA DA QUESTÃO 3 FOR SIM!");
					questionario.resetQuest4();
					chamaQuest5(questionario);
				}
				else if(quest3_rS.isChecked() && quest3_etQuantos.isEnabled() == false){
					questionario.quest3_cirurgia = 1;
					chamaQuest4(questionario);
				}
				else{
					questionario.quest3_cirurgia = 1;
					if(quest3_etQuantos.isEnabled())
						if(quest3_etQuantos.getText().toString().length() == 0)
							ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, há quantos anos?) DEVE ESTAR PREENCHIDO PARA PROSSEGUIR!");
					else{
						if(Integer.parseInt(quest3_etQuantos.getText().toString()) < 0 || Integer.parseInt(quest3_etQuantos.getText().toString()) > 150){
							quest3_etQuantos.setText("");
							ExibirMensagemOK("ERRO!", "O VALOR DO CAMPO (Se SIM, há quantos anos?) DEVE ESTAR ENTRE 0 E 150!");
						}
						else{
							questionario.quest3_tempo = Integer.parseInt(quest3_etQuantos.getText().toString());
							chamaQuest4(questionario);
						}
					}
				}					
			}
		});
    	quest3_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest3_rS.isChecked()==false && quest3_rN.isChecked()==false){				
					questionario.quest3_cirurgia= -1;
					questionario.quest3_tempo = -1;
					ExibirMensagemOK("AVISO!", "A QUESTÃO 4 FOI PULADA, POIS ELA DEPENDE DA RESPOSTA DA QUESTÃO 3, QUE NÃO FOI RESPODIDA.");
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 3!");
					questionario.resetQuest4();
					chamaQuest5(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest3();
					chamaQuest3(questionario);
					return true;
				}
			}
		});
    	quest3_etQuantos.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				quest3_etQuantos.setText("");
				quest3_etQuantos.setEnabled(false);
				questionario.quest3_tempo = -1;
				ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUANTO TEMPO FOI SUBMETIDA A CIRURGIA DO PERÍNEO!");
				return true;
			}
		});
    	quest3_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest2_menopausa != -1)
					chamaQuest2(questionario);
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 3 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}						
			}
		});
    		
    }    
    public void chamaQuest4(final QuestionarioIU questionario)
    {    	
      	setContentView(R.layout.quest4);
    	    	
    	RadioGroup quest4_rg;
    	Button quest4_btProximo = (Button) findViewById(R.id.quest4_btProximo), quest4_btAnterior = (Button) findViewById(R.id.quest4_btAnterior);
    	final RadioButton quest4_rS = (RadioButton) findViewById(R.id.quest4_rS), quest4_rN = (RadioButton) findViewById(R.id.quest4_rN);
    	quest4_rg = (RadioGroup) findViewById(R.id.quest4_rg);
    	
    	if(questionario.quest4_perda_urina == 1)
    		quest4_rg.check(R.id.quest4_rS);
    	else if(questionario.quest4_perda_urina == 0)
    		quest4_rg.check(R.id.quest4_rN);
    	
    	quest4_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				final boolean sim = R.id.quest4_rS == checkedId;
		    	final boolean nao = R.id.quest4_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest4_perda_urina = 1;
		    	}
		    	else if(nao){
		    		questionario.quest4_perda_urina = 0;
		    	}
		    }
		});
    	quest4_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest4_rS.isChecked()==false && quest4_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest4(questionario);					
				}
				else chamaQuest5(questionario);				
			}
		});
    	quest4_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest4_rS.isChecked()==false && quest4_rN.isChecked()==false){
					questionario.quest4_perda_urina = -1;
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 4!");
					chamaQuest5(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest4();
					chamaQuest4(questionario);
					return true;
				}
			}
		});
    	quest4_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest3_cirurgia != -1)
					chamaQuest3(questionario);
				else if(questionario.quest2_menopausa != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 4 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}						
			}
		});
    	
    }
    public void chamaQuest5(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest5);
    	
    	RadioGroup quest5_rg;
    	final RadioButton quest5_rS = (RadioButton) findViewById(R.id.quest5_rS), quest5_rN = (RadioButton) findViewById(R.id.quest5_rN);
    	Button quest5_btProximo = (Button) findViewById(R.id.quest5_btProximo), quest5_btAnterior = (Button) findViewById(R.id.quest5_btAnterior);
    	quest5_rg = (RadioGroup) findViewById(R.id.quest5_rg);
    	
    	if(questionario.quest5_segura_urina == 1)
    		quest5_rg.check(R.id.quest5_rS);
    	else if(questionario.quest5_segura_urina == 0)
    		quest5_rg.check(R.id.quest5_rN);
    	
    	quest5_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				boolean sim = R.id.quest5_rS == checkedId;
		    	boolean nao = R.id.quest5_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest5_segura_urina = 1;
		    	}
		    	else if(nao){
		    		questionario.quest5_segura_urina = 0;
		    	}
		    }
		});
    	quest5_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest5_rS.isChecked()==false && quest5_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest5(questionario);					
				}
				else chamaQuest6(questionario);				
			}
		});
    	quest5_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest5_rS.isChecked()==false && quest5_rN.isChecked()==false){
					questionario.quest5_segura_urina = -1;
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 5!");
					chamaQuest6(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest5();
					chamaQuest5(questionario);
					return true;
				}
			}
		});
    	quest5_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest4_perda_urina != -1)
					chamaQuest4(questionario);
				else if(questionario.quest3_cirurgia != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_menopausa != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 5 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}						
			}
		});
    }
    public void chamaQuest6(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest6);
    	
    	RadioGroup quest6_rg;
    	final RadioButton quest6_rS = (RadioButton) findViewById(R.id.quest6_rS), quest6_rN = (RadioButton) findViewById(R.id.quest6_rN);
    	Button quest6_btProximo = (Button) findViewById(R.id.quest6_btProximo), quest6_btAnterior = (Button) findViewById(R.id.quest6_btAnterior);
    	quest6_rg = (RadioGroup) findViewById(R.id.quest6_rg);
    	
    	if(questionario.quest6_sente_perda == 1)
    		quest6_rg.check(R.id.quest6_rS);
    	else if(questionario.quest6_sente_perda == 0)
    		quest6_rg.check(R.id.quest6_rN);
    	
    	quest6_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				boolean sim = R.id.quest6_rS == checkedId;
		    	boolean nao = R.id.quest6_rN == checkedId;
		    			    	
				if(sim){		    		
		    		questionario.quest6_sente_perda = 1;
		    		
		    	}
		    	else if(nao){
		    		questionario.quest6_sente_perda = 0;
		    		
		    	}
		    }
		});
    	quest6_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest6_rS.isChecked()==false && quest6_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest6(questionario);					
				}
				else if(quest6_rS.isChecked())
					chamaQuest7(questionario);
				else{	
					questionario.resetQuest7();
					chamaQuest8(questionario);
					ExibirMensagemOK("AVISO!", "A QUESTÃO 7 FOI PULADA, POIS ELA SÓ DEVE SER RESPONDIDA SE A RESPOSTA DA QUESTÃO 6 FOR SIM!");
				}
			}
		});
	quest6_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest6_rS.isChecked()==false && quest6_rN.isChecked()==false){
					questionario.quest6_sente_perda = -1;
					ExibirMensagemOK("AVISO!", "A QUESTÃO 7 FOI PULADA, POIS ELA DEPENDE DA RESPOSTA DA QUESTÃO 6, QUE NÃO FOI RESPODIDA.");
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 6!");
					questionario.resetQuest7();
					chamaQuest8(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest6();
					chamaQuest6(questionario);
					return true;
				}
			}
		});	
	quest6_btAnterior.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			if(questionario.quest5_segura_urina != -1)
				chamaQuest5(questionario);
			else if(questionario.quest4_perda_urina != -1){
				ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
				chamaQuest4(questionario);
			}
			else if(questionario.quest3_cirurgia != -1){
				ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
				chamaQuest3(questionario);
			}
			else if(questionario.quest2_menopausa != -1){
				ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
				chamaQuest2(questionario);
			}
			else
			{
				if(questionario.quest1_idade != -1)
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
				else	
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 6 FOI RESPONDIDA!");
				chamaQuest1(questionario);
			}						
		}
	});
    
    }
    public void chamaQuest7(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest7);
    	
    	Button quest7_btProximo = (Button) findViewById(R.id.quest7_btProximo), quest7_btAnterior = (Button) findViewById(R.id.quest7_btAnterior);	
    	
    	final CheckBox quest7_cbCorre, quest7_cbPeso, quest7_cbApertada, quest7_cbRisada, quest7_cbEsforco, quest7_cbCaminhada, quest7_cbEscada, quest7_cbUsaAgua, quest7_cbApertadaPortaBanheiro;
    	quest7_cbCorre = (CheckBox) findViewById(R.id.quest7_cbCorre);
    	quest7_cbPeso = (CheckBox) findViewById(R.id.quest7_cbPeso);
    	quest7_cbApertada = (CheckBox) findViewById(R.id.quest7_cbApertada);
    	quest7_cbRisada = (CheckBox) findViewById(R.id.quest7_cbRisada);
    	quest7_cbEsforco = (CheckBox) findViewById(R.id.quest7_cbEsforco);
    	quest7_cbCaminhada = (CheckBox) findViewById(R.id.quest7_cbCaminhada);
    	quest7_cbEscada = (CheckBox) findViewById(R.id.quest7_cbEscada);
    	quest7_cbUsaAgua = (CheckBox) findViewById(R.id.quest7_cbUsaAgua);
    	quest7_cbApertadaPortaBanheiro = (CheckBox) findViewById(R.id.quest7_cbApertadaPortaBanheiro);
    	
    	if(questionario.quest7_corre == 1)
    		quest7_cbCorre.setChecked(true);
    	if(questionario.quest7_pega_peso == 1)
    		quest7_cbPeso.setChecked(true);
    	if(questionario.quest7_muito_apertada == 1)
    		quest7_cbApertada.setChecked(true);
    	if(questionario.quest7_muita_risada == 1)
    		quest7_cbRisada.setChecked(true);
    	if(questionario.quest7_muito_esforco == 1)
    		quest7_cbEsforco.setChecked(true);
    	if(questionario.quest7_caminhada == 1)
    		quest7_cbCaminhada.setChecked(true);
    	if(questionario.quest7_sobe_escada == 1)
    		quest7_cbEscada.setChecked(true);
    	if(questionario.quest7_usa_agua == 1)
    		quest7_cbUsaAgua.setChecked(true);
    	if(questionario.quest7_apertada_porta_banheiro == 1)
    		quest7_cbApertadaPortaBanheiro.setChecked(true);
    	
    	quest7_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest7_cbCorre.isChecked() == false && quest7_cbPeso.isChecked() == false 
				&& quest7_cbApertada.isChecked() == false && quest7_cbRisada.isChecked() == false 
				&& quest7_cbEsforco.isChecked() == false && quest7_cbCaminhada.isChecked() == false
				&& quest7_cbEscada.isChecked() == false && quest7_cbUsaAgua.isChecked() == false 
				&& quest7_cbApertadaPortaBanheiro.isChecked() == false)
					ExibirMensagemOK("ERRO!", "SELECIONE NO MÍNIMO UMA DAS OPÇÕES!");
				else{
					if(quest7_cbCorre.isChecked())
			    		questionario.quest7_corre = 1;
			    	else questionario.quest7_corre = 0;
			    	if(quest7_cbPeso.isChecked())
			    		questionario.quest7_pega_peso = 1;
			    	else questionario.quest7_pega_peso = 0;
			    	if(quest7_cbApertada.isChecked())
			    		questionario.quest7_muito_apertada = 1;
			    	else questionario.quest7_muito_apertada = 0;
			    	if(quest7_cbRisada.isChecked())
			    		questionario.quest7_muita_risada = 1;
			    	else questionario.quest7_muita_risada = 0;
			    	if(quest7_cbEsforco.isChecked())
			    		questionario.quest7_muito_esforco = 1;
			    	else questionario.quest7_muito_esforco = 0;
			    	if(quest7_cbCaminhada.isChecked())
			    		questionario.quest7_caminhada = 1;
			    	else questionario.quest7_caminhada = 0;
			    	if(quest7_cbEscada.isChecked())
			    		questionario.quest7_sobe_escada = 1;
			    	else questionario.quest7_sobe_escada = 0;
			    	if(quest7_cbUsaAgua.isChecked())
			    		questionario.quest7_usa_agua = 1;
			    	else questionario.quest7_usa_agua = 0;
			    	if(quest7_cbApertadaPortaBanheiro.isChecked())
			    		questionario.quest7_apertada_porta_banheiro = 1;
			    	else questionario.quest7_apertada_porta_banheiro = 0;
			    	chamaQuest8(questionario);
				}
			}
		});
    	quest7_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest7_cbCorre.isChecked() == false && quest7_cbPeso.isChecked() == false 
					&& quest7_cbApertada.isChecked() == false && quest7_cbRisada.isChecked() == false 
					&& quest7_cbEsforco.isChecked() == false && quest7_cbCaminhada.isChecked() == false
					&& quest7_cbEscada.isChecked() == false && quest7_cbUsaAgua.isChecked() == false 
					&& quest7_cbApertadaPortaBanheiro.isChecked() == false){
						questionario.quest7_corre = -1; questionario.quest7_pega_peso = -1; questionario.quest7_muito_apertada = -1; questionario.quest7_muita_risada = -1;
						questionario.quest7_muito_esforco = -1; questionario.quest7_caminhada = -1; questionario.quest7_sobe_escada = -1; questionario.quest7_usa_agua = -1;
						questionario.quest7_apertada_porta_banheiro = -1;
						ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 7");
						chamaQuest8(questionario);
						return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest7();
					chamaQuest7(questionario);
					return true;
				}
			}
		});
    	
    	quest7_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest6_sente_perda != -1)
					chamaQuest6(questionario);
				else if(questionario.quest5_segura_urina != -1){ 
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 5, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest5(questionario);
				}
				else if(questionario.quest4_perda_urina != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_cirurgia != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_menopausa != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 7 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}						
			}
		});
    	    	
    }
    public void chamaQuest8(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest8);
    	    	
    	RadioGroup quest8_rg;
    	final RadioButton quest8_rS = (RadioButton) findViewById(R.id.quest8_rS), quest8_rN = (RadioButton) findViewById(R.id.quest8_rN);
    	Button quest8_btProximo = (Button) findViewById(R.id.quest8_btProximo), quest8_btAnterior = (Button) findViewById(R.id.quest8_btAnterior);
    	quest8_rg = (RadioGroup) findViewById(R.id.quest8_rg);
    	
    	if(questionario.quest8_conversou_profissional == 1)
    		quest8_rg.check(R.id.quest8_rS);
    	else if(questionario.quest8_conversou_profissional == 0)
    		quest8_rg.check(R.id.quest8_rN);
    	
    	quest8_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				boolean sim = R.id.quest8_rS == checkedId;
		    	boolean nao = R.id.quest8_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest8_conversou_profissional = 1;
		    		
		    	}
		    	else if(nao){
		    		questionario.quest8_conversou_profissional = 0;
		    		
		    	}
		    }
		});
    	quest8_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest8_rS.isChecked()==false && quest8_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest8(questionario);					
				}
				else chamaQuest9(questionario);				
			}
		});
    	quest8_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest8_rS.isChecked()==false && quest8_rN.isChecked()==false){
					questionario.quest8_conversou_profissional = -1;
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 8!");
					chamaQuest9(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest8();
					chamaQuest8(questionario);
					return true;
				}
			}
		});
    	
    	quest8_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest7_pega_peso != -1)
					chamaQuest7(questionario);
				else if(questionario.quest6_sente_perda != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 6, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest6(questionario);
				}
				else if(questionario.quest5_segura_urina != -1){ 
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 5, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest5(questionario);
				}
				else if(questionario.quest4_perda_urina != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_cirurgia != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_menopausa != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 8 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}						
			}
		});
    }
    public void chamaQuest9(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest9);
    	
    	Button quest9_btProximo = (Button) findViewById(R.id.quest9_btProximo), quest9_btAnterior = (Button) findViewById(R.id.quest9_btAnterior);  	
    	RadioGroup quest9_rg;
    	final RadioButton quest9_rS = (RadioButton) findViewById(R.id.quest9_rS), quest9_rN = (RadioButton) findViewById(R.id.quest9_rN);
    	quest9_rg = (RadioGroup) findViewById(R.id.quest9_rg);
    	
    	if(questionario.quest9_sabe_tratamento == 1)
    		quest9_rg.check(R.id.quest9_rS);
    	else if(questionario.quest9_sabe_tratamento == 0)
    		quest9_rg.check(R.id.quest9_rN);
    	
    	quest9_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				boolean sim = R.id.quest9_rS == checkedId;
		    	boolean nao = R.id.quest9_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest9_sabe_tratamento = 1;
		    		
		    	}
		    	else if(nao){
		    		questionario.quest9_sabe_tratamento = 0;
		    		
		    	}
		    }
		});
    	quest9_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest9_rS.isChecked()==false && quest9_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest9(questionario);					
				}
				else chamaQuest10(questionario);				
			}
		});
    	quest9_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest9_rS.isChecked()==false && quest9_rN.isChecked()==false){
					questionario.quest9_sabe_tratamento = -1;
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 9!");
					chamaQuest10(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest9();
					chamaQuest9(questionario);
					return true;
				}
			}
		});
    	quest9_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest8_conversou_profissional != -1)
					chamaQuest8(questionario);
				else if(questionario.quest7_pega_peso != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 7, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest7(questionario);
				}
				else if(questionario.quest6_sente_perda != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 6, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest6(questionario);
				}
				else if(questionario.quest5_segura_urina != -1){ 
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 5, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest5(questionario);
				}
				else if(questionario.quest4_perda_urina != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_cirurgia != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_menopausa != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 9 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}						
			}
		});
    }
    public void chamaQuest10(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest10);
    	   	
    	RadioGroup quest10_rg;
    	final RadioButton quest10_rS = (RadioButton) findViewById(R.id.quest10_rS), quest10_rN = (RadioButton) findViewById(R.id.quest10_rN);
    	Button quest10_btProximo = (Button) findViewById(R.id.quest10_btProximo), quest10_btAnterior = (Button) findViewById(R.id.quest10_btAnterior);
    	quest10_rg = (RadioGroup) findViewById(R.id.quest10_rg);
    	
    	if(questionario.quest10_medicamento_perda == 1)
    		quest10_rg.check(R.id.quest10_rS);
    	else if(questionario.quest10_medicamento_perda == 0)
    		quest10_rg.check(R.id.quest10_rN);
    	
    	quest10_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
												
				boolean sim = R.id.quest10_rS == checkedId;
		    	boolean nao = R.id.quest10_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest10_medicamento_perda = 1;
		    		
		    	}
		    	else if(nao){
		    		questionario.quest10_medicamento_perda = 0;
		    		
		    	}
		    }
		});
    	quest10_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest10_rS.isChecked()==false && quest10_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest10(questionario);					
				}
				else chamaQuest11(questionario);				
			}
		});
    	quest10_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest10_rS.isChecked()==false && quest10_rN.isChecked()==false){
					questionario.quest10_medicamento_perda = -1;
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 10!");
					chamaQuest11(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest10();
					chamaQuest10(questionario);
					return true;
				}
			}
		});
    	quest10_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest9_sabe_tratamento != -1)
					chamaQuest9(questionario);
				else if(questionario.quest8_conversou_profissional != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 8, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest8(questionario);
				}
				else if(questionario.quest7_pega_peso != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 7, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest7(questionario);
				}
				else if(questionario.quest6_sente_perda != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 6, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest6(questionario);
				}
				else if(questionario.quest5_segura_urina != -1){ 
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 5, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest5(questionario);
				}
				else if(questionario.quest4_perda_urina != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_cirurgia != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_menopausa != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 10 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}									
			}
		});
    }
    public void chamaQuest11(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest11);
   	 	
    	RadioGroup quest11_rg = (RadioGroup) findViewById(R.id.quest11_rg);
    	final RadioButton quest11_rS = (RadioButton) findViewById(R.id.quest11_rS), quest11_rN = (RadioButton) findViewById(R.id.quest11_rN);
    	final EditText quest11_etQuais = (EditText) findViewById(R.id.quest11_etQuais);  
    	Button quest11_btProximo = (Button) findViewById(R.id.quest11_btProximo), quest11_btAnterior = (Button) findViewById(R.id.quest11_btAnterior);	
    	
    	if(questionario.quest11_outro_medicamento == 1)
    	{
    		quest11_rg.check(R.id.quest11_rS);
	    	if(questionario.quest11_quais != "-1"){
    			quest11_etQuais.setEnabled(true);
	    		quest11_etQuais.setText(questionario.quest11_quais);
	    	}
	    	else quest11_etQuais.setEnabled(false);
    	}
    	else if(questionario.quest11_outro_medicamento == 0){
    		quest11_rg.check(R.id.quest11_rN);
    		quest11_etQuais.setEnabled(false);
    	}
    	else quest11_etQuais.setEnabled(false);
    	
    	quest11_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
						    	
				boolean sim = R.id.quest11_rS == checkedId;
		    	boolean nao = R.id.quest11_rN == checkedId;
		    	
		    	if(sim){		    		
		    		quest11_etQuais.setEnabled(true);
		    	}
		    	else if(nao){
		    		quest11_etQuais.setText("");
		    		quest11_etQuais.setEnabled(false);		    	
		    	}
		    }
		});
    	quest11_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest11_rS.isChecked()==false && quest11_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest11(questionario);					
				}
				if(quest11_rN.isChecked()){
					questionario.quest11_outro_medicamento = 0;
					chamaQuest12(questionario);
				}
				else if(quest11_rS.isChecked() && quest11_etQuais.isEnabled() == false){
					questionario.quest11_outro_medicamento = 1;
					chamaQuest12(questionario);
				}
				else{
					questionario.quest11_outro_medicamento = 1;
					if(quest11_etQuais.isEnabled())
						if(quest11_etQuais.getText().toString().length() == 0)
							ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, quais?) DEVE ESTAR PREENCHIDO PARA PROSSEGUIR!");
					else{						
						if(quest11_etQuais.getText().toString().length() < 6){
							quest11_etQuais.setText("");
							ExibirMensagemOK("ERRO!", "O TAMANHO DO TEXTO DO CAMPO (Se SIM, quais?) DEVE CONTER NO MÍNIMO 6 CARACTERES!");
						}
						else{
							questionario.quest11_quais = quest11_etQuais.getText().toString();
							chamaQuest12(questionario);
						}
					}
				}					
			}
		});
    	quest11_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest11_rS.isChecked()==false && quest11_rN.isChecked()==false){
					questionario.quest11_outro_medicamento = -1;
					questionario.quest11_quais = "-1";
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 11!");
					chamaQuest12(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest11();
					chamaQuest11(questionario);
					return true;
				}
			}
		});
    	quest11_etQuais.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				quest11_etQuais.setText("");
				quest11_etQuais.setEnabled(false);
				questionario.quest11_quais = "-1";
				ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER QUAIS REMÉDIOS ELA TOMA!");
				return true;
			}
		});
    	quest11_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest10_medicamento_perda != -1)
					chamaQuest10(questionario);
				else if(questionario.quest9_sabe_tratamento != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 9, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest9(questionario);
				}
				else if(questionario.quest8_conversou_profissional != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 8, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest8(questionario);
				}
				else if(questionario.quest7_pega_peso != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 7, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest7(questionario);
				}
				else if(questionario.quest6_sente_perda != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 6, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest6(questionario);
				}
				else if(questionario.quest5_segura_urina != -1){ 
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 5, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest5(questionario);
				}
				else if(questionario.quest4_perda_urina != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_cirurgia != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_menopausa != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 10 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}									
			}
		});
    }
    public void chamaQuest12(final QuestionarioIU questionario)
    {    	
    	setContentView(R.layout.quest12);
    	    	
    	RadioGroup quest12_rg;
    	final RadioButton quest12_rS = (RadioButton) findViewById(R.id.quest12_rS), quest12_rN = (RadioButton) findViewById(R.id.quest12_rN);
    	Button quest12_btFinalizar = (Button) findViewById(R.id.quest12_btFinalizar), quest12_btAnterior = (Button) findViewById(R.id.quest12_btAnterior);
    	quest12_rg = (RadioGroup) findViewById(R.id.quest12_rg);
    	
    	if(questionario.quest12_quer_tratamento == 1)
    		quest12_rg.check(R.id.quest12_rS);
    	else if(questionario.quest12_quer_tratamento == 0)
    		quest12_rg.check(R.id.quest12_rN);
    	
    	quest12_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				boolean sim = R.id.quest12_rS == checkedId;
		    	boolean nao = R.id.quest12_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest12_quer_tratamento = 1;
		    		
		    	}
		    	else if(nao){
		    		questionario.quest12_quer_tratamento = 0;
		    		}
		    }
		});
    	quest12_btFinalizar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest12_rS.isChecked()==false && quest12_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest12(questionario);
				}
				else
				{					
					ExibirQtdeQuestColhidos();
					salvaQuestionarioBancoDados(questionario);	
				}
			}
		});
    	quest12_btFinalizar.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest12_rS.isChecked()==false && quest12_rN.isChecked()==false){
					questionario.quest12_quer_tratamento = -1;					
					ExibirQtdeQuestColhidos();
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 12!");
					salvaQuestionarioBancoDados(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest12();
					chamaQuest12(questionario);
					return true;
				}
			}
		});
    	quest12_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest11_outro_medicamento != -1)
					chamaQuest11(questionario);
				else if(questionario.quest10_medicamento_perda != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 10, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest10(questionario);
				}
				else if(questionario.quest9_sabe_tratamento != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 9, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest9(questionario);
				}
				else if(questionario.quest8_conversou_profissional != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 8, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest8(questionario);
				}
				else if(questionario.quest7_pega_peso != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 7, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest7(questionario);
				}
				else if(questionario.quest6_sente_perda != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 6, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest6(questionario);
				}
				else if(questionario.quest5_segura_urina != -1){ 
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 5, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest5(questionario);
				}
				else if(questionario.quest4_perda_urina != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_cirurgia != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_menopausa != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_idade != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 10 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}
			}
		});
    }

    public void salvaQuestionarioBancoDados(QuestionarioIU questionario)
    {
    	gerenciador.inserirQuestionario(questionario);
    	chamaMenu();
    }
    
    public void ExibirQtdeQuestColhidos()
    {
    	if(gerenciador.getQuestionariosCount() <= 1 )
			ExibirMensagemOK("PARABÉNS", "VOCÊ CONCLUIU ESTA COLETA DE DADOS, E AGORA POSSUI " + gerenciador.getQuestionariosCount() + " QUESTIONÁRIO COLHIDO!");
		else
			ExibirMensagemOK("PARABÉNS", "VOCÊ CONCLUIU ESTA COLETA DE DADOS, E AGORA POSSUI " + gerenciador.getQuestionariosCount() + " QUESTIONÁRIOS COLHIDOS!");
    }
    
    public void ExibirMensagemOK(String titulo, String texto)
    {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(QuestionarioIncontUrinariaActivity.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();
	}    
}