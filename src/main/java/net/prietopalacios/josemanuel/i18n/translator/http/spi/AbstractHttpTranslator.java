package net.prietopalacios.josemanuel.i18n.translator.http.spi;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.prietopalacios.josemanuel.i18n.translator.http.HttpTranslator;
import net.prietopalacios.josemanuel.i18n.translator.proxy.JavaProxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHttpTranslator implements HttpTranslator{

	private static final Logger log = LoggerFactory.getLogger(AbstractHttpTranslator.class);
	private String url;
	private JavaProxy proxy;

	protected abstract void estableceUrl();
	protected abstract void rellenaFormulario(List<NameValuePair> formparams, String txtATraducir);
	protected abstract String recuperaTxtTraducido(Document doc);

	protected boolean isProxyEnabled(){
		if(proxy == null) return false;
		return true;
	}

	protected void setProxy(JavaProxy proxy){
		this.proxy = proxy;
	}

	protected void setUrl(String url){
		this.url = url;
	}

	protected String getUrl(){
		return this.url;
	}

	@Override
	public String translate(String spanish) throws IOException {
		estableceUrl();
		if(url == null) {
			throw new UnknownHostException("La url es nula");
		}
		log.info("URL: {}", getUrl());

		HttpClient client = null;
		String pageHTML = null;
		try{
			if(isProxyEnabled()){
				client = addProxyToHttpClient();
			}else{
				client = new DefaultHttpClient();
			}
			HttpPost post = new HttpPost(getUrl());

			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			rellenaFormulario(formparams, spanish);
			post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));

			HttpResponse response = client.execute(post);
			log.info("Response Code: {}", response.getStatusLine().getStatusCode());

			HttpEntity entity = response.getEntity();
			pageHTML = EntityUtils.toString(entity);
			log.debug(pageHTML);
			EntityUtils.consume(entity);
		}finally{
			client.getConnectionManager().shutdown();
		}

		if(pageHTML == null)  throw new IOException("El resultado es null");
		
		Document doc = Jsoup.parse(pageHTML);
		return recuperaTxtTraducido(doc);
	}

	@Override
	public String translate(String txt, JavaProxy proxy) throws IOException {
		setProxy(proxy);
		return translate(txt);
	}

	private HttpClient addProxyToHttpClient(){
		DefaultHttpClient client = new DefaultHttpClient();
		client.getCredentialsProvider().setCredentials(
				new AuthScope(proxy.getHost(), proxy.getPort()),
				new UsernamePasswordCredentials(proxy.getUser(), proxy.getPass()));

		HttpHost httpProxy = new HttpHost(proxy.getHost(), proxy.getPort());
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpProxy);

		return client;
	}

}
