package com.itheima.web.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.feedback.Feedback;
import com.itheima.entity.feedback.FeedbackExample;
import com.itheima.service.feedback.FeedBackService;
import com.itheima.web.controller.BaseController;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-29-20:12
 */
@Controller
@RequestMapping("/system/feedback")
public class FeedBackController extends BaseController {

    @Reference
    private FeedBackService feedBackService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //创建查询对象
        FeedbackExample example = new FeedbackExample();

        //设置查询条件
        FeedbackExample.Criteria criteria = example.createCriteria();


        //设置细粒度权限
        switch (user.getDegree()) {
            case 0:
                //saas平台管理员
                break;
            case 1:
                //企业管理员,可以查询所有数据
                criteria.andCompanyIdEqualTo(companyId);
                break;
            case 2:
                //部门总监，查询下属部门及人员信息
                criteria.andCompanyIdEqualTo(companyId);
                criteria.andCreateDeptLike(user.getDeptId() + "%");
                break;
            case 3:
                //部门负责人，只能查询本部门数据
                criteria.andCompanyIdEqualTo(companyId);
                criteria.andCreateDeptEqualTo(user.getDeptId());
                break;
            case 4:
                //普通员工，只能查看自己
                criteria.andCompanyIdEqualTo(companyId);
                criteria.andCreateByEqualTo(user.getId());
                break;
        }


        PageInfo<Feedback> pageInfo = feedBackService.findAll(example, page, size);


        request.setAttribute("page", pageInfo);

        return "system/feedback/feedback-list";
    }


    /**
     * 前往添加页面
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "system/feedback/feedback-add";
    }

    /**
     * 前往修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        //查询要修改的数据

        //根据id查询反馈信息
        Feedback feedback = feedBackService.findById(id);

        //存入请求域中
        request.setAttribute("feedback", feedback);

        return "system/feedback/feedback-update";
    }


    /**
     * 保存或者更新
     *
     * @param feedback
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Feedback feedback) {
        //设置企业信息
        feedback.setCompanyId(companyId);
        feedback.setCompanyName(companyName);
        //判断是保存还是更新
        if (UtilFuns.isEmpty(feedback.getFeedbackId())) {
            //保存
            feedback.setCreateBy(user.getId());
            feedback.setCreateDept(user.getDeptId());
            feedBackService.save(feedback);
        } else {
            //更新
            feedBackService.update(feedback);
        }

        return "redirect:/system/feedback/list.do";
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id) {

        //根据id删除
        feedBackService.delete(id);

        //重定向到列表页面
        return "redirect:/system/feedback/list.do";
    }


    /**
     * 前往详情页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/toView")
    public String toView(String id) {

        //根据id查询
        Feedback feedback = feedBackService.findById(id);

        //存入请求域中
        request.setAttribute("feedback", feedback);

        return "system/feedback/feedback-view";
    }


    /**
     * 前往处理页面
     * @param id
     * @return
     */
    @RequestMapping("/toProcess")
    public String toProcess(String id) {

        //根据id查询
        Feedback feedback = feedBackService.findById(id);

        //存入请求域中
        request.setAttribute("feedback", feedback);

        return "system/feedback/feedback-process";
    }
}
