package fi.arcusys.koku.common.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 25, 2011
 */
public class CalendarUtil {
	private final static Logger logger = LoggerFactory.getLogger(CalendarUtil.class);

	public static XMLGregorianCalendar getXmlGregorianCalendar(final Date date) {
		return getXmlCalendar(date, new SetCalendar());
	}

	public static XMLGregorianCalendar getXmlDate(final Date date) {
		return getXmlCalendar(date, new SetDateOnlyCalendar());
	}

	public static XMLGregorianCalendar getXmlTime(final Date date, final int minutes) {
		return getXmlCalendar(date, new SetDateOnlyCalendar() {
			@Override
			void setCalendar(Calendar calendar) {
				super.setCalendar(calendar);
				calendar.set(Calendar.MINUTE, minutes);
			}
		});
	}

	private static XMLGregorianCalendar getXmlCalendar(final Date date, final SetCalendar setCalendar) {
		if (date == null) {
			return null;
		}
		try {
			final GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
			calendar.setTime(date);
			setCalendar.setCalendar(calendar);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			logger.error(null, e);
		}
		return null;
	}
	
	private static class SetCalendar {
		void setCalendar(final Calendar calendar){
		}
	}

	private static class SetDateOnlyCalendar extends SetCalendar {
		void setCalendar(final Calendar calendar){
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}
	}
}
