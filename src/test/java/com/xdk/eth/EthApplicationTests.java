package com.xdk.eth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class EthApplicationTests {

    @Autowired
    Map<String, String> map;

    @Test
    void test1() {
        System.out.println(map);
    }

}
