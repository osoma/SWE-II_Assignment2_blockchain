import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    private static BlockChain LongestvalidChain=new BlockChain(4);

    //public BlockChain getLongestvalidChain() {return LongestvalidChain;}

    public static void setLongestvalidChain(BlockChain longestvalidChain) {
        LongestvalidChain = longestvalidChain;
    }

    public static void UpdateUsersChains(ArrayList<User>u)
    {
        BlockChain BB=new BlockChain(4);
        int max=0;
        int index=0;
        for(int i=0; i<u.size();i++) // find longest
        {
            User x=u.get(i);
            if(x.getB().getLength()>max)
            {
                max=x.getB().getLength();
                index=i;
                BB=x.getB();
            }
        }
        for(int i=0; i<u.size();i++) // assign longest BlockChain to all users
        {
            User x=u.get(i);
            x.setB(BB);
        }
    }
    public static void main(String[] args) throws IOException {
        BlockChain blockchain = new BlockChain(4);
        blockchain.addBlock(blockchain.newB("JavaApplication"));
        blockchain.addBlock(blockchain.newB("Hello World"));
        blockchain.addBlock(blockchain.newB("facebook.com"));

        if(blockchain.isBlockChainValid())
        {
            setLongestvalidChain(blockchain);
        }
        System.out.println(blockchain);
        // add an invalid block to corrupt Blockchain
        blockchain.addBlock(new Block(5, System.currentTimeMillis(), "aaaabbb", "aiosjdpiajd2839jdpaoisdj")); // invalid because index=5
        System.out.println("After adding invalid Block\n");
        System.out.println("Blockchain valid ? " + blockchain.isBlockChainValid());
        blockchain.deletelast(); // delete invalid block
        Scanner input=new Scanner(System.in);
        ArrayList<User>Users=new ArrayList<User>();
        Wallet w1=new Wallet(20160033,"AhmedMagdy521",150);
        String name1="Ahmed Mohamed Magdy";
        String id1="20160033";
        User u1=new User(blockchain,name1,id1,w1);
        Wallet w2=new Wallet(20160046,"OsamaShahat49",100);
        String name2="Osama Shahat";
        String id2="20160046";
        User u2=new User(blockchain,name2,id2,w2);
        Users.add(u1);
        Users.add(u2);
        System.out.println(u1.getW());
        System.out.println(u2.getW());
        User Curr=new User();
        while(true)
        {
            System.out.println("Menu:-");
            System.out.println("\t 1- Sign in\n\t 2- Sign up\n\t 3-Exit");
            int x = input.nextInt();
            input.skip("\n");

            switch(x)
            {
                case 1:
                    String name,id;
                    System.out.print("Enter User's name: ");
                    name=input.nextLine();
                    System.out.print("Enter User's ID: ");
                    id=input.next();
                    boolean NotFound=true;
                    for(int i=0; i<Users.size();i++)
                    {
                        User H=Users.get(i);
                        if(H.getId().equals(id) && H.getName().equals(name))
                        {
                            System.out.print("Login Successfully");
                            Curr = H;
                            NotFound = false;
                            break;
                        }
                    }
                    if(NotFound==true)
                    {
                        System.out.println("Login Failed");
                        break;
                    }
                    while(Curr!= null) {
                        System.out.println("\nMenu:-");
                        System.out.println("\t 1- Make Transaction\n\t 2- Sign out");
                        x = input.nextInt();
                        input.skip("\n");

                        switch (x) {

                            case 1:
                                String ID1, ID2;
                                User Sender = new User(), Receiver = new User();
                                System.out.print("Enter the Sender ID:");
                                ID1 = input.next();
                                System.out.print("Enter the Receiver ID:");
                                ID2 = input.next();
                                boolean f1 = false, f2 = false;
                                for (int i = 0; i < Users.size(); i++) {
                                    User H = Users.get(i);
                                    if (H.getId().equals(ID1)) {
                                        f1 = true;
                                        Sender = H;
                                    } else if (H.getId().equals(ID2)) {
                                        f2 = true;
                                        Receiver = H;
                                    }
                                }
                                if (f1 && f2) {
                                    System.out.print("Enter the amount: ");
                                    String Value = input.next();
                                    Sender.addTransaction(Sender, Receiver, Value);
                                    System.out.println(Sender.getW());
                                    System.out.println(Receiver.getW());
                                } else {
                                    System.out.println("Either Sender ID or Receiver ID is invalid");
                                }
                                UpdateUsersChains(Users);
                                break;
                            case 2:
                                Curr = null;
                                break;
                        }
                    }
                    break;
                case 2:
                    String n,idd,Signature;
                    int WpubKey;
                    double val;
                    System.out.print("Enter User's name: ");
                    n=input.nextLine();
                    System.out.print("Enter User's Signature: ");
                    Signature=input.nextLine();
                    System.out.print("Enter User's ID: ");
                    idd=input.nextLine();
                    System.out.print("Enter User's wallet public key: ");
                    WpubKey=input.nextInt();
                    System.out.print("Enter User's wallet value: ");
                    val=input.nextDouble();
                    Wallet w=new Wallet(WpubKey,Signature,val);
                    User u=new User(blockchain,n,idd,w);
                    Users.add(u);
                    System.out.println("User added successfully");
                    System.out.println(Users);
                    break;
                case 3:
                    System.out.println("Longest Valid Chain:-");
                    System.out.println(LongestvalidChain);
                    exit(0);
            }

        }

    }

}
