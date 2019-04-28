package services;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class DateService {

	public LocalDateTime getTodaysDate() {
		LocalDateTime todaysDate = LocalDateTime.now();
		return todaysDate;
	}
}
