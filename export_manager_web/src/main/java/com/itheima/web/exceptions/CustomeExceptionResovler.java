package com.itheima.web.exceptions;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-06-16:50
 */
@Component
public class CustomeExceptionResovler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception ex) {

        ModelAndView mv = new ModelAndView();
//        转发到error.jsp页面
        mv.setViewName("error");
//        判断异常类型
        if (ex instanceof CustomeException) {
            mv.addObject("errorMsg", ex.getMessage());
        } else if (ex instanceof UnauthorizedException) {
            mv.setViewName("forward:/unauthorized.jsp");
        } else {
            ex.printStackTrace();
            mv.addObject("errorMsg", "服务器忙碌");
        }

        return mv;
    }
}
