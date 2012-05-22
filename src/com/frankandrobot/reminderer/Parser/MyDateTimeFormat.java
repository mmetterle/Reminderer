package com.frankandrobot.reminderer.Parser;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;

public interface MyDateTimeFormat {

	/**
	 * Looks for match at start of string. If found, returns String array. First
	 * element contains the match. Second element contains the remaining string.
	 * Otherwise returns null.
	 * 
	 * @param input
	 * @return
	 */
	public String[] find(final String input);

	/**
	 * Looks for dates only (02/12, Monday, Jun 2, etc)
	 * 
	 * @author uri
	 *
	 */
	public class DateFormat implements MyDateTimeFormat {
		Resources resources;
		DateFormatStrategy parser;

		public DateFormat(Context context) {
			parser = new DateFormatStrategy.BruteForce(context);

		}

		public String[] find(final String input) {
			return parser.find(input);

		}
	}

	/**
	 * Looks for day names (defined in the user's locale)
	 * 
	 * @author uri
	 * 
	 */
	static public class DayFormat implements MyDateTimeFormat {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		ParsePosition pos = new ParsePosition(0);
		Date result;

		/**
		 * Looks at the start of a string for a day (defined in the user's
		 * locale). If found, returns String array containing the match (1st
		 * elem) and the remaining string (2nd elem). Otherwise, returns null.
		 * 
		 * @param input
		 * @return
		 */
		public String[] find(final String input) {
			result = null;
			result = sdf.parse(input, pos);
			if (result == null)
				return null;
			return new String[] { sdf.format(result),
					input.substring(pos.getIndex()) };
		}
	}
}
