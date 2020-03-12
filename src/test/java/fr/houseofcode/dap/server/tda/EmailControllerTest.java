/**
 * 
 */
package fr.houseofcode.dap.server.tda;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author dterro
 *
 */
public class EmailControllerTest {

	@Test
	public void testDisplayNbUnreadEmail() throws IOException, GeneralSecurityException {
		//Init data
		EmailController ec = new EmailController();
		//Appel de la méthode
		Integer result = ec.displayNbUnreadEmail("dam");
		//Contrôle de l'éxécution	
		Integer expectedNbEmail = 25;
		Assert.assertNotNull("Nombre d'email non présent", result);
		Assert.assertEquals(expectedNbEmail, result);
	}
}
