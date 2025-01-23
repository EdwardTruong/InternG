package com.example.cache_example_with_ehcache.appilication.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContantUnit {
    public static final class STATUS {
        public static final int DELETED = -1;
        public static final int INACTIVE = 0;
        public static final int ACTIVE = 1;
        public static final int PENDING = 2;
    }

    public final static class TIME {
        public final static long EXPIRE_DURATION_JWT = 6 * 60 * 60 * 1000; // 6h(milliseconds )
        public final static long EXPIRE_DURATION_JWT_MINUTES = 6 * 60;
        public final static long EXPIRE_DURATION_JWT_CHANGE_PASSWORD = 15 * 60 * 1000; // 15 munites(milliseconds )
        public final static long EXPIRE_DURATION_SESSION_CHANGE_PASSWORD = 15; // 15 (minutes)
        public final static long REMEMBERME = 24 * 60 * 60 * 1000; // 24h

    }

    public final static class MESSENGER_NOT_FOUND {
        public final static String ROLE_NOT_EXIST = "Error: Role cannot be found!";
        public final static String CLINIC_NOTFOUND = "Error: Clinic cannot be found!";
        public final static String SPECIALIZATION_NOTFOUND = "Error: Specialization cannot be found!";
        public final static String USER_NOT_FOUND_EMAIL = "The account cannot be found with email : ";
        public final static String USER_NOT_FOUND_ID = "The account cannot be found with id";
        public final static String KEY_NOT_FOUND = "Key not found !";
        public final static String DOCTOR_NOT_FOUND = "Doctor not found !";
        public final static String SESSION_NOT_FOUND = "Session doesn't exit !";
        public final static String SCHEDULE_NOT_FOUND = "Doctor's schedule not found!";
        public final static String STATUS_NOT_FOUND = "Status's name not ! (ok,no,non)";

    }

    public final static class MESSENGER_ERROR {
        public final static String SECURITY_ERROR = "URL need to JWT ! ";
        public final static String FOLDER_EMPTY = "File upload location can not be Empty.";
        public final static String FILE_EMPTY = "Failed to store empty files.";
        public final static String FILE_NAME_ERROR = "Sorry! Filename contains invalid path sequence: ";
        public final static String FAILED_STORE = "Failed to store file.";
        public final static String INITALIZE_STORE = "Could not initialize storage";
        public final static String FILE_DELETE = "Could not detele file: ";
        public final static String NOT_IMAGE = "This is not image.";
        public final static String ORVER_SIZE = "File upload over size.";
        public final static String SESSION_TIME_OUT = "Link has expired. ";
        public final static String PATIENTS_NOT_BELOW_DOC = "The patient is not on the examination list !";
        public final static String TIME_BOOK_ERROR = "Please select the doctor's exact working hours !";
        public final static String TIME_INPUT_ERROR = "Not in the correct format for date input!";
        public final static String USER_EXIST = "Error: Email is already in use !";
        public final static String CANT_LOCK_ADMIN = "Can not lock main Admin !";
        public final static String CANT_LOCK_ACCOUNT = "Can not lock account !";

        public final static String ACTIVE_ERROR = "Active have 3 values : (-1,0,1) - (No, Not confirmed yet, Yes).";
        public final static String PRICES_ERROR = "min price > max price.";
        public final static String CANT_LOCK = "This account lock already !";
        public final static String CANT_UNLOCK = "The account was previously locked !";
        public final static String CANT_LOCK_DOC = "This is doctor account.";
        public final static String PASSOWRD_REGISTER_ERROR = "Password need have ";
        public final static String FORBIDDEN_MESSENGER = "The password needs to be at least 6 characters long and contain at least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character.";
        public final static String UNIDENTIFIED = "Can't actives user !";
    }

    public final static class PASSWORD {
        public final static Set<Character> SPECIAL_CHACTERLIST = new HashSet<>(
                Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'));

    }
}
