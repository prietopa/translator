package net.prietopalacios.josemanuel.i18n.translator.http.spi;

import java.util.List;

import org.apache.http.NameValuePair;
import org.jsoup.nodes.Document;

public class GoogleHttpTraductor extends AbstractHttpTranslator{
	
	private static final String URL = "http://www.google.es/translate";

	@Override
	protected void estableceUrl() {
		setUrl(URL);
	}

	@Override
	protected void rellenaFormulario(List<NameValuePair> formparams,
			String txtATraducir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String recuperaTxtTraducido(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
