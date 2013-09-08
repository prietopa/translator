package net.prietopalacios.josemanuel.i18n.translator;

import java.io.IOException;

import net.prietopalacios.josemanuel.i18n.translator.http.HttpTranslator;
import net.prietopalacios.josemanuel.i18n.translator.http.spi.XuntaHttpTraductor;
import net.prietopalacios.josemanuel.i18n.translator.proxy.JavaProxy;

public class TxtTranslator {

	public static void main(String[] args) {
		if(args[0] == null){
			System.out.println("No ha introducido texto.");
			return;
		}

		HttpTranslator translator = new XuntaHttpTraductor();

		if(args.length == 1){
			try {
				System.out.println(translator.translate(args[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}
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
						"1) cadena a traducir.\n" +
						"2) proxy host.\n" +
						"3) proxy port.\n" +
						"4) proxy username.\n" +
						"5) proxy password.");
				return;
			}

			try {
				System.out.println(translator.translate(args[0], proxy));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
