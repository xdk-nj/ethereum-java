package com.xdk.eth.contract;

import com.xdk.eth.bytecode.SimpleStorage;
import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class ContractTest {
    private static Web3j web3j;
    private static Admin admin;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        builder.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        OkHttpClient httpClient = builder.build();
        web3j = Web3j.build(new HttpService("http://localhost:7545", httpClient, false));
        admin = Admin.build(new HttpService("http://localhost:7545"));
    }

    public static void transfer() throws IOException {
        String pk = "0xa85084D5aA8bAE0127B4c59103b520f0685dd691";
        String sk = "24ed15fd84a06e4a52926667167c9ff31f729f83689ca8f0a8d215c7d90143a6";
        PersonalUnlockAccount account = admin.personalUnlockAccount(pk, sk).send();

        String to = "0xC2380508A871e9d23f0711174594d5A32DA490B9";
        Transaction transaction = Transaction.createEtherTransaction(
                pk, new BigInteger("34"),
                new BigInteger("200000000"), new BigInteger("6721975000"), to, new BigInteger("300000")
        );

        EthSendTransaction transactionResponse = admin.ethSendTransaction(transaction).send();
        String transactionHash = transactionResponse.getTransactionHash();
        System.out.println(transactionHash);
    }

    public static void deployContract() throws Exception {
        Credentials credentials = Credentials.create("24ed15fd84a06e4a52926667167c9ff31f729f83689ca8f0a8d215c7d90143a6");
        SimpleStorage storage = SimpleStorage.load("0xE565B251c92FC6fBa7878D3F7c6c7557eF4d4460", web3j, credentials, new DefaultGasProvider());
        System.out.println(storage.getContractAddress());

        BigInteger res = storage.get().send();
        System.out.println(res);

//        TransactionReceipt send = storage.set(new BigInteger("1000")).send();
//        String hash = send.getTransactionHash();
//        System.out.println(hash);
    }

    public static void main(String[] args) throws Exception {
        deployContract();
    }
}
