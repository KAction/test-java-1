package calendar;
import calendar.Event;
import calendar.Calendar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class App {

	public static Event coffee(LocalDate date) {
		LocalTime morning = LocalTime.of(10, 00);
		return new Event(date, morning, morning.plusHours(1), "coffee");
	}

	public static Event milk(LocalDate date) {
		LocalTime evening = LocalTime.of(19, 00);
		return new Event(date, evening, evening.plusHours(1), "milk");
	}

	public static void main(String[] args) {
		LocalDate today = LocalDate.of(2019, 9, 23);
		LocalDate tomorrow = today.plusDays(1);
		LocalDate dayAfter = today.plusDays(2);
		LocalDate someday = today.plusDays(10);
		LocalTime noon = LocalTime.of(12, 00);

		Calendar c = new Calendar(() -> LocalDateTime.of(today, noon));
		c.addEvent(coffee(today));
		c.addEvent(milk(today));
		c.addEvent(coffee(tomorrow));
		c.addEvent(milk(tomorrow));
		c.addEvent(milk(dayAfter));
		c.addEvent(milk(someday));
		c.addEvent(new Event(today, noon, noon.plusHours(2), "You still can get it!"));
		// Oh, I run out of milk today.
		c.removeEvent(milk(today));

		System.out.printf("Current moment is: %s\n", LocalDateTime.of(today, noon));
		c.printRemainingAgendaForTheWeek();
	}
}
