package calendar;

import calendar.Event;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.function.*;
import java.util.ArrayList;
import java.time.DayOfWeek;

public class Calendar {
	private Supplier<LocalDateTime> now;
	private HashMap<LocalDate, TreeSet<Event>> events;

	// Use real clock to get current time
	public Calendar() {
		this(() -> LocalDateTime.now());
	}

	public Calendar(Supplier<LocalDateTime> now) {
		this.now = now;
		this.events = new HashMap();
	}

	// Complexity: O(1) for hash map get/set, insertion into TreeSet
	// is O(n), where {n} is size of that TreeSet.
	//
	// So, in worst case, where all N events are in same day, it is
	// still O(ln(N)).
	public void addEvent(Event event) {
		TreeSet<Event> daily = this.events.get(event.date);
		if (daily == null) {
			daily = new TreeSet();
			daily.add(event);
			this.events.put(event.date, daily);
		} else {
			daily.add(event);
		}
	}

	// Same as addEvent: O(ln(N)) at worst.
	public void removeEvent(Event event) {
		TreeSet<Event> daily = this.events.get(event.date);
		if (daily != null) {
			daily.remove(event);
		}
	}

	// Return list of events, scheduled till the end of this week,
	// ordered by start moment, from most close till more distant.
	//
	// Complexity: O(N) at most: TreeSet already stores objects
	// sorted, so we do not have to sort it now.
	//
	// We already paid the price when we made insertion O(ln(n)).
	// It could be O(1), but then this would become O(n * ln(n)).
	public ArrayList<Event> remainingEventsForTheWeek() {
		ArrayList<Event> result = new ArrayList();

		LocalDateTime now = this.now.get();
		LocalDate today = now.toLocalDate();
		LocalTime moment = now.toLocalTime();
		boolean after = false;

		do {
			TreeSet<Event> todayEvents = this.events.get(today);
			if (todayEvents != null) {
				// Events are sorted
				for (Event e: todayEvents) {
					if (after) {
						result.add(e);
					} else if (e.startTime.compareTo(moment) >= 0) {
						result.add(e);
						after = true;
					}
				}
			}

		after = true;
		today = today.plusDays(1);
		// Week ends on Sunday.
		} while (today.getDayOfWeek() != DayOfWeek.MONDAY);

		return result;
	}

	public void printRemainingAgendaForTheWeek() {
		for (Event c: this.remainingEventsForTheWeek()) {
			System.out.println(c);
		}
	}
}
