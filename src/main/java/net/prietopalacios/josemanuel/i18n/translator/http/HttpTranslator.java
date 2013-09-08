package net.prietopalacios.josemanuel.i18n.translator.http;

import java.io.IOException;

import net.prietopalacios.josemanuel.i18n.translator.proxy.JavaProxy;

public interface HttpTranslator {

	public String translate(String txt) throws IOException;
	public String translate(String txt, JavaProxy proxy) throws IOException;
	
}
