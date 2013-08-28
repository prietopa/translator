package net.prietopalacios.josemanuel.i18n.properties.translator.spi;

import net.prietopalacios.josemanuel.i18n.translator.properties.spi.GalicianTranslator;

import org.junit.Ignore;
import org.junit.Test;

public class GalicianTranslatorTest {

	@Test
	@Ignore
	public void testTranslate() {
		GalicianTranslator galician = new GalicianTranslator();
		galician.translate("src/test/resources/Language.properties");
	}
	
}
