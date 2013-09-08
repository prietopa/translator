package net.prietopalacios.josemanuel.i18n.translator.http.spi;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class XuntaHttpTraductor extends AbstractHttpTranslator{
	
	private static final String URL = "http://www.xunta.es/tradutor/text.do";
	
	@Override
	protected void estableceUrl() {
		setUrl(URL);
	}

	@Override
	protected void rellenaFormulario(List<NameValuePair> formparams, String txtATraducir) {
		formparams.add(new BasicNameValuePair("DTS_PIVOT_LANGUAGE", "0"));
		formparams.add(new BasicNameValuePair("DTS_CREATE_GLOSSARY", "0"));
		formparams.add(new BasicNameValuePair("DTS_CREATE_CODING_LIST", "0"));
		formparams.add(new BasicNameValuePair("DTS_MARK_UNKNOWNS", "1"));
		formparams.add(new BasicNameValuePair("DTS_MARK_CONSTANTS", "0"));
		formparams.add(new BasicNameValuePair("DTS_MARK_COMPOUNDS", "0"));
		formparams.add(new BasicNameValuePair("DTS_MARK_MEMORY", "0"));
		formparams.add(new BasicNameValuePair("translationDirection", "SPANISH-GALICIAN"));
		formparams.add(new BasicNameValuePair("firsDialect", ""));
		formparams.add(new BasicNameValuePair("secondDialect", ""));
		formparams.add(new BasicNameValuePair("subjectArea", "(GV)"));
		formparams.add(new BasicNameValuePair("showAlternatives", "off"));
		formparams.add(new BasicNameValuePair("text", txtATraducir));
		formparams.add(new BasicNameValuePair("submit", "Traducir"));
	}

	@Override
	protected String recuperaTxtTraducido(Document doc) {
		Elements iframe = doc.getElementsByAttributeValue("name","RESULT");
		String textoTraducido = iframe.get(0).ownText();
		return limpia(textoTraducido);
	}
	
	private String limpia(String pp){
		pp = pp.replaceAll("&lt;br&gt;", "\n"); 				// <br>
		pp = pp.replaceAll("&lt;span class=unknown&gt;", ""); 	// <span class=unknown>
		pp = pp.replaceAll("&lt;/span&gt;", ""); 				// </span>
		return pp;
	}

}
