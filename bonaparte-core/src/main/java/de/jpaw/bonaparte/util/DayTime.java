/*
 * Copyright 2012 Michael Bischoff
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.jpaw.bonaparte.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * Some convenience functions for days and timestamps.
 * The conversion methods toCalendar and toDate are accessed from generated code.
 *
 * @author Michael Bischoff
 *
 */
public class DayTime {

    /** Provides functionality missing in the {@link java.util.GregorianCalendar} class, to create a new object initialized with the current timestamp. */
    static public GregorianCalendar getCurrentTimestamp() {
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(new Date());
        return now;
    }

    /** Provides functionality to convert a Joda timestamp to a GregorianCalendar. */
    static public GregorianCalendar toCalendar(LocalDateTime when) {
        if (when == null) {
            return null;
        }
        GregorianCalendar then = new GregorianCalendar();
        then.setTime(when.toDate());
        return then;
    }

    /** Provides functionality to convert a Joda date to a GregorianCalendar. */
    static public GregorianCalendar toCalendar(LocalDate when) {
        if (when == null) {
            return null;
        }
        GregorianCalendar then = new GregorianCalendar();
        then.setTime(when.toDate());
        return then;
    }

    /** Provides functionality to convert a Joda date to a GregorianCalendar. */
    static public GregorianCalendar toCalendar(LocalTime when) {
        if (when == null) {
            return null;
        }
        long millis = when.getMillisOfDay();
        GregorianCalendar then = new GregorianCalendar();
        then.setTimeInMillis(millis);
        return then;
    }


    /** Provides functionality to convert a Joda instant to a GregorianCalendar. */
    static public GregorianCalendar toCalendar(Instant when) {
        if (when == null) {
            return null;
        }
        long millis = when.getMillis();
        GregorianCalendar then = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        then.setTimeInMillis(millis);
        return then;
    }

    /** Provides functionality to convert a Joda timestamp to a java Date. */
    static public Date toDate(LocalDateTime when) {
        if (when == null) {
            return null;
        }
        return when.toDate();
    }

    /** Provides functionality to convert a Joda date to a java Date. */
    static public Date toDate(LocalDate when) {
        if (when == null) {
            return null;
        }
        return when.toDate();
    }

    /** Provides functionality to convert a Joda date to a java Date. */
    static public Date toDate(LocalTime when) {
        if (when == null) {
            return null;
        }
        return new Date(when.getMillisOfDay());  // intended conversion: Date is some time in Jan 1st 1970
    }

    /** Provides functionality to convert a Joda instant to a java Date. */
    static public Date toDate(Instant when) {
        if (when == null) {
            return null;
        }
        return new Date(when.getMillis());
    }

    /** Computes the difference between two LocalDateTime instances with millisecond precision.
     * Unfortunately the method getLocalMillis() which returns the time since the epoch is protected (as of JodaTime 2.1),
     * therefore we have to use the millis of a day ad work around the day wrap. (This is a solution for short time periods only!)
     * TODO: Should test that (by comparing start + 23 hours with end, and throwing an exception if start + 23 hours is still < end).
     */
    static public int LocalDateTimeDifference(LocalDateTime start, LocalDateTime end) {
        long t0 = start.getMillisOfDay();
        long t1 = end.getMillisOfDay();
        if (t0 > t1) {
            return (int)((t1 + 86400000L) - t0);
        }
        return (int)(t1 - t0);
    }

    /** Converts the day portion of a LocalDate or localDateTime into a number in the format YYYYMMDD. */
    static public int dayAsInt(LocalDateTime when) {
        return when.getDayOfMonth() + 100 * when.getMonthOfYear() + 10000 * when.getYear();
    }
    /** Converts the day portion of a LocalDate or localDateTime into a number in the format YYYYMMDD. */
    static public int dayAsInt(LocalDate when) {
        return when.getDayOfMonth() + 100 * when.getMonthOfYear() + 10000 * when.getYear();
    }

    /** Converts the time portion of a LocalTime or localDateTime into a number in the format HHMMSSMMM. */
    static public int timeAsInt(LocalDateTime when) {
        return when.getMillisOfSecond() + 1000 * when.getSecondOfMinute() + 100000 * when.getMinuteOfHour() + 10000000 * when.getHourOfDay();
    }
    /** Converts the time portion of a LocalTime or localDateTime into a number in the format HHMMSSMMM. */
    static public int timeAsInt(LocalTime when) {
        return when.getMillisOfSecond() + 1000 * when.getSecondOfMinute() + 100000 * when.getMinuteOfHour() + 10000000 * when.getHourOfDay();
    }

//    static public Instant toInstant(LocalDateTime ldt) {
//        // no good conversion known ? return new Instant(Date.getMillis(ldt.toDate()));
//    }
    static public LocalDateTime toLocalDateTime(Instant i) {
        return new LocalDateTime(i.getMillis(), DateTimeZone.UTC);
    }

    /** Converts the time since midnight (in milliseconds, i.e. 0..86_400_000) to a LocalTime. */
    static public LocalTime timeForMillis(int millis) {
        return new LocalTime(millis, DateTimeZone.UTC);
    }
}
