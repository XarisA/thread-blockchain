package com.unipi.xa_gm;

import com.google.gson.GsonBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Blockchain {
    private String blockchainId;
    private static List<Block> blockChain = new ArrayList<>();


    public static List<Block> getBlockChain() {
        return blockChain;
    }


    public Blockchain(List<CustomThread> customThreads ,int prefix) {

        long startTime = System.nanoTime();
        System.out.println("-----Blockchain Creation-----");
        //Create unique blockchainId
        final String blockchainId = UUID.randomUUID().toString().replace("-", "");
        this.blockchainId=blockchainId;
        //Create a block for every thread
        for (int i=0;i<customThreads.size();i++)
            if (i==0){
                Block genesisBlock = new Block(blockchainId, customThreads.get(i),"0",new Date().getTime());
                genesisBlock.mineBlock(prefix);
                blockChain.add(genesisBlock);
            }
            else {
                Block nextBlock = new Block(blockchainId, customThreads.get(i),blockChain.get(blockChain.size()-1).getHash(),new Date().getTime());
                nextBlock.mineBlock(prefix);
                blockChain.add(nextBlock);
            }

        long endTime = System.nanoTime();
        long duration = endTime-startTime;
        System.out.println("Done in "+(float)duration/1000000000 +" seconds");
        //Validate blockchain
        System.out.println("Is BlockChain valid ?:"+ChainValidator.isChainValid(prefix,blockChain));
    }

    public void blockchainPrint(){
        String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println("-----Blockchain Prettified Printing-----");
        System.out.println("Your blockchain is:");
        System.out.println(blockChainJson);
    }

    public void sqlSave(List<Block> blockChain){
        /**
         * This method writes the given blocks to table "simulation" in database
         */
        System.out.println("-----Saving to SQL-----");
        try {
            String query="insert into simulations (BlockchainId,BlockId,ThreadName,ExecutionTime,Dependencies) values (?,?,?,?,?)";

            Connection conn = SQLConnection.DBConnector();

            assert conn != null;
            for (Block block:blockChain){
                PreparedStatement p = conn.prepareStatement(query);
                p.setString(1,blockchainId);
                p.setString(2,block.getHash());
                p.setString(3,block.getThread());
                p.setFloat(4,block.getExecutionTime());
                p.setString(5,String.join(";",block.getDependencies()));
            p.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Done!");
    }

}
