package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.User;
import com.itheima.service.system.SysLogService;
import com.itheima.web.controller.BaseController;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-09-17:53
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {

    @Reference
    private SysLogService sysLogService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //获取当前用户信息
        User user = (User) session.getAttribute("user");
        //查询
        PageInfo pageInfo = sysLogService.findAll(user.getCompanyId(), page, size);

        //将结果存入请求域中
        request.setAttribute("page", pageInfo);
        //转发
        return "system/log/log-list";
    }


}
