import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

public class Block{

    private int index;
    private long timestamp;
    private String hash;
    private String previousHash;
    private String data;
    private int nonce;


    public Block(int index, long timestamp, String previousHash, String data) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.data = data;
        nonce = 0;
        hash = Block.CalcHash(this);
    }

    public int getIndex() {
        return index;
    }



    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return previousHash;
    }



    public String str() {
        return index + timestamp + previousHash + data + nonce;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Block ").append(index).append(" [previousHash : ").append(previousHash).append(", ").
                append("timestamp : ").append(new Date(timestamp)).append(", ").append("data : ").append(data).append(", ").
                append("hash : ").append(hash).append("]");
        return builder.toString();
    }

    public static String CalcHash(Block block) {
        if (block != null) {
            MessageDigest digest = null;

            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                return null;
            }

            String txt = block.str();
            final byte bytes[] = digest.digest(txt.getBytes());
            final StringBuilder builder = new StringBuilder();

            for (final byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    builder.append('0');
                }

                builder.append(hex);
            }

            return builder.toString();
        }

        return null;
    }
    public String DiffString(int difficulty)
    {
        String data="";
        for(int i=0; i<difficulty;i++)
        {
            data+="0";
        }
        return data;
    }
    public void mineBlock(int difficulty) {
        nonce = 0;
        while (!getHash().substring(0,  difficulty).equals(DiffString(difficulty))) {
            nonce++;
            hash = Block.CalcHash(this);
        }
    }
}