package com.xiangzi.learnconfigserver.main;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * description
 * author:zhangxx
 * Date:2019/9/17
 * Time:15:31
 */
public class TestController {

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("xiangzi");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("TodayIsSunShine");
        String password = textEncryptor.encrypt("Xx17681230080");
        System.out.println("username:" + username);
        System.out.println("password:" + password);
    }
}
