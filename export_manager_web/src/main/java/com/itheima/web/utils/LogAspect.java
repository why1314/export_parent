package com.itheima.web.utils;

import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.system.SysLog;
import com.itheima.entity.system.User;
import com.itheima.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 详情用于记录系统日志
 * 需求：用户点击了页面连接请求到达控制器之前，先记录系统日志
 * 要求：
 * 使用spring基于注解的aop配置
 * 通知使用环绕通知
 * 增强的方法写在控制器方法执行之前
 *
 * @author wz
 * @date 2019-05-09-18:12
 */
@Aspect    //声明当前类为切面类
@Component //让spring接管切面类的创建
public class LogAspect {


    /**
     * private String id; 日志id
     * private String userName;  用户名
     * private String ip;     用户ip
     * private Date time;     访问时间
     * private String method;   操作的方法  在环绕通知的参数中（pjp）
     * private String action;   操作的名称  在环绕通知的参数中（pjp）同时要求控制器方法的requestMapping注解的name属性有值
     * private String companyId;  公司id    在用户对象中
     * private String companyName;公司名称   在用户对象中
     *
     * @param pjp
     * @return
     */

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    //配置环绕通知，同时指定切入点表达式
    @Around("execution(* com.itheima.web.controller.*.*.*(..))")
    public Object aroundSysLog(ProceedingJoinPoint pjp) {
        try {

            //获取签名
            Signature signature = pjp.getSignature();
            //判断 当前的签名  是不是    方法的签名
            if (signature instanceof MethodSignature) {
                MethodSignature ms = (MethodSignature) signature;
                //从方法的签名中获取方法对象
                Method method = ms.getMethod();
                //判断该方法是否被@RequestMapping注解了
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    //获取RequestMapping注解
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    //取出name属性的值
                    String action = annotation.name();

                    //============================
                    SysLog log = new SysLog();
                    //获取用户信息
                    User user = (User) session.getAttribute("user");

                    if (user == null || UtilFuns.isEmpty(user.getUserName())) {
                        log.setUserName("匿名");
                    } else {
                        //设置用户名
                        log.setUserName(user.getUserName());
                        log.setCompanyId(user.getCompanyId());
                        log.setCompanyName(user.getCompanyName());
                    }
                    //设置访问ip
                    log.setIp(request.getRemoteAddr());
                    //设置访问时间
                    log.setTime(new Date());
                    //============================
                    //为log的action属性赋值
                    log.setAction(action);
                    //为log的method属性赋值
                    log.setMethod(method.getName());
                    //填充log的内容
                    sysLogService.save(log);
                }
            }
            
            //获取当前方法执行所需的参数
            Object[] args = pjp.getArgs();
            //执行方法并返回
            return pjp.proceed(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
