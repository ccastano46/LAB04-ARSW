package edu.eci.arsw.springdemo;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class GrammarChecker {

	SpellChecker sc;


	/**
	 * Setter del SpellChecker
	 * @pre las unicas posibles opciones de idioma son ingles o espanol.
	 * @param lenguage, idioma al cual se va a ajustar el GrammarChecker
	 * @param applicationContext, ClassPath que contiene las configuraciones del programa.
	 * @throws NoSuchBeanDefinitionException, si no se encuentra el idioma al cual se quiere realizar el chequeo
	 */
	public void setSpellChecker(String lenguage,ApplicationContext applicationContext) throws NoSuchBeanDefinitionException {
		this.sc = applicationContext.getBean(lenguage.toLowerCase(),SpellChecker.class);
	}


	public String check(String text){
		StringBuilder sb=new StringBuilder();
		sb.append("Spell checking output:"+sc.checkSpell(text));
		sb.append("Plagiarism checking output: Not available yet");
		return sb.toString();
	}
}

