package com.itheima.company;

import com.itheima.mapper.system.UserMapper;
import com.itheima.service.system.impl.UserSserviceImpl;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-09-17:13
 */
public class ProxyTest {
    public static void main(String[] args) {
        //基于接口的动态代理
        //被代理对象是接口
        Proxy.newProxyInstance(
                UserMapper.class.getClassLoader(),//类加载器，加载代理对象的字节码，和被代理对象使用相同的类加载器
                new Class[]{UserMapper.class},//接口的字节码数组，被代理对象实现的接口的字节码数组
                new InvocationHandler() {//一个接口，用于提供增强代码
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //增强的代码
                        return null;
                    }
                });

        //被代理对象是类
        Proxy.newProxyInstance(
                UserSserviceImpl.class.getClassLoader(),//类加载器，加载代理对象的字节码，和被代理对象使用相同的类加载器
                UserSserviceImpl.class.getInterfaces(),//接口的字节码数组，被代理对象实现的接口的字节码数组
        new InvocationHandler() {//一个接口，用于提供增强代码
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强的代码
                return null;
            }
        });
//        Enhancer.create(UserMapper.class.getClassLoader(),);
    }
}
