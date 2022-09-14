import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;

public final class Console
{
    private static List<String> log = new ArrayList<String>();
     static List<String> valueLog = new ArrayList<String>();
    public static String logHASH = "";
    public static String valueLogHASH = "";
    private static File logFile = initLogFile();
    public static void log(String text){
        System.out.println(text);
        log.add(text);
        try
        {
            logHASHDigest();
        }
        catch (NoSuchAlgorithmException nsae)
        {
            nsae.printStackTrace();
        }
    }

    public static String read()throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public static void logValue(String value){
        valueLog.add(value);
        try
        {
            valueLogHASHDigest();
        }
        catch (NoSuchAlgorithmException nsae)
        {
            nsae.printStackTrace();
        }
    }

    public static void fileSaveValueLog(){
            try {
                FileWriter writer = new FileWriter(logFile.toString(), true);
                writer.write(valueLogHASH + '\n');
                writer.close();
            } catch (IOException e) {
                Console.log("An error occurred.");
                e.printStackTrace();
            }
    }

    public static void fileReadValueLog(){
        try {
            Scanner myReader = new Scanner(logFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    private static File initLogFile(){
        String path = null;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("windows")>-1) {
            path = System.getenv("APPDATA");
        } else if (osName.indexOf("mac")>-1) {
            path = System.getenv("?");
        } else {
            path = System.getProperty("user.home");
        }
        try {
            File logFileN = new File(path + "\\Log.txt");
            if (logFileN.createNewFile()) {
            } else {
            }
            return logFileN;
        } catch (IOException e) {
            Console.log("An error occurred in the File Creation process");
            e.printStackTrace();
        }
        return null;
    }

    private static void logHASHDigest() throws NoSuchAlgorithmException {
        String cache = "";
        for(int i = 0; i < log.size(); i++){
            cache += log.get(i);
        }
        logHASH = toHexString(getSHA(cache));
    }

    private static void valueLogHASHDigest() throws NoSuchAlgorithmException {
        String cache = "";
        for(int i = 0; i < valueLog.size(); i++){
            cache += valueLog.get(i);
        }
        valueLogHASH = toHexString(getSHA(cache));
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
