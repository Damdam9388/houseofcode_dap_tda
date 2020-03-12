package fr.houseofcode.dap.server.tda.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

public class Utils {
	/**
	 * .
	 */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	/**
	 * .
	 */
	private static final String TOKENS_DIRECTORY_PATH = "D:/Users/dterro/Dap/tokens";

	/**
	 * Global instance of the scopes required by this quickstart.
	 * If modifying these scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = new ArrayList<String>();
	/**
	 *...
	 */
	private static final String CREDENTIALS_FILE_PATH = "D:/Users/dterro/Dap/tokens/credentials.json";

	/**
	 * Creates an authorized Credential object.
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userKey) throws IOException {
		//		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		//		return new AuthorizationCodeInstalledApp(getFlow(HTTP_TRANSPORT), receiver).authorize(userKey);

		GoogleAuthorizationCodeFlow flow = getFlow(HTTP_TRANSPORT);
		return flow.loadCredential(userKey);
	}

	public static GoogleAuthorizationCodeFlow getFlow(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		SCOPES.clear();
		SCOPES.add(CalendarScopes.CALENDAR_READONLY);
		SCOPES.add(GmailScopes.GMAIL_READONLY);
		//		InputStream in = CalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		//		if (in == null) {
		//			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		//		}
		File appClientSecretFile = new File(CREDENTIALS_FILE_PATH);

		InputStreamReader in = new InputStreamReader(new FileInputStream(appClientSecretFile),
				Charset.forName("UTF-8"));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();

		return flow;
	}

}
