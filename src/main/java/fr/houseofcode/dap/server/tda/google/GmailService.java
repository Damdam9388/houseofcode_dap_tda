/**
 * 
 */
package fr.houseofcode.dap.server.tda.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @author dterro
 *
 */
public interface GmailService {
	Integer displayMessage(String userKey) throws GeneralSecurityException, IOException;
}
