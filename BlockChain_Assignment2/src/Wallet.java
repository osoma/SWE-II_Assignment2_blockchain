import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Wallet
{
    private double value;
    private int Publickey;
    private String PrivateKey;
    private String Signature;
    public Wallet(int publickey,String Signature,double value)
    {
        this.Publickey=publickey;
        this.Signature=Signature;
        this.value=value;
        calcPrivateKey();
    }
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

    @Override
    public String toString() {
        return "Wallet{" +
                "value=" + value +
                ", Publickey=" + Publickey +
                ", PrivateKey='" + PrivateKey + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }

    public void decreaseValue(double val)
    {
        this.value-=val;
    }
    public void calcPrivateKey()
    {
        try{
            this.PrivateKey = SHA256(Signature);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value += value;
    }



}
