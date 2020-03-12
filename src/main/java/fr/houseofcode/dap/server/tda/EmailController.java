/**
 *
 */
package fr.houseofcode.dap.server.tda;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.tda.google.GmailServiceImp;

/**
 *
 * @author damie
 *
 */

@RestController
public class EmailController {
	
	private static final Logger LOG = LogManager.getLogger();
	
	@Autowired
	private GmailServiceImp service;

	@RequestMapping("/email/nbUnread")
	public Integer displayNbUnreadEmail(@RequestParam(value = "userKey", required = true) final String userKey)
			throws IOException, GeneralSecurityException {
		LOG.info("Recuperation du nombre d'email pour l'utilisateur" + userKey);
		return service.displayMessage(userKey);
	}
}