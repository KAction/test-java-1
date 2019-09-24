package calendar;
/* Task said nothing about timezones, so let's pretend that this major
 * source of pain does not exist
 */
import java.time.LocalTime;
import java.time.LocalDate;
import java.lang.Comparable;

public class Event implements Comparable {
	public final LocalDate date;
	public final LocalTime startTime;
	public final LocalTime endTime;
	public final String description;
	public Event(LocalDate date, LocalTime startTime, LocalTime endTime, String description) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
	};

	public String toString() {
		return String.format("%s %s (%s - %s): %s",
		    this.date.getDayOfWeek(), this.date, this.startTime,
		    this.endTime, this.description);
	}

	@Override
	public int compareTo(Object o) {
		Event other = (Event) o;
		int r;

		r = this.date.compareTo(other.date);
		if (r != 0) {
			return r;
		}

		r = this.startTime.compareTo(other.startTime);
		if (r != 0) {
			return r;
		}

		r = this.endTime.compareTo(other.endTime);
		if (r != 0) {
			return r;
		}

		return this.description.compareTo(other.description);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (this.getClass() != other.getClass()) {
			return false;
		}

		Event e = (Event) other;

		return this.compareTo(e) == 0;
	}

	@Override
	public int hashCode() {
		return this.date.hashCode()
			+ this.startTime.hashCode()
			+ this.endTime.hashCode()
			+ this.description.hashCode();
	}

};
