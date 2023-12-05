package com.pedro.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FuncZip {

	public static void compactar(String pastaOrigem, String arquivoZip) {
		
		try {
			FileOutputStream fos = new FileOutputStream(arquivoZip);
			ZipOutputStream zos = new ZipOutputStream(fos);

			compactarPasta(zos, pastaOrigem, "");

			zos.close();
			fos.close(); 
			System.out.println("Compactação concluída.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void compactarPasta(ZipOutputStream zos, String pastaOrigem, String caminhoAtual)
			throws IOException {

		File pasta = new File(pastaOrigem);
		String[] arquivos = pasta.list();

		for (String arquivo : arquivos) {
			String caminhoCompleto = pastaOrigem + "/" + arquivo;

			if (new File(caminhoCompleto).isDirectory()) {
				compactarPasta(zos, caminhoCompleto, caminhoAtual + arquivo + "/");
				continue;
			}

			FileInputStream fis = new FileInputStream(caminhoCompleto);
			ZipEntry zipEntry = new ZipEntry(caminhoAtual + arquivo);
			zos.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}

			fis.close();
		}
	}
}