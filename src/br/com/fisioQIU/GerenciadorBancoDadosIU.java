package br.com.fisioQIU;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.fisioQIU.QuestionarioIU;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GerenciadorBancoDadosIU extends SQLiteOpenHelper{
	
		private static final String NOME_BANCO = "questionarioIU.db"; //atributos de identificação do banco de dados
		private static final int VERSAO_BANCO = 1;
		private static final String TABELA_QUESTIONARIOS = "questionarios";
		
		
		public GerenciadorBancoDadosIU (Context context) // contrutor que passa os parametros do banco que vai ser criado para a classe pai o criar.
		{
			super(context, NOME_BANCO, null, VERSAO_BANCO);
		}
		
		public void onCreate(SQLiteDatabase db) // método de criação do banco de dados e suas tabelas.
		{
			db.execSQL("CREATE TABLE "+ TABELA_QUESTIONARIOS +" (id INTEGER PRIMARY KEY AUTOINCREMENT," 
						+ " quest1_idade INTEGER NOT NULL, quest2_menopausa INTEGER NOT NULL, quest2_tempo INTEGER NOT NULL,"
						+ " quest3_cirurgia INTEGER NOT NULL, quest3_tempo INTEGER, quest4_perda_urina INTEGER NOT NULL,"
						+ " quest5_segura_urina INTEGER NOT NULL, quest6_sente_perda INTEGER NOT NULL, quest7_corre INTEGER NOT NULL,"
						+ " quest7_pega_peso INTEGER NOT NULL, quest7_muito_apertada INTEGER NOT NULL, quest7_muita_risada INTEGER NOT NULL,"
						+ " quest7_muito_esforco INTEGER NOT NULL, quest7_caminhada INTEGER NOT NULL, quest7_sobe_escada INTEGER NOT NULL,"
						+ " quest7_usa_agua INTEGER NOT NULL, quest7_apertada_porta_banheiro INTEGER NOT NULL, quest8_conversou_profissional INTEGER NOT NULL,"
						+ " quest9_sabe_tratamento INTEGER NOT NULL, quest10_medicamento_perda INTEGER NOT NULL, quest11_outro_medicamento INTEGER NOT NULL,"
						+ " quest11_quais TEXT NOT NULL, quest12_quer_tratamento INTEGER NOT NULL);"); // string em sql que diz como vai ser criado o banco de dados.
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) // método para atualizar o banco de dados para uma nova versão caso faça alterações na aplicação.
		{
			
		}
		
		public void inserirQuestionario(QuestionarioIU questionario) {   // método para inserir uma pessoa no banco de dados;
		    
			ContentValues valores = new ContentValues(); 	 
		    valores.put("quest1_idade", questionario.quest1_idade);            //insere na coluna nome o valor de p_pessoa.nome().
		    valores.put("quest2_menopausa", questionario.quest2_menopausa);    
		    valores.put("quest2_tempo", questionario.quest2_tempo);         
		    valores.put("quest3_cirurgia", questionario.quest3_cirurgia);
		    valores.put("quest3_tempo", questionario.quest3_tempo);
		    valores.put("quest4_perda_urina", questionario.quest4_perda_urina);
		    valores.put("quest5_segura_urina", questionario.quest5_segura_urina);		    
		    valores.put("quest6_sente_perda", questionario.quest6_sente_perda);    
		    valores.put("quest7_corre", questionario.quest7_corre);         
		    valores.put("quest7_pega_peso", questionario.quest7_pega_peso);
		    valores.put("quest7_muito_apertada", questionario.quest7_muito_apertada);
		    valores.put("quest7_muita_risada", questionario.quest7_muita_risada);
		    valores.put("quest7_muito_esforco", questionario.quest7_muito_esforco);
		    valores.put("quest7_caminhada", questionario.quest7_caminhada);    
		    valores.put("quest7_sobe_escada", questionario.quest7_sobe_escada);         
		    valores.put("quest7_usa_agua", questionario.quest7_usa_agua);
		    valores.put("quest7_apertada_porta_banheiro", questionario.quest7_apertada_porta_banheiro);
		    valores.put("quest8_conversou_profissional", questionario.quest8_conversou_profissional);
		    valores.put("quest9_sabe_tratamento", questionario.quest9_sabe_tratamento);
		    valores.put("quest10_medicamento_perda", questionario.quest10_medicamento_perda);    
		    valores.put("quest11_outro_medicamento", questionario.quest11_outro_medicamento);         
		    valores.put("quest11_quais", questionario.quest11_quais);
		    valores.put("quest12_quer_tratamento", questionario.quest12_quer_tratamento);
		    
		    getWritableDatabase().insert(TABELA_QUESTIONARIOS, null, valores); // pega uma versão para escrita do banco e insere os valores de pessoa na tabela pessoas.
		}
		
		
		public int getQuestionariosCount() {      // método para contar o número de pessoas cadastradas na tabela pessoas.
	        String countQuery = "SELECT  * FROM " + TABELA_QUESTIONARIOS;  //string em sql para a busca.
	        SQLiteDatabase db = this.getReadableDatabase(); // pega uma versão de leitura do banco de dados.
	        Cursor cursor = db.rawQuery(countQuery, null); // armazena em cursor o resultado da busca no banco.	 
	        return cursor.getCount(); // retorna a quantidade de ocorrencias de dados na tabela pessoas.
	    }
		
		public List<QuestionarioIU> getAllQuestionarios() {
		    List<QuestionarioIU> listaQuestionarios = new ArrayList<QuestionarioIU>();
		    // Select All Query
		    String selectQuery = "SELECT * FROM " + TABELA_QUESTIONARIOS;
		 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
		    try{
		    if (cursor.moveToFirst()) 
		    {
		        do {
		        	QuestionarioIU questionario = new QuestionarioIU();		            
		            questionario.quest1_idade = Integer.parseInt(cursor.getString(0));            //insere na coluna nome o valor de p_pessoa.nome().
				    questionario.quest2_menopausa = Integer.parseInt(cursor.getString(1));    
				    questionario.quest2_tempo = Integer.parseInt(cursor.getString(2));         
				    questionario.quest3_cirurgia = Integer.parseInt(cursor.getString(3));
				    questionario.quest3_tempo = Integer.parseInt(cursor.getString(4));   
				    questionario.quest4_perda_urina = Integer.parseInt(cursor.getString(5));
				    questionario.quest5_segura_urina = Integer.parseInt(cursor.getString(6));		    
				    questionario.quest6_sente_perda = Integer.parseInt(cursor.getString(7));    
				    questionario.quest7_corre = Integer.parseInt(cursor.getString(8));         
				    questionario.quest7_pega_peso = Integer.parseInt(cursor.getString(9));
				    questionario.quest7_muito_apertada = Integer.parseInt(cursor.getString(10));
				    questionario.quest7_muita_risada = Integer.parseInt(cursor.getString(11));
				    questionario.quest7_muito_esforco = Integer.parseInt(cursor.getString(12));
				    questionario.quest7_caminhada = Integer.parseInt(cursor.getString(13));    
				    questionario.quest7_sobe_escada = Integer.parseInt(cursor.getString(14));         
				    questionario.quest7_usa_agua = Integer.parseInt(cursor.getString(15));
				    questionario.quest7_apertada_porta_banheiro = Integer.parseInt(cursor.getString(16));
				    questionario.quest8_conversou_profissional = Integer.parseInt(cursor.getString(17));
				    questionario.quest9_sabe_tratamento = Integer.parseInt(cursor.getString(18));
				    questionario.quest10_medicamento_perda = Integer.parseInt(cursor.getString(19));    
				    questionario.quest11_outro_medicamento = Integer.parseInt(cursor.getString(20));         
				    questionario.quest11_quais = cursor.getString(21);
				    questionario.quest12_quer_tratamento = Integer.parseInt(cursor.getString(22));
							    
		            // Adding questionario to list
		            listaQuestionarios.add(questionario);
		        } while (cursor.moveToNext());
		    }
		    }
		    catch(Exception erro)
		    {
		    	
		    }
		   return listaQuestionarios;
		}

		
		
		
		public Vector<String> SalvaBDString()
		{
			Vector<String> dados = new Vector<String>();
			dados.add("quest1_idade; quest2_menopausa; quest2_tempo; quest3_cirurgia; quest3_tempo; quest4_perda_urina; quest5_segura_urina;" 
					+ " quest6_sente_perda ; quest7_corre ; quest7_pega_peso ; quest7_muito_apertada ; quest7_muita_risada ; quest7_muito_esforco ;"
					+ " quest7_caminhada ; quest7_sobe_escada ; quest7_usa_agua ; quest7_apertada_porta_banheiro ; quest8_conversou_profissional;"
					+ "quest9_sabe_tratamento ; quest10_medicamento_perda ; quest11_outro_medicamento ; quest11_quais ; quest12_quer_tratamento"); 
			
			 String selectQuery = "SELECT * FROM " + TABELA_QUESTIONARIOS;
			 
			    SQLiteDatabase db = this.getWritableDatabase();
			    Cursor cursor = db.rawQuery(selectQuery, null);
			    
			    if (cursor.moveToFirst()) 
			    {
			        do {
			        	String linha = cursor.getString(1) + ";" + cursor.getString(2) + ";" + cursor.getString(3) + ";" +
			        			cursor.getString(4) + ";" + cursor.getString(5) + ";" + cursor.getString(6) + ";" + cursor.getString(7) + ";" +
			        			cursor.getString(8) + ";" + cursor.getString(9) + ";" + cursor.getString(10) + ";" + cursor.getString(11) + ";" +
			        			cursor.getString(12) + ";" + cursor.getString(13) + ";" + cursor.getString(14) + ";" + cursor.getString(15) + ";" +
			        			cursor.getString(16) + ";" + cursor.getString(17) + ";" + cursor.getString(18) + ";" + cursor.getString(19) + ";" +
			        			cursor.getString(20) + ";" + cursor.getString(21) + ";" + cursor.getString(22) + ";" + cursor.getString(23);
			        	dados.add(linha);
			        } while (cursor.moveToNext());
			        
			    }
			    return dados;
		}
}