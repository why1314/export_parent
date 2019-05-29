package com.itheima.service.feedback;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.feedback.Feedback;
import com.itheima.entity.feedback.FeedbackExample; /**
 * 详情
 *
 * @author wz
 * @date 2019-05-29-20:11
 */
public interface FeedBackService {

    /**
     * 查询所有反馈
     * @param example
     * @param page
     * @param size
     * @return
     */
    PageInfo<Feedback> findAll(FeedbackExample example, int page, int size);

    /**
     * 根据id查询反馈数据
     * @param id
     * @return
     */
    Feedback findById(String id);

    /**
     * 添加新的反馈
     * @param feedback
     */
    void save(Feedback feedback);

    /**
     *更新反馈
     * @param feedback
     */
    void update(Feedback feedback);

    /**
     * 删除反馈数据
     * @param id
     */
    void delete(String id);
}
