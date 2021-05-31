package com.xdk.eth.msg;

import okhttp3.OkHttpClient;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SignTest {
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

    public static void main(String[] args) throws IOException {
        String pk = "0xa85084D5aA8bAE0127B4c59103b520f0685dd691";
        String sk = "24ed15fd84a06e4a52926667167c9ff31f729f83689ca8f0a8d215c7d90143a6";
        String signature = web3j.ethSign(pk, "你好").send().getSignature();
        System.out.println(signature);

    }
}
