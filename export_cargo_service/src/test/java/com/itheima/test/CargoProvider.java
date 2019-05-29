package com.itheima.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-13-17:28
 */
public class CargoProvider {
    public static void main(String[] args) throws IOException {
        //读取配置文件
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        ac.start();
        System.in.read();
    }
}
