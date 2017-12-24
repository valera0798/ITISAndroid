package ru.itis.android.alarmclock.exception;

/**
 * Created by Users on 01.11.2017.
 */

public class NonExistentAlarmException extends Exception {
    public static final String LOG_E_NONEXISTENT_EXCEPTION = "NONEXISTENT_EXCEPTION";

    private static final String MESSAGE = "Alarm does not exist";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
