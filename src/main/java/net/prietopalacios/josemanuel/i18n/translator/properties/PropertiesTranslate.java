package net.prietopalacios.josemanuel.i18n.translator.properties;

import net.prietopalacios.josemanuel.i18n.translator.proxy.JavaProxy;

public interface PropertiesTranslate {

	public void translate(final String fileIn);
	public void translate(final String fileIn, final JavaProxy proxy);
	
}
