/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.springdemo.ui;

import edu.eci.arsw.springdemo.GrammarChecker;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 *
 * @author hcadavid
 */
public class Main {

    public static void main(String[] args) {
        String language = "";
        String text;
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce el texto que deseas verificar: ");
            text = scanner.nextLine();
            System.out.print("Introduce el idioma con el que desea verificar (espanol o ingles): ");
            language = scanner.nextLine();
            scanner.close();
            ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
            GrammarChecker gc=ac.getBean("grammarChecker",GrammarChecker.class);
            gc.setSpellChecker(language,ac);
            System.out.println(gc.check(text));
        }catch (NoSuchBeanDefinitionException e){
            System.out.print("El idioma " + language + " no existe");
        }


    }


}
