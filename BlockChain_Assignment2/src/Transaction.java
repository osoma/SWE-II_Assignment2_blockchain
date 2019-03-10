import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Transaction {

    private String hash;
    private String value;
    private long timestamp; // time of transaction
    User from;
    User to;

    public static String SHA256(String input) throws UnsupportedEncodingException
    {
        try
        {
            MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
            byte [] hash=messageDigest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString=new StringBuffer(); //store hash in hexidecimal.
            for(int i=0;i<hash.length;i++)
            {
                String hex=Integer.toHexString(0xff&hash[i]);
                if (hex.length()==1)
                    hexString.append("0");
                hexString.append(hex);

            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return  "error";
        }
    }
    public Transaction(User from,User to, String value) {
        this.timestamp = System.currentTimeMillis();
        this.from=from;
        this.to=to;
        try{
            this.hash = SHA256(from.getId()+value+to.getId());
        }catch (Exception e) {
            e.printStackTrace();
        }
        this.setValue(value);
    }



    public void setValue(String value) {

        // new value need to recalc hash
        try{
            this.hash = SHA256(from+value+to);
        }catch (Exception e) {
            e.printStackTrace();
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "hash='" + hash + '\'' +
                ", value='" + value + '\'' +
                ", timestamp=" + new Date(timestamp) +
                ", from=" + from.getName() +
                ", to=" + to.getName() +
                '}';
    }
}