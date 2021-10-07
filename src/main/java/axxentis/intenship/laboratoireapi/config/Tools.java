package axxentis.intenship.laboratoireapi.config;

import java.util.Random;
import java.util.regex.Pattern;


public class Tools {

    /**
     * Create capitalize string
     *
     * @param imputValue
     * @return
     */
    public static final String toCapitalize(String imputValue) {
        // get First letter of the string
        String firstLetStr = imputValue.substring(0, 1);
        // Get remaining letter using substring
        String remLetStr = imputValue.substring(1);
        // convert the first letter of String to uppercase
        firstLetStr = firstLetStr.toUpperCase();
        // concantenate the first letter and remaining string
        return firstLetStr + remLetStr.toLowerCase();
    }

    public static final String lowerCase(String imputValue) {
        return imputValue.toLowerCase();
    }

    public static final String upperCase(String imputValue) {
        return imputValue.toUpperCase();
    }

    public static final String generateSERVEURID() {
        Random r = new Random(System.currentTimeMillis());
        int code = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        return "SERVEUR_ID_" + code;
    }

    public static final String generateUSERNAME() {
        Random r = new Random(System.currentTimeMillis());
        int code = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        return "USERNAME_" + code;
    }

    public static final String generateCXPID() {
        Random r = new Random(System.currentTimeMillis());
        int code = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        return "CXP_ID_" + code;
    }

    public static final String generateCODE(int code_length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                + "lmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(code_length);
        for (int i = 0; i < code_length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public static String generateRandomPASSWORD(int pwd_length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(pwd_length);
        for (int i = 0; i < pwd_length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isValidEMAIL(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
