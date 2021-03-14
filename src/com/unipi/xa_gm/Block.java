package com.unipi.xa_gm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public class Block {

    private String blockchainId;
    private String hash;
    private String previousHash;
    private String thread;
    private long executionTime;
    private List<String> dependencies;
    private long timeStamp;
    private int nonce;


    //Constructor (Applied Composition)
    public Block(String blockchainId, CustomThread customThread, String previousHash, long timeStamp) {
        this.blockchainId=blockchainId;
        this.thread = customThread.getName();
        this.executionTime = customThread.getExecutionTime();
        this.dependencies = customThread.getDependencyListFormated();
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        hash = calculateBlockHash();
    }

    public String getThread() {return thread;}
    public long getExecutionTime() {return executionTime;}
    public List<String> getDependencies() {return dependencies;}
    public String getHash() {return hash;}
    public String getPreviousHash() {return previousHash;}

    public String mineBlock(int prefix){
        String prefixString = new String(new char[prefix]).replace('\0','0');
        while (!hash.substring(0,prefix).equals(prefixString)){
            nonce++;
            hash = calculateBlockHash();
        }
        return hash;
    }

    public String calculateBlockHash() {
        String dataToHash = previousHash + String.valueOf(timeStamp)+ String.valueOf(nonce) + blockchainId +
                thread + String.valueOf(executionTime) + String.join("",dependencies);

        MessageDigest digest = null;
        byte[] bytes = null;
        try
        {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b: bytes)
            buffer.append(String.format("%02x",b));
        return buffer.toString();
    }
}
