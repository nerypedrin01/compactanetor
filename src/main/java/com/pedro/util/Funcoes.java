package com.pedro.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Funcoes {

	private static Map<String, List<File>> filesMap = new LinkedHashMap<>();

	private static boolean criaMap = true;

	public static void separaComprovantesNaRaiz(String path) throws IOException {

		if (criaMap) {
			filesMap.put("302", new ArrayList<>());
			filesMap.put("105", new ArrayList<>());
			filesMap.put("GUARA", new ArrayList<>()); 
			filesMap.put("PLANALTINA", new ArrayList<>());
			criaMap = false;
		}

		File files = new File(path);

		for (File file : files.listFiles()) {

			if (file.isDirectory())
				separaComprovantesNaRaiz(file.getAbsolutePath());

			if (file.getName().toUpperCase().contains("COMP") && file.getAbsolutePath().toUpperCase().contains("302")) {
				System.out.println(file.getName());
				filesMap.get("302").add(file);
			} else if (file.getName().toUpperCase().contains("COMP")
					&& file.getAbsolutePath().toUpperCase().contains("105")) {
				System.out.println(file.getName());
				filesMap.get("105").add(file);

			} else if (file.getName().toUpperCase().contains("COMP")
					&& file.getAbsolutePath().toUpperCase().contains("GUARA")) {
				System.out.println(file.getName());
				filesMap.get("GUARA").add(file);
			} else if (file.getName().toUpperCase().contains("COMP")
					&& file.getAbsolutePath().toUpperCase().contains("PLANALTINA")) {
				System.out.println(file.getName());
				filesMap.get("PLANALTINA").add(file);

			}
		}

	}

	public static void criaPasta(String path) throws IOException {

		System.out.println("Criando pastas");
		File files = new File(path);

		for (File file : files.listFiles()) {

			for (int i = 1; i <= 31; i++) {

				if (i > 9) {
					File diretorio = new File(file.getAbsoluteFile() + "//" + i);
					diretorio.mkdir();
				} else {
					File diretorio = new File(file.getAbsoluteFile() + "//0" + i);
					diretorio.mkdir();
				}
			}
		}

	}

	public static void moveTo(String path) throws IOException {

		for (File file : new File(path).listFiles()) {

			System.out.println(file.getName());

			for (File fileMove : filesMap.get(file.getName())) {

				if (fileMove
						.renameTo(new File(new File(file.getAbsolutePath() + "//" + fileMove.getName().substring(0, 2)),
								fileMove.getName())))
					System.out.println("Arquivo movido com sucesso!\n" + fileMove.getAbsolutePath());

			}

		}

	}

	public static void deletaPastaVazias(String paString) {

		for (File file : new File(paString).listFiles()) {

			if (file.isDirectory() && file.listFiles() != null && file.listFiles().length > 0)
				deletaPastaVazias(file.getAbsolutePath());

			else if (file.isDirectory() && (file.listFiles() == null || file.listFiles().length <= 0)) {
				System.out.println("Deletando Folder: " + file.getPath());
				file.delete();
			}
		}
	}

	public static void deletaArqAgendamento(String paString) {

		for (File file : new File(paString).listFiles()) {

			if (file.isDirectory())
				deletaArqAgendamento(file.getAbsolutePath());

			if (file.isFile() && !file.getName().toUpperCase().contains("COMP"))
				file.delete();

		}
	}

	public static void run(String path) throws IOException {


		System.out.println("--------Iniciando separação de arquivos-------");
		separaComprovantesNaRaiz(path);
		System.out.println("--------Criando pastas -------");
		criaPasta(path);
		System.out.println("--------Movendo arquivos para sua pasta de destino-------");
		moveTo(path);
		System.out.println("--------Deletando arquivos de agendamento-------");
		deletaArqAgendamento(path);
		System.out.println("--------Deletando todas as pastas vazias-------");
		deletaPastaVazias(path);

	}

}
