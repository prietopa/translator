package net.prietopalacios.josemanuel.i18n.translator.http.spi;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.prietopalacios.josemanuel.i18n.translator.http.HttpTranslator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHttpTranslator implements HttpTranslator{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractHttpTranslator.class);
	private String url;
	
	protected abstract void estableceUrl();
	protected abstract void rellenaFormulario(List<NameValuePair> formparams, String txtATraducir);
	protected abstract String recuperaTxtTraducido(Document doc);
	
	protected void setUrl(String url){
		this.url = url;
	}
	
	protected String getUrl(){
		return this.url;
	}
	
	public String translate(String spanish) throws IOException {
		estableceUrl();
		if(url == null) {
			throw new UnknownHostException("La url es nula");
		}
		log.info("URL: {}", getUrl());

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(getUrl());

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		rellenaFormulario(formparams, spanish);
		post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
		
		HttpResponse response = client.execute(post);
		log.info("Response Code: {}", response.getStatusLine().getStatusCode());

		HttpEntity entity = response.getEntity();
		String pageHTML = EntityUtils.toString(entity);
		log.debug(pageHTML);
		EntityUtils.consume(entity);
		
		Document doc = Jsoup.parse(pageHTML);
		return recuperaTxtTraducido(doc);
	}
	
}
