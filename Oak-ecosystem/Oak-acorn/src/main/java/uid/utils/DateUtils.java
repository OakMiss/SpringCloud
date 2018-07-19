package uid.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtils provides date formatting, parsing
 * Created by Oak on 2018/7/19.
 * Description:
 */
public class DateUtils {

	private DateUtils() {
		super();
	}

	/**
	 * Patterns
	 */
	public static final String DAY_PATTERN = "yyyy-MM-dd";
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Parse date by 'yyyy-MM-dd' pattern
	 *
	 * @param str
	 * @return
	 */
	public static Date parseByDayPattern(String str) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_PATTERN);
			return dateFormat.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Format date by 'yyyy-MM-dd HH:mm:ss' pattern
	 *
	 * @param date
	 * @return
	 */
	public static String formatByDateTimePattern(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
		return dateFormat.format(date);
	}

}
