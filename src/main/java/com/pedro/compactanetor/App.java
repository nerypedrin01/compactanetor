package com.pedro.compactanetor;

import java.io.File;
import java.io.IOException;

import com.pedro.util.Compactador;
import com.pedro.util.EnviaEmail;
import com.pedro.util.Funcoes;

public class App {

	private static String pathPadrao = "D:\\10 OUTUBRO";
	private static String pathZip = "D:\\10 OUTUBRO.zip";

	public static void main(String[] args) {

		try {

			Funcoes.run(pathPadrao);

			System.out.println("-------------Iniciando Compactação de arquivos--------------");
			Compactador.compactar(pathPadrao, pathZip);

		} catch (IOException e) {
			e.printStackTrace(); 
		}
		EnviaEmail.send(new File(pathZip), "felipejhony@ljr.eti.br");
	}
}
