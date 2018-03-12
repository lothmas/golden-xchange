/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iqa.utilities;


import com.iqa.profiles.service.ProfilesService;
import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import static org.bouncycastle.util.IPAddress.isValid;

/**
 *
 * @author kwk
 */
public class GeneralDomainFunctions {

    @Autowired
    ProfilesService usersService;


    public static Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

//Read more: http://javarevisited.blogspot.com/2013/02/convert-xmlgregoriancalendar-to-date-xmlgregoriancalendar-java-example-tutorial.html#ixzz3Cie7n3l7
//    Joomla Password Hashing Implementation in Java
    /**
     *
     * @return
     */
    public static String getSalt() {
        return GeneralDomainFunctions.getSalt(EncryptionType.MD5, "", "");
    }

    /**
     *
     * @param encryption
     * @return
     */
    public static String getSalt(EncryptionType encryption) {
        return GeneralDomainFunctions.getSalt(encryption, "", "");
    }

    /**
     *
     * @param encryption
     * @param seed
     * @return
     */
    public static String getSalt(EncryptionType encryption, String seed) {
        return GeneralDomainFunctions.getSalt(encryption, seed, "");
    }

    /**
     *
     * @param encryption
     * @param seed
     * @param plaintext
     * @return
     */
    public static String getSalt(EncryptionType encryption, String seed, String plaintext) {

        switch (encryption) {
            case MD5:
            default:
                String salt = "";
                if (!seed.isEmpty()) {
                    salt = seed;
                }
                return salt;
        }
    }

    /**
     *
     * @param plaintext
     * @return
     * @throws Exception
     */
    public static String getCryptedPassword(String plaintext) throws Exception {
        return GeneralDomainFunctions.getCryptedPassword(plaintext, "", EncryptionType.MD5, false);
    }

    /**
     *
     * @param plaintext
     * @return
     * @throws Exception
     */
    public static String getCryptedPasswordAndSalt(String plaintext) throws Exception {
        String salt = generateRandomSalt(32);
        return GeneralDomainFunctions.getCryptedPassword(plaintext, salt) + ":" + salt;
    }

    /**
     *
     * @param plaintext
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getCryptedPassword(String plaintext, String salt) throws NoSuchAlgorithmException {
        return GeneralDomainFunctions.getCryptedPassword(plaintext, salt, EncryptionType.MD5, false);
    }

    /**
     *
     * @param plaintext
     * @param salt
     * @param encryption
     * @return
     * @throws Exception
     */
    public static String getCryptedPassword(String plaintext, String salt, EncryptionType encryption) throws Exception {
        return GeneralDomainFunctions.getCryptedPassword(plaintext, salt, encryption, false);
    }

    /**
     *
     * @param plaintext
     * @param salt
     * @param encryption
     * @param show_encrypt
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getCryptedPassword(String plaintext, String salt, EncryptionType encryption, boolean show_encrypt) throws NoSuchAlgorithmException {

        String encrypted;
        String newSalt = GeneralDomainFunctions.getSalt(EncryptionType.MD5, "", "");
        salt = (newSalt.isEmpty()) ? salt : newSalt;

        switch (encryption) {
            case MD5:
            default:
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                String stringToEncrypt = (!salt.isEmpty()) ? plaintext + salt : plaintext;
                md5.update(stringToEncrypt.getBytes());
                byte[] digest = md5.digest();
                StringBuilder stringBuffer = new StringBuilder();
                for (byte b : digest) {
                    stringBuffer.append(String.format("%02x", b & 0xff));
                }
                encrypted = stringBuffer.toString();
                return (show_encrypt) ? "{MD5}" + encrypted : encrypted;
        }
    }

    /**
     *
     * @param length Default is 8
     * @return
     * @throws IOException
     */
    public static String generateRandomSalt(int length) throws IOException {

        String salt = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int len = salt.length();
        String makepass = "";

        String seedRandom = GeneralDomainFunctions.microtime() + GeneralDomainFunctions.seedRandomExtraText();

        // get bytes from string
        byte bytes[] = seedRandom.getBytes();

        Checksum checksum = new CRC32();

        // update the current checksum with the specified array of byte
        checksum.update(bytes, 0, bytes.length);

        // get the current checksum value
        long seed = checksum.getValue();

        Random random = new Random(seed);

        for (int i = 0; i < length; i++) {
            makepass += salt.charAt(random.nextInt(len - 1));
        }

        return makepass;
    }

