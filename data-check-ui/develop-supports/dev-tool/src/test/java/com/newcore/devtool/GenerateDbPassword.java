package com.newcore.devtool;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 生成数据库密码的密文.
 *
 * @see ConfigTools#main(String[])
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-core.xml", "/dao/applicationContext-dao.xml"})
public class GenerateDbPassword {

    @Test
    public void generatePassword() throws Exception {
        String password = "maeagle0328";
        String[] arr = ConfigTools.genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + ConfigTools.encrypt(arr[0], password));
    }
}

