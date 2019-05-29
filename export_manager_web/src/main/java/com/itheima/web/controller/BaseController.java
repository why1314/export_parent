package com.itheima.web.controller;


import com.itheima.entity.system.User;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected String companyId;
    protected String companyName;
    protected User user;


    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        this.request = request;
        this.response = response;
        this.session = session;
        //模拟数据
        user = (User) session.getAttribute("loginUser");
        if (user != null) {
            this.companyId = user.getCompanyId();
            this.companyName = user.getCompanyName();
        }
    }
}
