package com.ubs.opsit.interviews;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements Interface TimeConverter to convert time as per berlin
 * clock. The berlin clock is displayed using following rules: The clock is made
 * up of 5 rows. On the very top of the clock is a lamp that blinks to show the
 * seconds. It turns on for one second, then off for one second, and so on. The
 * next 2 rows represent the hours. The upper most of these rows represents 5
 * hour blocks and is made up of 4 red lamps. The second row represents 1 hour
 * blocks and is also made up of 4 red lamps. The final two rows represent the
 * minutes. The upper most row represents 5 minute blocks and is made up of 11
 * lamps- every 3rd lamp red but the rest yellow. The second row represents 1
 * minute blocks and is made up of 4 yellow lamps.
 * 
 */

public class TimerConverterImpl implements TimeConverter {
	private static final Logger LOG = LoggerFactory
			.getLogger(TimerConverterImpl.class);

	/**
	 * Returns time as displayed in berlin-clock as string. Input to the method
	 * is the time in HH:MM:SS format.
	 * 
	 * @param time
	 *            input time in HH:MM:SS format
	 * @return time as per berlin clock format
	 */
	public String convertTime(String time) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Trying to convert input time: " + time);
		}

		List<Integer> parts = new ArrayList<Integer>();
		// Split time as HH, MM and SS.
		for (String part : time.split(":")) {
			parts.add(Integer.parseInt(part));
		}
		StringBuilder sb = new StringBuilder();
		sb.append(getSeconds(parts.get(2)));
		sb.append("\r\n");
		sb.append(getTopHours(parts.get(0)));
		sb.append("\r\n");
		sb.append(getBottomHours(parts.get(0)));
		sb.append("\r\n");
		sb.append(getTopMinutes(parts.get(1)));
		sb.append("\r\n");
		sb.append(getBottomMinutes(parts.get(1)));
		if (LOG.isDebugEnabled()) {
			LOG.debug("Converted Berlin clock time: " + sb.toString());
		}
		return sb.toString();
	}

	/**
	 * On an even second, Y is returned for the top row. On an odd second, O is
	 * returned for the top row.
	 * 
	 * @param number
	 * @return
	 */
	protected String getSeconds(int number) {
		if (number % 2 == 0)
			return "Y";
		else
			return "O";
	}

	/**
	 * At 5 hours past, ROOO is returned. At 10 hours past, RROO is returned. At
	 * 23 hour past, RRRR is returned.
	 * 
	 * @param number
	 * @return
	 */
	protected String getTopHours(int number) {
		return getOnOff(4, getTopNumberOfOnSigns(number));
	}

	/**
	 * When it is 1 hour past, ROOO is returned. When it is 2 hours past, RROO
	 * is returned. When it is 5 hours past, OOOO is returned.
	 * 
	 * @param number
	 * @return
	 */
	protected String getBottomHours(int number) {
		return getOnOff(4, number % 5);
	}

	/**
	 * At 5 past the minute, YOOOOOOOOOO is returned. At 15 past the minute,
	 * YYROOOOOOOOOO is returned. At 55 past the minute, YYRYYRYYRYY is
	 * returned.
	 * 
	 * @param number
	 * @return
	 */
	protected String getTopMinutes(int number) {
		return getOnOff(11, getTopNumberOfOnSigns(number), "Y").replaceAll(
				"YYY", "YYR");
	}

	/**
	 * When it is 1 minute past the hour, YOOO is returned. When it is 2 minutes
	 * past the hour, YYOO is returned. When it is 5 minutes past the hour, OOOO
	 * is returned.
	 * 
	 * @param number
	 * @return
	 */
	protected String getBottomMinutes(int number) {
		return getOnOff(4, number % 5, "Y");
	}

	// Default value for onSign would be useful
	private String getOnOff(int lamps, int onSigns) {
		return getOnOff(lamps, onSigns, "R");
	}

	/**
	 * This method will generate the final string for a given row.
	 * 
	 * @param lamps
	 *            Number of lamps in a row
	 * @param onSigns
	 * @param onSign
	 * @return
	 */
	private String getOnOff(int lamps, int onSigns, String onSign) {
		String out = "";
		// String multiplication would be useful
		for (int i = 0; i < onSigns; i++) {
			out += onSign;
		}
		for (int i = 0; i < (lamps - onSigns); i++) {
			out += "O";
		}
		return out;
	}

	/**
	 * Get the number of on signs for Top Hour/Minute row
	 * 
	 * @param number
	 * @return
	 */
	private int getTopNumberOfOnSigns(int number) {
		return (number - (number % 5)) / 5;
	}
}