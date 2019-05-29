package com.itheima.web.controller;


import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.system.Module;
import com.itheima.entity.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * shiro的登录方式
     *
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", name = "用户登陆")
    public String login(String email, String password) {

        try {
            //获取Subject主体
            Subject subject = SecurityUtils.getSubject();
            //获取令牌
            UsernamePasswordToken token = new UsernamePasswordToken(email, password);
            //调用Subject的登录方法，把令牌提供给shiro的核心
            subject.login(token);
            //获取认证结果，获取到user对象，则表示成功，如果没有获取到shiro会抛异常
            User user = (User) subject.getPrincipal();//获取主题信息

            //登录以后要将该用户所拥有的模块查询出来
            List<Module> modules = userService.findUserModules(user.getId());
            //将相关数据存入回话域中
            session.setAttribute("modules", modules);

            session.setAttribute("loginUser", user);

            return "home/main";//登陆成功以后才进入主页面
        } catch (AuthenticationException e) {
            request.setAttribute("error", "邮箱不存在或密码不正确");
            return "forward:login.jsp";//这种写法不再使用视图解析器
        }

    }

    /**
     * 传统的登录方式
     *
     * @return
     */

   /* @RequestMapping(value = "/login",name = "用户登陆")
    public String login(String email, String password) {

        //判断用户邮箱和密码是否有数据
        if (UtilFuns.isEmpty(email)) {
            //首次登陆
            return "redirect:/login.jsp";
        }

        User user = userService.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            //提示邮箱不存在或密码不正确
            request.setAttribute("error", "邮箱不存在或密码不正确");
            return "forward:login";//这种写法不再使用视图解析器
        }
        //登录以后要将该用户所拥有的模块查询出来
        List<Module> modules = userService.findUserModules(user.getId());

        //将相关数据存入回话域中
        session.setAttribute("modules", modules);

        session.setAttribute("user", user);

        return "home/main";//登陆成功以后才进入主页面
    }*/

    //退出
    @RequestMapping(value = "/logout", name = "用户登出")
    public String logout() {
        SecurityUtils.getSubject().logout();   //登出
        return "forward:/login.jsp";
    }

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }
}
