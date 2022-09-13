import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;

public final class Console
{
    private static List<String> log =new ArrayList<String>();
    private static String logHASH = "";
    public static void log(String text){
         System.out.println(text);
         log.add(text);
         try
         {
             logHASHUpdate();
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
    private static void logHASHUpdate() throws NoSuchAlgorithmException {
        String cache = "";
        for(int i = 0; i < log.size(); i++){
            cache += log.get(i);
        }
        logHASH = toHexString(getSHA(cache));
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
