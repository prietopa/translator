package net.prietopalacios.josemanuel.i18n.translator.properties.spi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.List;

import net.prietopalacios.josemanuel.i18n.translator.http.HttpTranslator;
import net.prietopalacios.josemanuel.i18n.translator.http.spi.GoogleHttpTraductor;
import net.prietopalacios.josemanuel.i18n.translator.http.spi.XuntaHttpTraductor;
import net.prietopalacios.josemanuel.i18n.translator.proxy.JavaProxy;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GalicianTranslator extends AbtractPropertiesTranslate {

	private static final Logger log = LoggerFactory.getLogger(GalicianTranslator.class);
	protected static final String GL_POSTFIX = "_gl" + PROPERTIES_FILE_EXTENSION;
	private HttpTranslator translator;
	private boolean isGoogleTranslator;

	public GalicianTranslator(){
		setTranslator(new XuntaHttpTraductor());
		this.isGoogleTranslator = false;
	}

	public HttpTranslator getTranslator() {
		return translator;
	}

	private void setTranslator(HttpTranslator xunta) {
		this.translator = xunta;
	}

	@Override
	protected String getEndOfName() {
		return GL_POSTFIX;
	}

	public void translate(String fileIn) {
		try {
			List<String> list = capturaSoloElTextoATraducirEnBloques(2000, fileIn);
			String textoTraducido = "";

			textoTraducido = spanishToGalician(list);

			String outFilePathName = getOutFilePathName(fileIn);
			insertaTextoTraducido(textoTraducido, fileIn, outFilePathName);
			
			if(isGoogleTranslator){
				log.info("SE HA USADO EL TRADUCTOR DE GOOGLE.");
			}

		} catch (IOException e) {
			if(e instanceof UnknownHostException){
				log.error("Parece que no hay conexion a internet. ", e);
			}else if(e instanceof ClientProtocolException){
				log.error("Error en el protocolo HTTP. ", e);
			}else if(e instanceof UnsupportedEncodingException){
				log.error("Errores en la codificacion del texto. ", e);
			}else{
				log.error("Error general: ", e);
			}
		}
	}
	
	public void translate(String fileIn, JavaProxy proxy) {
		setProxy(proxy);
		translate(fileIn);
	}

	private String spanishToGalician(List<String> list) throws IOException {
		String textoTraducido = null;
		for(String txt: list){
			log.debug("Texto a traducir:\n {}", txt);
			String traducido = null;
			try{
				if(isProxyEnabled()){
					traducido = translator.translate(txt, getProxy());
				}else{
					traducido = translator.translate(txt);
				}
			}catch(UnknownHostException e){
				setTranslator(new GoogleHttpTraductor());
				traducido = translator.translate(txt);
				isGoogleTranslator = true;
			}
			log.debug("Texto traducido:\n {}", traducido);
			textoTraducido += traducido;
		}

		return textoTraducido;
	}

	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("no ha indicado el fichero");
			return;
		}
		if( ! args[0].startsWith("/")){
			System.out.println("Debe insertar la ruta absoluta del fichero");
			return;
		}
		if(args[0] == null) return;
		
		GalicianTranslator galician = new GalicianTranslator();
		if(args.length > 2){
//		String file = "/Users/jmprieto/dev/wkf/propertiesTranslator/src/test/resources/Language.properties";
			galician.translate(args[0]);
		}
		
		if(args.length > 2){
			JavaProxy proxy = null;
			try{
				proxy = new JavaProxy();
				proxy.setHost(args[1]);
				proxy.setPort(new Integer(args[2]));
				proxy.setUser(args[3]);
				proxy.setPass(args[4]);
			}catch (Exception e){
				System.out.println("Ha introducido de manera incorrecta los datos del proxy. args:\n" +
						"1) Ruta absoluta al fichero properties.\n" +
						"2) proxy host.\n" +
						"3) proxy port.\n" +
						"4) proxy username.\n" +
						"5) proxy password.");
				return;
			}

			try {
				galician.translate(args[0], proxy);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
