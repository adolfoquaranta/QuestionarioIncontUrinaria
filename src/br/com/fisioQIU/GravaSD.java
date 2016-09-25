package br.com.fisioQIU;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
//import java.util.Iterator;
//import java.util.List;
import android.os.Environment;

public class GravaSD{
	
	public static boolean grava(String nome_arquivo, Vector<String> conteudo)
	{
		@SuppressWarnings("unused")
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		if(!mExternalStorageWriteable)
		{
			return false;
		}
		File file = new File(Environment.getExternalStorageDirectory(), nome_arquivo);
		try {
			FileWriter aux = new FileWriter(file);
			BufferedWriter buffer = new BufferedWriter(aux);
			for(int i = 0 ; i < conteudo.size(); ++i)
			{
				buffer.write(conteudo.elementAt(i));
				buffer.newLine();
			}
			buffer.close();
			aux.close();
		    return true;
		} catch (FileNotFoundException e) {
		    // handle exception
			return false;
		} catch (IOException e) {
		    // handle exception
			return false;
		}

	}
	/*public static String lista_to_string(List<QuestionarioIU> A)
	{
		String dados = null;
		
		Iterator<QuestionarioIU> contador = A.iterator();
		while(contador.hasNext())
		{
			QuestionarioIU aux = contador.next();
			dados += aux.quest1_idade + ";" + aux.quest2_menopausa + ";" + aux.quest2_tempo + ";"
			+ aux.quest3_cirurgia + ";" + aux.quest3_tempo + ";" + aux.quest4_perda_urina + ";" 
			+ aux.quest5_segura_urina + ";" + aux.quest6_sente_perda + ";" + aux.quest7_corre + ";" 
			+ aux.quest7_pega_peso + ";" + aux.quest7_muito_apertada + ";" + aux.quest7_muita_risada + ";" 
			+ aux.quest7_muito_esforco + ";"	+ aux.quest7_caminhada + ";" + aux.quest7_sobe_escada + ";" 
			+ aux.quest7_usa_agua + ";" + aux.quest7_apertada_porta_banheiro + ";" 
			+ aux.quest8_conversou_profissional + ";" + aux.quest9_sabe_tratamento + ";" 
			+ aux.quest10_medicamento_perda + ";" + aux.quest11_outro_medicamento + ";" 
			+ aux.quest11_quais + ";" + aux.quest12_quer_tratamento + "/n";
		}
		return dados;
	}
	*/
	
	public GravaSD()
	{}
}
