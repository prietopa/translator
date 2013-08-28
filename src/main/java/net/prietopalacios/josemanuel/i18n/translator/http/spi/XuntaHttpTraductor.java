package net.prietopalacios.josemanuel.i18n.translator.http.spi;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class XuntaHttpTraductor extends AbstractHttpTranslator{
	
	private static final String URL = "http://www.xunta.es/tradutor/text.do";
	
//	public String translate(String spanish) throws IOException {
//		log.info("URL: {}", URL);
//
//		HttpClient client = new DefaultHttpClient();
//		HttpPost post = new HttpPost(URL);
//
//		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//		formparams.add(new BasicNameValuePair("DTS_PIVOT_LANGUAGE", "0"));
//		formparams.add(new BasicNameValuePair("DTS_CREATE_GLOSSARY", "0"));
//		formparams.add(new BasicNameValuePair("DTS_CREATE_CODING_LIST", "0"));
//		formparams.add(new BasicNameValuePair("DTS_MARK_UNKNOWNS", "1"));
//		formparams.add(new BasicNameValuePair("DTS_MARK_CONSTANTS", "0"));
//		formparams.add(new BasicNameValuePair("DTS_MARK_COMPOUNDS", "0"));
//		formparams.add(new BasicNameValuePair("DTS_MARK_MEMORY", "0"));
//		formparams.add(new BasicNameValuePair("translationDirection", "SPANISH-GALICIAN"));
//		formparams.add(new BasicNameValuePair("firsDialect", ""));
//		formparams.add(new BasicNameValuePair("secondDialect", ""));
//		formparams.add(new BasicNameValuePair("subjectArea", "(GV)"));
//		formparams.add(new BasicNameValuePair("showAlternatives", "off"));
//		formparams.add(new BasicNameValuePair("text", spanish));
//		formparams.add(new BasicNameValuePair("submit", "Traducir"));
//
//		post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
//
//		HttpResponse response = client.execute(post);
//		log.info("Response Code: {}", response.getStatusLine().getStatusCode());
//
//		HttpEntity entity = response.getEntity();
//		String pageHTML = EntityUtils.toString(entity);
//		log.debug(pageHTML);
//
//		EntityUtils.consume(entity);
//		
//		Document doc = Jsoup.parse(pageHTML);
//		Elements iframe = doc.getElementsByAttributeValue("name","RESULT");
//		
//		String textoTraducido = iframe.get(0).ownText();
//		
//		return limpia(textoTraducido);
//	}
	
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
