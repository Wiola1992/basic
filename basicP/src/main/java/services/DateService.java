package services;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class DateService {

	public Date getTodaysDate() {
		Calendar calendar = Calendar.getInstance();
		Date todaysDate = new Date(calendar.getTimeInMillis());
	return todaysDate;
	}
}
