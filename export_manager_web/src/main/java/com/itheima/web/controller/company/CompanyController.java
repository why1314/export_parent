package com.itheima.web.controller.company;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.company.Company;
import com.itheima.entity.page.PageResult;
import com.itheima.service.company.CompanyService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-04-17:39
 */

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Reference
    private CompanyService companyService;

    /**
     * 查询所有
     *
     * @return
     */
    @RequiresPermissions("企业管理")//基于注解的配置，必须有此权限才能访问
    @RequestMapping(value = "/list", name ="查询所有公司")
    public String findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        //调用service查询  此方法没有分页
        //List<Company> companyList = companyService.findAll();
        //request.setAttribute("list", companyList);

        //自己封装分页查询的信息
        //PageResult pageResult = companyService.findPage(page, size);
        //request.setAttribute("page", pageResult);

        PageInfo pageInfo = companyService.findPageByHelper(page, size);

        //存入请求域中
        request.setAttribute("page", pageInfo);
        //转发到列表页面
        return "company/company-list";
    }

    /**
     * 前往添加页面
     *
     * @return
     */
    @RequestMapping(value = "/toAdd")
    public String toAdd() {

        return "company/company-add";
    }

    /**
     * 保存或者更新
     *
     * @param company
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Company company) {
//        判断表单提交过来的company是否有主键
        if (StringUtils.isEmpty(company.getId())) {
//            保存
            companyService.save(company);
        } else {
//            更新
            companyService.update(company);
        }

        return "redirect:/company/list.do";
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
//        查询出来要更新的对象
        Company company = companyService.findById(id);
//        存入请求域中
        request.setAttribute("company", company);
//        转发到更新页面
        return "company/company-update";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
//         调用service方法删除
        companyService.delete(id);
//          重定向到列表页面
        return "redirect:/company/list.do ";
    }


}
