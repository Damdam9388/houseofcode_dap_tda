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
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 *
 * @author adminHOC
 *
 */
@Service
public class CalendarService {

	private static final Logger LOG = LogManager.getLogger();

	/** The internal application name. */
	private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
	/** the default JsonFactory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/**
	 *
	 * @return ...
	 * @throws IOException ...
	 * @throws GeneralSecurityException ...
	 */
	private Calendar getService(String userKey) throws IOException, GeneralSecurityException {
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY,
				Utils.getCredentials(httpTransport, userKey)).setApplicationName(APPLICATION_NAME).build();
		return service;
	}

	/**
	 *
	 * @return ...
	 * @throws IOException ...
	 * @throws GeneralSecurityException ...
	 */
	public String displayNextEvents(String userKey) throws IOException, GeneralSecurityException {

		LOG.debug("retour ecran d'accueil");

		// Build a new authorized API client service.
		String nextEvent = "";

		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = getService(userKey).events().list("primary").setMaxResults(1).setTimeMin(now)
				.setOrderBy("startTime").setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			//System.out.println("No upcoming events found.");
			nextEvent = "Pas d'évènement prévu prochainement.";
		} else {
			//System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				//System.out.printf("%s (%s)\n", event.getSummary(), start);
				nextEvent = "Votre prochain évènement est : " + event.getSummary() + start;
			}
		}
		return nextEvent;
	}
}
