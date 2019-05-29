package com.itheima.web.controller;

import com.itheima.entity.company.Company;
import com.itheima.service.company.CompanyService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-13-14:08
 */
@Controller
public class ApplyController {

    @Reference
    private CompanyService companyService;

    /**
     * 企业申请
     *
     * @param company
     * @return
     */
    @RequestMapping("/apply")
    public @ResponseBody String applyCompany(Company company){

        //调用service进行保存
        companyService.save(company);
        return "1";
    }
}
