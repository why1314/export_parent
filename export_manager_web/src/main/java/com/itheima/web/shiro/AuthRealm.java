package com.itheima.web.shiro;

import com.itheima.entity.system.Module;
import com.itheima.entity.system.User;
import com.itheima.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

/**
 * 详情:自定义Realm域
 * 此时九已经明确了该类所包含的方法
 * 1.认证:用户名和密码的校验
 * 2.授权：获取用户的权限和每次访问时的权限鉴定（鉴权）
 *
 * @author wz
 * @date 2019-05-11-17:55
 */
public class AuthRealm extends AuthorizingRealm {
    public AuthRealm() {
        System.out.println("==================================================================================");
    }

    @Autowired
    private UserService userService;

    /**
     * 关于授权的方法（还可以配合标签进行鉴权）
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //取出认证成功的用户
        User user = (User) principals.getPrimaryPrincipal();
        // 查询该用户所具有的功能
        List<Module> Modules = userService.findUserModules(user.getId());

        //要填充的数据
        HashSet<String> moduleSet = new HashSet<>();
        for (Module module : Modules) {
            moduleSet.add(module.getName());
        }

        //创建返回值对象 授权的对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //按照返回值对象填充数据
        info.setStringPermissions(moduleSet);//set集合就是用户可以操作的模块，集合中的元数都是String类型

        return info;
    }


    /**
     * 关于认证的方法:内部为用户名和密码的对比
     *
     * @param token
     * @return 如果此方法返回null，则Subject的登陆方法就会抛异常
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //把参数强转为UsernamePasswordToken 里面封装的有前台传递过来的数据
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;

        //获取邮箱
        String username = uToken.getUsername();
        //使用邮箱去数据库查询
        User user = userService.findByEmail(username);

        //当user不为null时，创建返回值对象并返回
        if (user != null) {
            //创建返回值对象，且使用的是使用的构造函数要调用自定义比较器
            //认证信息对象
            //构造函数使用三个参数   从数据库查询出来的user对象（主题），密文密码(凭证Credential)，realm域的名称
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return info;
        }
        return null;
    }
}
