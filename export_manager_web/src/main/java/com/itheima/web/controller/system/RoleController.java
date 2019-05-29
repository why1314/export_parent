package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.system.Module;
import com.itheima.entity.system.Role;
import com.itheima.service.system.RoleService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-08-16:00
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //查询分页角色信息
        PageInfo pageInfo = roleService.findAll(companyId, page, size);

        //存入请求域中
        request.setAttribute("page", pageInfo);

        //返回结果
        return "system/role/role-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {

        return "system/role/role-add";
    }


    @RequestMapping("/edit")
    public String edit(Role role) {

        //为角色添加所属公司信息
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);

        if (UtilFuns.isEmpty(role.getId())) {

            //添加角色
            roleService.save(role);

        } else {
            roleService.update(role);
        }


        return "redirect:/system/role/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String roleid) {

        //查询要修改的角色信息
        Role role = roleService.findById(roleid);

        //将结果存入请求域中
        request.setAttribute("role", role);

        //返回结果
        return "system/role/role-add";
    }

    @RequestMapping("/delete")
    public String delete(String roleid) {

        roleService.delete(roleid);

        return "system/role/role-module";
    }

    /**
     * 前往分配权限页面
     * 第一种方法
     * @param roleid
     * @return
     */
    /*@RequestMapping("/roleModule")
    public String roleModule(String roleid) {

        Role role = roleService.findById(roleid);

        request.setAttribute("role",role);
        return "system/role/role-list";
    }*/

    /**
     * 前往分配权限页面
     * 第二种方法：异步请求获取树形结构的json数据
     *
     * @param roleid
     * @return
     */
    /*@RequestMapping("/initModuleData")
    public String initModuleData(String roleid) {

        Role role = roleService.findById(roleid);

        request.setAttribute("role",role);
        return "system/role/role-list";
    }*/

    /*以上两种方法都需要查询两次数据库进行字符串拼接*/


    /**
     * 和下面的initModuleData方法一起使用
     *
     * @param roleid
     * @return
     */
    @RequestMapping("/roleModule")
    public String roleModule(String roleid) {

        Role role = roleService.findById(roleid);

        request.setAttribute("role", role);

        return "system/role/role-module";
    }

    /**
     * 前往分配权限页面
     * 第三种方法：
     *
     * @param roleid
     * @return
     */
    @RequestMapping("/initModuleData")
    public @ResponseBody List initModuleData(String roleid) {

        List<Map> list = roleService.findTreeJson(roleid);

//        for (Map map : list) {
//            System.out.println(map);
//        }

        return list;
    }

    /**
     * @param id  是当前的角色id
     * @param moduleIds 是用,拼接的模块id
     * @return
     */
    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(@RequestParam("roleid") String id,String moduleIds){
        //1.调用业务层实现更新权限
        roleService.updateRoleModule(id,moduleIds);
        //2.重定向到角色列表页面
        return "redirect:/system/role/list.do";
    }
}
