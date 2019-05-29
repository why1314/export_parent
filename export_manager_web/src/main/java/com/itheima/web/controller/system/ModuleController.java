package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.system.Module;
import com.itheima.service.system.ModuleService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-08-16:00
 */
@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //查询分页模块信息
        PageInfo pageInfo = moduleService.findAll(page, size);

        //存入请求域中
        request.setAttribute("page", pageInfo);

        //返回结果
        return "system/module/module-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {

        List<Module> moduleList = moduleService.findAll();

        request.setAttribute("menus",moduleList);

        return "system/module/module-add";
    }


    @RequestMapping("/edit")
    public String edit(Module module) {

        if (UtilFuns.isEmpty(module.getId())) {


            //添加模块
            moduleService.save(module);

        } else {

            //更新模块
            moduleService.update(module);
        }


        return "redirect:/system/module/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        //查询要修改的模块信息
        Module module = moduleService.findById(id);

        //将结果存入请求域中
        request.setAttribute("module", module);

        //返回结果
        return "system/module/module-add";
    }

    @RequestMapping("/delete")
    public String delete(String id) {

        moduleService.delete(id);

        return "redirect:/system/module/list.do";
    }


}
