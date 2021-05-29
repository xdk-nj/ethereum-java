package com.xdk.eth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

@RestController
@RequestMapping("/eth")
public class BlockController {

    @Autowired
    private Web3j web3j;

    @GetMapping("/height")
    public long getHeight() {
        try {
            EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
            long blockHeight = blockNumber.getBlockNumber().longValue();
            return blockHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @GetMapping("/getBlockTimestampByBlockNumber")
    public Long getBlockTimestampByBlockNumber(@RequestParam("blockNum") String blockNum) {
        try {
            EthBlock block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger(blockNum)), true).send();
            return block.getBlock().getTimestamp().longValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }
    @GetMapping("/getBlockByHash")
    public EthBlock ethGetBlockByHash(@RequestParam("blockHash") String blockHash) {
        try {
            EthBlock block = web3j.ethGetBlockByHash(blockHash, true).send();
            return block;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getBlockByNumber")
    public EthBlock ethGetBlockByNumber(@RequestParam("blockNum") String blockNum) {
        try {
            EthBlock block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger(blockNum)), true).send();
            return block;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/getTxByBlockHashAndIndex")
    public Transaction ethGetTransactionByBlockHashAndIndex(@RequestParam("blockHash") String blockHash, @RequestParam("transactionIndex") BigInteger transactionIndex) {
        try {
            Optional<Transaction> transaction = web3j.ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send().getTransaction();
            return transaction.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getTxByBlockHash")
    public Transaction ethGetTransactionByBlockHash(@RequestParam("blockHash") String blockHash) {
        try {
            Optional<Transaction> transaction = web3j.ethGetTransactionByBlockHashAndIndex(blockHash, new BigInteger("0")).send().getTransaction();
            return transaction.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getTxByTxHash")
    public Transaction ethGetTransactionByHash(@RequestParam("txHash") String txHash) {
        try {
            EthTransaction send = web3j.ethGetTransactionByHash(txHash).send();
            Optional<Transaction> transaction = send.getTransaction();
            return transaction.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}