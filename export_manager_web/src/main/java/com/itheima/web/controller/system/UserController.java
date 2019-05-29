package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.Dept;
import com.itheima.entity.system.Role;
import com.itheima.entity.system.User;
import com.itheima.service.system.DeptService;
import com.itheima.service.system.RoleService;
import com.itheima.service.system.UserService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-07-17:20
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //分页查询所有的用户信息
        PageInfo pageInfo = userService.findAll(companyId, page, size);

        //将结果存入请求域中
        request.setAttribute("page", pageInfo);

        //转发到指定页面进行显示
        return "system/user/user-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {

        //查询该公司下的所有部门集合
        List<Dept> deptList = deptService.findAll(companyId);

        //将结果存入请求域中
        request.setAttribute("deptList", deptList);

        //转发到指定页面进行显示
        return "system/user/user-add";
    }

    @RequestMapping("/edit")
    public String edit(User user) {

        if (StringUtils.isEmpty(user.getId())) {

            //如果没有id，者为添加

            user.setCompanyId(companyId);

            user.setCompanyName(companyName);

            userService.save(user);
        } else {

            //如果有id，者为修改
            //先查询出来要修改的用户信息
            User u = userService.findById(user.getId());

            //将前台传递的数据覆盖到查询出来的数据中
            BeanUtils.copyProperties(user, u, new String[]{"companyId", "companyName"});

            //进行更新
            userService.update(u);
        }

        //转发到指定页面进行显示
        return "redirect:/system/user/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        //查询要修改的用户信息
        User user = userService.findById(id);

        //转发到指定页面进行显示
        request.setAttribute("user", user);

        //查询该公司下的所有部门集合
        List<Dept> deptList = deptService.findAll(companyId);

        //将结果存入请求域中
        request.setAttribute("deptList", deptList);

        //转发到指定页面进行显示
        return "system/user/user-update";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id) {

        userService.delete(id);

        //转发到指定页面进行显示
        return "redirect:/system/user/list.do";
    }

    /**
     * 角色列表
     *
     * @param id
     * @return
     */
    @RequestMapping("/roleList")
    public String roleList(String id) {

        //查询该公司下所有的角色
        List<Role> roleList = roleService.findRoleListByCompanyId(companyId);

        //查询当前用户所拥有的角色
//        List<Map> userRoleStr = userService.findUserRole(id);
        List<String> userRoleStr = userService.findUserRole(id);

        //获得该用户的所有信息
        User user = userService.findById(id);


        //将数据存入请求域中
        request.setAttribute("roleList", roleList);

        request.setAttribute("userRoleStr", userRoleStr.toString());

        request.setAttribute("user", user);

        //转发到指定页面进行显示
        return "system/user/user-role";
    }


    /**
     * 为用户重新分配角色
     *
     * @param userid
     * @param roleIds
     * @return
     */
    @RequestMapping("/changeRole")
    public String changeRole(String userid, String roleIds) {


        userService.changeRole(userid, roleIds);

        //转发到指定页面进行显示
        return "redirect:/system/user/list.do";
    }

}
