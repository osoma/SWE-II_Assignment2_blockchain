import java.util.ArrayList;

public class User
{
    private BlockChain B;
    private String name;
    private String Id;
    private Wallet w;
    private ArrayList<Transaction> Transactions=new ArrayList<Transaction>();
    public User()
    {

    }
    public User(BlockChain B,String name,String id,Wallet w)
    {
        this.B=B;
        this.name=name;
        this.Id=id;
        this.w=w;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", Id='" + Id + '\'' +
                '}';
    }

    public void addTransaction(User from, User to, String value)
    {
        Transaction t=new Transaction(from,to,value); // each transaction has unique id since user id is unique

        if(from.getW().getValue()<Double.parseDouble(value))
        {
            System.out.println("Transaction Failed\n");
            return;
        }
        else
        {
            from.getTransactions().add(t);
            to.getTransactions().add(t);
            B.addBlock(B.newB(t.toString()));
            to.getW().setValue(Double.parseDouble(value));
            from.getW().decreaseValue(Double.parseDouble(value));
            System.out.println("Transaction Succeeded\n");
        }
    }


    public ArrayList<Transaction> getTransactions() {
        return Transactions;
    }



    public BlockChain getB() {
        return B;
    }

    public void setB(BlockChain b) {
        B = b;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }


    public Wallet getW() {
        return w;
    }






}
