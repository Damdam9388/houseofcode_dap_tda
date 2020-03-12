/**
 * 
 */
package fr.houseofcode.dap.server.tda;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.tda.google.GmailServiceImp;

/**
 * @author damie
 *
 */
@RestController
public class LabelsController {

	@Autowired
	private GmailServiceImp service;

	@RequestMapping("/labels")
	public String displayListLabels(@RequestParam(value = "userKey", required = true) final String userKey)
			throws IOException, GeneralSecurityException {
		return service.displayLabels(userKey);
	}

}