    /**
     *
     * @return
     */
    private static String microtime() {

        long mstime = System.nanoTime();
        long seconds = mstime / 1000;
        double decimal = (mstime - (seconds * 1000)) / 1000d;

        return decimal + " " + seconds;
    }

    /**
     *
     * @return @throws IOException
     */
    private static String seedRandomExtraText() throws IOException {
        String saltExtraInfo = "";
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            saltExtraInfo += rand.nextInt(100);
        }
        saltExtraInfo += System.currentTimeMillis();
        saltExtraInfo += "|" + System.nanoTime();

        return saltExtraInfo;
    }

    /**
     * Gets the value of the key in the serializedData parameter and returns it
     * if found else null
     *
     * @param key The Key to look for
     * @param serializedData The serialized data to look in
     * @return The value associated to key if found. null if not found
     */
    public static Object getKeyValue(String key, String serializedData) {

        MixedArray list = Pherialize.unserialize(serializedData).toArray();

        return (list.get(key) != null) ? list.get(key) : "";
    }

    public static String serializeList(Map<String, String> map) {

        return Pherialize.serialize(map);

    }

    private static String makeMemberId(boolean corporate_member_id) {
        String allowed_symbols_1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String allowed_symbols_2 = "0123456789";
        String pattern = "/CP|CB|CK|RN|RM|MM|CO|DO|CL|DB|QP|QB|DP/";
        String keystring_1 = "";
        String keystring_2 = "";
        Random random = new Random();
        while (true) {
            keystring_1 = "";
            int length = 2;

            for (int i = 0; i < length; i++) {

                keystring_1 += allowed_symbols_1.charAt(random.nextInt(allowed_symbols_1.length() - 1));
            }
            if (!Pattern.matches(pattern, keystring_1)) {
                break;
            }
        }

        keystring_2 = "";
        int length = 8;
        for (int i = 0; i < length; i++) {
            keystring_2 += allowed_symbols_2.charAt(random.nextInt(allowed_symbols_2.length() - 1));
        }

        if (!corporate_member_id) {
            return "M" + keystring_1 + keystring_2;
        } else {
            return "C" + keystring_1 + keystring_2;
        }
    }

    /**
     * @param email
     * @return true if it is a correct e-mail and false otherwise
     */
    public static boolean isEmailValid(String email) {
        boolean correctEmail = EmailValidator.getInstance().isValid(email);
        return correctEmail;
    }

    /**
     * Checks whether all the supplied string input is an integer
     *
     * @param stringNumber input string of any length to be tested
     * @return true if it contains only integers and false otherwise
     */
    public static boolean isIntegerString(String stringNumber) {
        return isIntegerString(stringNumber, 0);
    }

    /**
     * Checks whether all the supplied string input is an integer of size 1 up to supplied
     * length
     *
     * @param stringNumber input string to be tested
     * @param stringSize the expected length of the expected integer string
     * @return true if it contains only integers and false otherwise
     */
    public static boolean isIntegerString(String stringNumber, int stringSize) {
        boolean isInteger = false;

        String pattern;

        if (stringSize > 0) {

            pattern = "^[0-9]{1," + stringSize + "}$";

        } else {

            pattern = "^[0-9]{1,}$";

        }

        if (stringNumber.matches(pattern)) {
            isInteger = true;
        }

        return isInteger;
    }

    /**
     * Checks whether all the supplied string input is an integer of supplied
     * length ONLY
     *
     * @param stringNumber input string to be tested
     * @param stringSize the exact length of the expected integer string
     * @return true if it contains only integers and false otherwise
     */
    public static boolean isIntegerStringOfStrictSize(String stringNumber, int stringSize) {

        boolean isInteger = false;

        String pattern ;

        if(stringSize>0){

            pattern = "^[0-9]{" + stringSize + "}$";

        }else{

            return isInteger;

        }

//        if (Pattern.matches(pattern, stringNumber)) {
//            isInteger = true;
//        }

        if (stringNumber.matches(pattern)) {
            isInteger = true;
        }

        return isInteger;
    }

    /**
     * Testing whether a string has strictly three alphabet characters (Not Case
     * Sensitive)
     *
     * @param inputString a string with input to be tested
     * @return true if input is a string with exactly 3 characters and false
     * otherwise
     */
    public static boolean isThreeCharacters(String inputString) {
        return isCharactersOnly(inputString, 3);
    }

    /**
     * Testing whether a string has strictly the supplied length of alphabet
     * characters (Not Case Sensitive) i.e. [a-zA-Z]
     *
     * @param inputString a string with input to be tested
     * @param stringLength the expected size of string
     * @return true if input is a string with the supplied length of characters
     * and false otherwise
     */
    public static boolean isCharactersOnly(String inputString, int stringLength) {

        boolean isThreeCharacters = false;

        String pattern = "^[a-zA-Z]{1," + stringLength + "}$";

        if (inputString.matches(pattern)) {
            isThreeCharacters = true;
        }

        return isThreeCharacters;

    }

    /**
     * Testing whether a string has strictly the supplied length of alphabet
     * characters (Not Case Sensitive includes spaces) i.e. [a-zA-Z ]
     *
     * @param inputString a string with input to be tested
     * @param stringLength the expected size of string
     * @return true if input is a string with the supplied length of characters
     * and false otherwise
     */
    public static boolean isCharactersAndSpacesOnly(String inputString, int stringLength) {

        boolean isThreeCharacters = false;

        if (stringLength < 1) {

            return isThreeCharacters;

        }

//        stringLength-=1;


        String pattern = "^[a-zA-Z]{1}[a-zA-Z ]{0," + stringLength + "}$";

        if (inputString.length() <= stringLength) {
            isThreeCharacters = true;
        }

        return isThreeCharacters;

    }

    /**
     * Testing whether an e-mail string has strictly the supplied length of alphabet
     * characters (Not Case Sensitive includes special characters BUT NO spaces)
     *
     * @param emailString a string with input to be tested
     * @param stringLength the expected maximum size of string
     * @return true if input is a string with the supplied length of characters
     * and false otherwise
     */
    public static boolean isValidEmailCharactersOfSize(String emailString, int stringLength) {

        boolean isThreeCharacters = false;

        if(stringLength<1){

            return isThreeCharacters;

        }

        if(!isEmailValid(emailString)){

            return isThreeCharacters;

        }

        if (emailString.length()<=stringLength) {

            isThreeCharacters = true;

        }

        return isThreeCharacters;

    }

    /**
     * Verify whether a given string is of the form "yyyy-mm-dd".
     *
     * @param testDateString input string to be tested if its of form
     * "yyyy-mm-dd".
     * @return true if testDatestring is of form "yyyy-mm-dd" and false
     * otherwise.
     */
    public static boolean isValidDate(String testDateString) {
        boolean isDate = false;
        String pattern = "^(([0-9][0-9])([0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]))$";
        testDateString = testDateString.replaceAll(" ", "");
        if (Pattern.matches(pattern, testDateString)) {
            isDate = true;
        }

        return isDate;
    }

    public static String convertStringToBase64(String str) {

        byte[] byteArray = Base64.encode(str.getBytes());

        return new String(byteArray);
    }

    /**
     *
     * @param base64
     * @return
     */
    public static String convertBase64ToString(String base64) {

        byte[] byteArray = Base64.decode(base64.getBytes());

        return new String(byteArray);
    }

    /**
     * The function is normally used for verifying data integrity of data sent.
     * Example on the barclays credit card payment system.
     *
     * @param plaintext - the input string
     * @param salt - a secret salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String HMacSHA1(String plaintext, String salt) throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec keySpec = new SecretKeySpec(salt.getBytes(), "HmacSHA1");

        Mac mac = Mac.getInstance("HmacSHA1");

        mac.init(keySpec);

        byte[] result = mac.doFinal(plaintext.getBytes());

        byte[] byteArray = Base64.encode(result);

        return new String(byteArray);

    }

    public static boolean isOnline(String resourceUrl) throws MalformedURLException, IOException {

        boolean online = false;
        try {

            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection conn = (HttpURLConnection) new URL(resourceUrl).openConnection();
            conn.setRequestMethod("GET");

            int result = conn.getResponseCode();

            if (result == 200) {

                online = true;

            }

            return online;

        } catch (ConnectException e) {

            return online;

        }
    }

    public static Long convertIPv4ToLong(String clientIpAddress) throws UnknownHostException {

        long result;
        try {

            if (null != clientIpAddress && !clientIpAddress.isEmpty() && isValid(clientIpAddress)) {

                InetAddress bar = InetAddress.getByName(clientIpAddress);

                Integer value = ByteBuffer.wrap(bar.getAddress()).getInt();

                result = value.longValue();

                if (value.longValue() < 0) {

                    result = -1 * result;

                }

                return result;

            } else {

                throw new UnknownHostException("This ip :" + clientIpAddress + " is invalid!!");

            }

        } catch (UnknownHostException ex) {

            throw new UnknownHostException("This is an unknown host with ip :" + clientIpAddress + ". Detail is : " + ex.getMessage());

        }

    }

    /**This is used instead of /"convertIPv4ToLong(String clientIpAddress)/"
     *
     * @param ipAddress
     * @return
     * @throws UnknownHostException
     */
    public static long ipToLong(String ipAddress) throws UnknownHostException {

        if (null != ipAddress && !ipAddress.isEmpty() && isValid(ipAddress)) {

            String[] ipAddressInArray = ipAddress.split("\\.");

            long result = 0;
            for (int i = 0; i < ipAddressInArray.length; i++) {

                int power = 3 - i;
                int ip = Integer.parseInt(ipAddressInArray[i]);
                result += ip * Math.pow(256, power);

            }

            return result;

        } else {

            throw new UnknownHostException("This ip :" + ipAddress + " is invalid!!");

        }


    }

    public static Boolean containOnlyNumbers(String inputString) {

        Boolean containOnlyNumbers = false;

        String pattern = "^\\+[1-9]{1}[0-9]+$";

        if (null != inputString && Pattern.matches(pattern, inputString)) {

            containOnlyNumbers = true;

        }

        return containOnlyNumbers;

    }

    public static Boolean isValidRSATelephoneNumber(String inputString) {

        Boolean containOnlyNumbers = false;

        String pattern = "^0[1-9]{1}[0-9]{8}$";

        if (null != inputString && Pattern.matches(pattern, inputString)) {

            containOnlyNumbers = true;

        }

        return containOnlyNumbers;

    }

    public static boolean zimIDNumberValidation(String zimIDNumber) {

        zimIDNumber = zimIDNumber.trim().toUpperCase();

        if (zimIDNumber.length() != 14 && zimIDNumber.length() != 15) {
            return false;
        }

        Pattern p = Pattern.compile("^[0-9]{2}-[0-9]{6,7}\\s[a-zA-Z]\\s[0-9]{2}$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(zimIDNumber);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate input, to find whether is an integer or not
     *
     * @param stringInteger input
     * @return True if the supplied string is an integer" and False if otherwise
     */
    public boolean isInteger(String stringInteger) {
        try {
            Integer.valueOf(stringInteger);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //    An Enum used on Password Hashing above
    public enum EncryptionType {

        /**
         *
         */
        MD5(1), SHA1(2);

        private final int value;

        private EncryptionType(int value) {
            this.value = value;
        }

        /**
         * @return
         */
        public int getValue() {
            return value;
        }
    }

}
