package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.Dept;
import com.itheima.service.system.DeptService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-07-10:51
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    /**
     * 带有分页的部门列表查询
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //调用service的分页查询
        PageInfo pageInfo = deptService.findAll(companyId, page, size);

        //存入到request请求域中
        request.setAttribute("page", pageInfo);

        //转发到指定页面进行显示
        return "system/dept/dept-list";

    }

    /**
     * 前往添加页面，
     * 添加部门时是允许选择父部门的，所以必须把该公司下的所有部门查询出来
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        //查询所有部门 ,不能带分页
        List<Dept> deptList = deptService.findAll(companyId);

        //吧查询结果存入请求域中
        request.setAttribute("deptList", deptList);

        //转发到指定页面进行显示
        return "system/dept/dept-add";
    }


    /**
     * 保存或者更新部门
     *
     * @param dept
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Dept dept) {
        if (StringUtils.isEmpty(dept.getId())) {
            //没有id代表是添加操作
            dept.setCompanyId(companyId);
            dept.setCompanyName(companyName);
            deptService.save(dept);

        } else {

            Dept dbDept = deptService.findById(dept.getId());
            //                        源   目标
            BeanUtils.copyProperties(dept, dbDept, new String[]{"companyId", "companyName"});
            //有id就代表为修改操作
            deptService.update(dbDept);//调用方法直接更新的前提条件：全字段更新

        }


        //重定向到指定路径
        return "redirect:/system/dept/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        //查询所有部门 ,不能带分页
        List<Dept> deptList = deptService.findAll(companyId);

        //吧查询结果存入请求域中
        request.setAttribute("deptList", deptList);

        //查询要编辑的部门
        Dept dept = deptService.findById(id);

        //将查询出来的信息存入请求域中
        request.setAttribute("dept", dept);

        //转发到指定页面进行显示
        return "system/dept/dept-update";
    }


    @RequestMapping("/delete")
    public String delete(String id) {

        //调用service的删除方法
        deptService.delete(id);

        //转发到指定页面进行显示
        return "redirect:/system/dept/list.do";
    }


}
