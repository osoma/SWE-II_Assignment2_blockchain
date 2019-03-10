import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    private int difficulty;
    private List<Block> blocks = new ArrayList<>();
    private int length;
    public BlockChain(int difficulty) {
        this.difficulty = difficulty;
        // create the first block
        Block Genesis = new Block(0, System.currentTimeMillis(), null, "Genesis Block");
        Genesis.mineBlock(difficulty);
        blocks.add(Genesis);
        length=1;
    }
    public void deletelast() // to delete the invalid block if it's added
    {
        blocks.remove(blocks.size()-1);
    }


    public Block LastBlock() {
        return blocks.get(blocks.size() - 1);
    }

    public Block newB(String data) {
        Block LastBlock = LastBlock();
        length++;
        return new Block(LastBlock.getIndex() + 1, System.currentTimeMillis(),
                LastBlock.getHash(), data);
    }

    public void addBlock(Block b) {
        if (b != null) {
            b.mineBlock(difficulty);
            blocks.add(b);
        }
    }

    public boolean GensisValidation() {
        Block Genesis = blocks.get(0);

        if (Genesis.getIndex() != 0) {
            return false;
        }

        if (Genesis.getPrevHash() != null) {
            return false;
        }

        if (Genesis.getHash() == null ||
                !Block.CalcHash(Genesis).equals(Genesis.getHash())) {
            return false;
        }

        return true;
    }
    public int getLength()
    {
        return length;
    }
    public boolean ValidateGenesis(Block newB, Block prev) {
        if (newB != null  &&  prev != null) {
            if (prev.getIndex() + 1 != newB.getIndex()) {
                return false;
            }

            if (newB.getPrevHash() == null  ||
                    !newB.getPrevHash().equals(prev.getHash())) {
                return false;
            }

            if (newB.getHash() == null  ||
                    !Block.CalcHash(newB).equals(newB.getHash())) {
                return false;
            }

            return true;
        }
        return false;
    }

    public boolean isBlockChainValid() {
        if (!GensisValidation()) {
            return false;
        }
        for (int i = 1; i < blocks.size(); i++) {
            Block Current = blocks.get(i);
            Block prev = blocks.get(i - 1);

            if (!ValidateGenesis(Current, prev)) {
                return false;
            }
        }

        return true;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Block block : blocks) {
            builder.append(block).append("\n");
        }
        return builder.toString();
    }

}