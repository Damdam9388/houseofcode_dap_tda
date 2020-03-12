package fr.houseofcode.dap.server.tda.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

/**
 * @author adminHOC
 */
@Service
public class GmailServiceImp implements GmailService {

	/**
	 *  Logger
	 */
	private static final Logger LOG = LogManager.getLogger();

	/**
	 *.
	 */
	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";

	/**
	 *
	 */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/**
	 * contains the unique instance of GmailService class.
	 */
	private static GmailServiceImp instance;

	/**
	 * is an empty private constructor (cf. Singleton pattern).
	 */
	private GmailServiceImp() {
	}

	/**
	 * create the unique new instance of GmailService(Cf singleton).
	 * @return the unique instance of GmailService(Cf singleton)
	 */
	public static GmailServiceImp getInstance() {
		if (instance == null) {
			instance = new GmailServiceImp();
		}
		return instance;
	}

	/**
	 * Gmail Service.
	 * @return the value contained in the Gmail Service
	 * @throws IOException ...
	 * @throws GeneralSecurityException ...
	 */
	private Gmail getService(String userKey) throws IOException, GeneralSecurityException {

		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, Utils.getCredentials(httpTransport, userKey))
				.setApplicationName(APPLICATION_NAME).build();
		return service;
	}

	/**
	 * display the labels.
	 * @return the list of labels of the user's account
	 * @throws IOException ...
	 * @throws GeneralSecurityException ...
	 */
	public String displayLabels(String userKey) throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		LOG.info("Get the Labels of the Gmail account");
		String user = "me";
		String allLabels = "";
		// Print the labels in the user's account.
		ListLabelsResponse listResponse = getService(userKey).users().labels().list(user).execute();
		List<Label> labels = listResponse.getLabels();
		if (labels.isEmpty()) {
			allLabels = "Aucun Label trouvé.";
		} else {
			allLabels = "labels: ";
			for (Label label : labels) {
				allLabels = allLabels + "\n" + label.getName();
			}
		}
		return allLabels;
	}

	/**
	 * display the number of unread messages.
	 * @return the number of unread messages on the user's account.
	 * @throws GeneralSecurityException ...
	 * @throws IOException ...
	 */
	public Integer displayMessage(String userKey) throws GeneralSecurityException, IOException {
		Integer result = 0;
		String user = "me";
		// Print the list of Unread Message
		ListMessagesResponse allMessages = getService(userKey).users().messages().list(user).setQ("in:inbox is:unread")
				.execute();

		List<Message> messages = allMessages.getMessages();

		if (messages != null) {
			if (!messages.isEmpty()) {

				result = messages.size();

			}

		}

		return result;
	}
}
