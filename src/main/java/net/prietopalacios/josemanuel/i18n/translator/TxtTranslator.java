package net.prietopalacios.josemanuel.i18n.translator;

import java.io.IOException;

import net.prietopalacios.josemanuel.i18n.translator.http.HttpTranslator;
import net.prietopalacios.josemanuel.i18n.translator.http.spi.XuntaHttpTraductor;

public class TxtTranslator {

	public static void main(String[] args) {
		if(args[0] == null){
			System.out.println("No ha introducido texto.");
			return;
		}
		HttpTranslator translator = new XuntaHttpTraductor();
		try {
			System.out.println(translator.translate(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
