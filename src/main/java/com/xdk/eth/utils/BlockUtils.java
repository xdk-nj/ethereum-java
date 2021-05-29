package com.xdk.eth.utils;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.SocketHandler;

public class BlockUtils {
    private static Web3j web3j;
    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30*1000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(30*1000, TimeUnit.MILLISECONDS);
        builder.readTimeout(30*1000, TimeUnit.MILLISECONDS);
        OkHttpClient httpClient = builder.build();
        web3j = Web3j.build(new HttpService("http://localhost:7545",httpClient,false));
    }

    public static List<EthBlock> getAllBlocks() {
        long h = getHeight();
        List<EthBlock> res = new ArrayList<>();
        for (int i = 0; i <= h; i++) res.add(ethGetBlockByNumber(String.valueOf(i)));
        return res;
    }

    public static List<String> getAllBlockHash() {
        long h = getHeight();
        List<String> res = new ArrayList<>();
        for (int i = 0; i <= h; i++) res.add(ethGetBlockByNumber(String.valueOf(i)).getBlock().getHash());
        return res;
    }

    public static List<String> getAllTxHash() {
        long h = getHeight();
        List<String> res = new ArrayList<>();
        for (int i = 0; i <= h; i++) {
            List<EthBlock.TransactionResult> txs = ethGetBlockByNumber(String.valueOf(i)).getBlock().getTransactions();
            txs.forEach(tx -> {
                EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
                res.add(transaction.getHash());
            });
        }
        return res;
    }

    public static List<Long> getAllTimestamp() {
        long h = getHeight();
        List<Long> res = new ArrayList<>();
        for (int i = 0; i <= h; i++) {
            BigInteger timestamp = ethGetBlockByNumber(String.valueOf(i)).getBlock().getTimestamp();
            res.add(Long.valueOf(timestamp.toString()));
        }
        return res;
    }

    public static Long getHeight() {
        try {
            EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
            long blockHeight = blockNumber.getBlockNumber().longValue();
            return blockHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static Long getBlockTimestampByBlockNumber(String blockNum) {
        try {
            EthBlock block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger(blockNum)), true).send();
            return block.getBlock().getTimestamp().longValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static EthBlock ethGetBlockByHash(String blockHash) {
        try {
            EthBlock block = web3j.ethGetBlockByHash(blockHash, true).send();
            return block;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static EthBlock ethGetBlockByNumber(String blockNum) {
        try {
            EthBlock block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger(blockNum)), true).send();
            return block;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Transaction ethGetTransactionByBlockHashAndIndex(String blockHash, BigInteger transactionIndex) {
        try {
            Optional<Transaction> transaction = web3j.ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send().getTransaction();
            return transaction.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Transaction ethGetTransactionByBlockHash(String blockHash) {
        try {
            Optional<Transaction> transaction = web3j.ethGetTransactionByBlockHashAndIndex(blockHash, new BigInteger("0")).send().getTransaction();
            return transaction.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Transaction ethGetTransactionByHash(String txHash) {
        try {
            Optional<Transaction> transaction = web3j.ethGetTransactionByHash(txHash).send().getTransaction();
            return transaction.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        // System.out.println(getAllBlockHash());
        System.out.println(getAllTxHash());

    }
}