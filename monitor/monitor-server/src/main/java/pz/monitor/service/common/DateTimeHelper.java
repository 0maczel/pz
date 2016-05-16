package pz.monitor.service.common;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class DateTimeHelper {
	public static Timestamp toTimestamp(ZonedDateTime dateTime) {
		if (dateTime == null)
			return null;
		return Timestamp.from(dateTime.toInstant());
	}
}
