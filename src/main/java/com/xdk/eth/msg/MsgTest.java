package com.xdk.eth.msg;

import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
/**
 * 以任意字符串发送交易
 * */
public class MsgTest {

    private static Web3j web3j;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        builder.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        OkHttpClient httpClient = builder.build();
        web3j = Web3j.build(new HttpService("http://localhost:7545", httpClient, false));
    }

    public static void main(String[] args) throws Exception {
        String msg = "你好吗";
        String hexStr = str2HexStr(msg);
        System.out.println(hexStr);

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                new BigInteger("46"),
                new BigInteger("200000000"),
                new BigInteger("6721975000"),
                "0x07F15372280afff9C8CAA1273a4038F69B110C74",
                hexStr);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,
                Credentials.create("24ed15fd84a06e4a52926667167c9ff31f729f83689ca8f0a8d215c7d90143a6"));
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        String transactionHash = ethSendTransaction.getTransactionHash();
        System.out.println(transactionHash);

        EthTransaction transaction = web3j.ethGetTransactionByHash(transactionHash).send();
        Transaction tx = transaction.getTransaction().get();
        String input = tx.getInput().substring(2);
        System.out.println(input);
        String str = hexStr2Str(input);
        System.out.println(str);
    }

    public static String str2HexStr(String str) {
        char[] chars = "0123456789abcdef".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789abcdef";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
}
