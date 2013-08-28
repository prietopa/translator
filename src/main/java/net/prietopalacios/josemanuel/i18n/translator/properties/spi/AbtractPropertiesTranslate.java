package net.prietopalacios.josemanuel.i18n.translator.properties.spi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.prietopalacios.josemanuel.i18n.translator.properties.PropertiesTranslate;

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbtractPropertiesTranslate implements PropertiesTranslate {

	private static final Logger log = LoggerFactory.getLogger(AbtractPropertiesTranslate.class);

	private static final int bytesToRead = 4096;
	protected static final String UTF8 = org.apache.commons.lang.CharEncoding.UTF_8;
	protected static final String EQUAL = "=";
	protected static final String NEW_LINE = "\n";
	protected static final String PROPERTIES = "properties";
	protected static final String DOT = ".";
	protected static final String PROPERTIES_FILE_EXTENSION = DOT + PROPERTIES;

	private String encoding;

	protected abstract String getEndOfName();

	private String getEncoding(){
		return this.encoding;
	}

	private void setEncoding(String filePath) throws FileNotFoundException, IOException {
		if(this.encoding != null) return;

		byte[] buf = new byte[bytesToRead];
		FileInputStream fis = new FileInputStream(filePath);

		UniversalDetector detector = new UniversalDetector(null);
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		fis.close();
		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		detector.reset();
		log.debug("ENCODING: {}", encoding);

		this.encoding = encoding;
	}

	private BufferedReader getBufferedReader(String filePath) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		FileInputStream fis = new FileInputStream(filePath);
		setEncoding(filePath);
		return new BufferedReader(new InputStreamReader(fis, getEncoding()));
	}

	protected String capturaSoloElTextoATraducir(String filePath) throws IOException{
		BufferedReader br = getBufferedReader(filePath);

		StringBuffer buffer = new StringBuffer();
		while (true) {
			String line = br.readLine();
			if(line == null) break;
			if(line.contains(EQUAL)){
				String[] equals = line.split(EQUAL);
				buffer.append(new String(equals[1].getBytes(), UTF8) + NEW_LINE);
			}
		}

		br.close();
		log.debug("FILE: \n {}" , buffer);

		return buffer.toString();
	}

	protected List<String> capturaSoloElTextoATraducirEnBloques(final int maxChar, final String filePath) throws IOException{
		BufferedReader br = getBufferedReader(filePath);

		String bloque = "";
		int cont = 0;
		List<String> list = new ArrayList<String>();
		while (true) {
			String line = br.readLine();
			if(line == null) break;
			if(line.contains(EQUAL)){
				String[] text = line.split(EQUAL);
				String textToTranslate = new String(text[1].getBytes(), UTF8);
				cont += textToTranslate.length();
				if(cont > maxChar){
					list.add(bloque);
					bloque = textToTranslate + NEW_LINE;
					cont = 0;
					cont += textToTranslate.length();
				}else{
					bloque += textToTranslate + NEW_LINE;
				}
			}
		}

		br.close();

		return list;
	}

	/**
	 * CUIDADO: el traductor de la Xunta hay cosas que no las entiende, como |\ `... o el codigo html
	 *
	 * @throws IOException
	 */
	protected void insertaTextoTraducido(String textoTraducido, final String inFilePath, final String outFilePath) throws IOException{
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFilePath), getEncoding()));

		BufferedReader original = getBufferedReader(inFilePath);

		int cont = 0;
		while (true) {
			String lineaOriginal = original.readLine();
			if(lineaOriginal == null) break;
			if(lineaOriginal.contains(EQUAL)){
				String lineaTraducida = dameLineaNumero(cont++, textoTraducido);
				String[] lineasOriginales = lineaOriginal.split(EQUAL);
				bw.write(lineasOriginales[0] + insertaTabComoEnOriginal(lineaOriginal) + "= ");
				if(laLineaContieneExcepciones(lineasOriginales[1])){
					bw.write(lineasOriginales[1] + NEW_LINE);
				}else{
					bw.write(lineaTraducida + NEW_LINE);
				}
			}else{
				bw.write(lineaOriginal + NEW_LINE);
			}
		}

		bw.close();
	}

	/**
	 * A las lineas que contienen XXX o YYY no las vamos a tratar, devolvemos true.
	 * 
	 * @param linea
	 * @return
	 */
	private boolean laLineaContieneExcepciones(String linea){
		return linea.contains(" <a id");
	}

	protected String getOutFilePathName(String inFilePathName){
		int x = inFilePathName.indexOf(PROPERTIES_FILE_EXTENSION);
		int y = inFilePathName.lastIndexOf("/");
		String path = inFilePathName.substring(0, y);
		return path + "/" + inFilePathName.substring(y+1, x) + getEndOfName();
	}

	private String dameLineaNumero(int numeroLinea, String textoTraducido) throws UnsupportedEncodingException{
		String linea = "";
		for (int i = 0; i <= numeroLinea; i++) {
			if(textoTraducido.length() > 2){
				int intprimeraLinea = textoTraducido.indexOf(NEW_LINE);
				if(intprimeraLinea > 0){
					linea = textoTraducido.substring(0, intprimeraLinea); // TODO
					textoTraducido = textoTraducido.substring(intprimeraLinea+1, textoTraducido.length());
				}
			}
		}

		return linea;
	}

	private String insertaTabComoEnOriginal(String lineaOriginal) {
		String xtab = "";
		for (int i = 0; i < cuentaTabuladores(lineaOriginal); i++) {
			xtab += "\t";
		}
		return xtab;
	}

	public int cuentaTabuladores(String linea){
		int cont = 0;
		char[] array = linea.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if(array[i] == '\\' && array[i + 1] == 't'){
				cont++;
			}
		}
		return cont;
	}

}
