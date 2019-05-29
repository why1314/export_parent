package com.itheima.web.shiro;

import com.itheima.commons.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Objects;

/**
 * 详情:自定义的密码比较器
 *
 * @author wz
 * @date 2019-05-11-17:57
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {


    /**
     * 密码比较器
     * 比较密码
     *
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        //把参数强转为UsernamePasswordToken
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;

        //获取邮箱和密码
        String email = uToken.getUsername();//作为后期加密使用的盐
        char[] password = uToken.getPassword();//未加密的明文密码,后期进行加密

        //取出密文密码
        String dbPassword = (String) info.getCredentials();//取出凭证

        //对明文密码加密
        //new Md5Hash(password,salt,2).toString();
        String md5Password = Encrypt.md5(new String(password), email);


        //密码进行比较
        return Objects.equals(md5Password, dbPassword);
    }
}
