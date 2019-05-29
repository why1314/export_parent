package com.itheima.service.feedback.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.feedback.Feedback;
import com.itheima.entity.feedback.FeedbackExample;
import com.itheima.mapper.feedback.FeedbackMapper;
import com.itheima.service.feedback.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-29-20:08
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public PageInfo<Feedback> findAll(FeedbackExample example, int page, int size) {
        //设置分页信息
        PageHelper.startPage(page, size);

        //查询数据
        List<Feedback> list = feedbackMapper.selectByExample(example);

        return new PageInfo<>(list);
    }

    @Override
    public Feedback findById(String id) {
        return feedbackMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Feedback feedback) {
        //设置反馈id
        feedback.setFeedbackId(UtilFuns.generatedId());
        //设置反馈创建时间
        feedback.setCreateTime(new Date());
        //设置反馈的状态
        feedback.setState("0");
        feedbackMapper.insertSelective(feedback);
    }

    @Override
    public void update(Feedback feedback) {

        if (!UtilFuns.isEmpty(feedback.getAnswerBy())) {
            feedback.setState("1");
        }
        feedbackMapper.updateByPrimaryKeySelective(feedback);
    }

    @Override
    public void delete(String id) {
        feedbackMapper.deleteByPrimaryKey(id);
    }
}
