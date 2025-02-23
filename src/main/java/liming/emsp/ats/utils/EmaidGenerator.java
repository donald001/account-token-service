package liming.emsp.ats.utils;
import java.util.Random;
public class EmaidGenerator {

    private static final String COUNTRY_CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();

    public static String generateEMAID(boolean includeCheckDigit) {
        String baseID = generateCountryCode()
                + generateProviderId()
                + generateEMAInstance();

        return includeCheckDigit ? baseID + calculateCheckDigit(baseID) : baseID;
    }

    // based on ISO/IEC 7064 MOD 37-2
    private static char calculateCheckDigit(String baseEMAID) {
        int[] weights = {3, 7, 5, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53};
        int sum = 0;

        for (int i = 0; i < baseEMAID.length(); i++) {
            int charValue = Character.digit(baseEMAID.charAt(i), 36);
            sum += charValue * weights[i % weights.length];
        }

        int checkValue = (38 - (sum % 37)) % 37;
        return Character.toUpperCase(Character.forDigit(checkValue, 36));
    }

    private static String generateCountryCode() {
        return randomString(COUNTRY_CODE_CHARS, 2);
    }

    private static String generateProviderId() {
        return randomString(ALPHANUMERIC_CHARS, 3);
    }

    private static String generateEMAInstance() {
        return randomString(ALPHANUMERIC_CHARS, 9);
    }

    private static String randomString(String charPool, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(charPool.charAt(random.nextInt(charPool.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Standard EMAID: " + generateEMAID(false));
        System.out.println("Full EMAID with check digit: " + generateEMAID(true));
    }
}
