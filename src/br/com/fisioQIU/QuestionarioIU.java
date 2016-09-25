package br.com.fisioQIU;

public class QuestionarioIU {
	
	public Integer quest1_idade;
	
	public Integer quest2_menopausa;
	public Integer quest2_tempo;
	
	public Integer quest3_cirurgia;
	public Integer quest3_tempo;
	
	public Integer quest4_perda_urina;
	
	public Integer quest5_segura_urina;
	
	public Integer quest6_sente_perda;
	
	public Integer quest7_corre;
	public Integer quest7_pega_peso;
	public Integer quest7_muito_apertada;
	public Integer quest7_muita_risada;
	public Integer quest7_muito_esforco;
	public Integer quest7_caminhada;
	public Integer quest7_sobe_escada;
	public Integer quest7_usa_agua;
	public Integer quest7_apertada_porta_banheiro;
	
	public Integer quest8_conversou_profissional;
	
	public Integer quest9_sabe_tratamento;

	public Integer quest10_medicamento_perda;
	
	public Integer quest11_outro_medicamento;
	public String quest11_quais;
	
	public Integer quest12_quer_tratamento;
	
	public QuestionarioIU()
	{
		
	}
	
	final public void resetQuestionario()
	{
		quest1_idade = -1;
		
		quest2_menopausa = -1;
		quest2_tempo = -1;
		
		quest3_cirurgia = -1;
		quest3_tempo = -1;
		
		quest4_perda_urina = -1;
		
		quest5_segura_urina = -1;
		
		quest6_sente_perda = -1;
		
		quest7_corre = -1;
		quest7_pega_peso = -1;
		quest7_muito_apertada = -1;
		quest7_muita_risada = -1;
		quest7_muito_esforco = -1;
		quest7_caminhada = -1;
		quest7_sobe_escada = -1;
		quest7_usa_agua = -1;
		quest7_apertada_porta_banheiro = -1;
		
		quest8_conversou_profissional = -1;
		
		quest9_sabe_tratamento = -1;

		quest10_medicamento_perda = -1;
		
		quest11_outro_medicamento = -1;		
		@SuppressWarnings("unused")
		String quest11_quais = "-1";
		
		quest12_quer_tratamento = -1;		
	}
	
	final public void resetQuest1()
	{
		quest1_idade = -1;
	}
	final public void resetQuest2()
	{
		quest2_menopausa = -1;
		quest2_tempo = -1;
	}
	final public void resetQuest3()
	{
		quest3_cirurgia = -1;
		quest3_tempo = -1;
	}
	final public void resetQuest4()
	{
		quest4_perda_urina = -1;
	}
	final public void resetQuest5()
	{
		quest5_segura_urina = -1;
	}
	final public void resetQuest6()
	{
		quest6_sente_perda = -1;
	}
	final public void resetQuest7()
	{
		quest7_corre = -1;
		quest7_pega_peso = -1;
		quest7_muito_apertada = -1;
		quest7_muita_risada = -1;
		quest7_muito_esforco = -1;
		quest7_caminhada = -1;
		quest7_sobe_escada = -1;
		quest7_usa_agua = -1;
		quest7_apertada_porta_banheiro = -1;
	}
	final public void resetQuest8()
	{
		quest8_conversou_profissional = -1;
	}
	final public void resetQuest9()
	{
		quest9_sabe_tratamento = -1;
	}
	final public void resetQuest10()
	{
		quest10_medicamento_perda = -1;
	}
	final public void resetQuest11()
	{
		quest11_outro_medicamento = -1;
		@SuppressWarnings("unused")
		String quest11_quais = "-1";
	}
	final public void resetQuest12()
	{
		quest12_quer_tratamento = -1;	
	}

}
