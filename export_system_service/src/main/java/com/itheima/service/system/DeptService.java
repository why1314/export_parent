package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.Dept;

import java.util.List;

/**
 * 详情 部门的业务层接口
 *
 * @author wz
 * @date 2019-05-07-10:43
 */
public interface DeptService {
    /**
     * 查询那个公司下的所有部门,带分页，使用PageHelper
     *
     * @param companyId
     * @return
     */
    PageInfo findAll(String companyId,int page,int size);

    /**
     * 根据id查询部门
     * @param deptId
     * @return
     */
    Dept findById(String deptId);

    /**
     * 对部门进行更新，需要先根据id查询出来才能更新
     * @param dept
     */
    void update(Dept dept);

    /**
     * 新建部门
     * @param dept
     */
    void save(Dept dept);

    /**
     * 删除指定id的部门
     * @param deptId
     */
    void delete(String deptId);

    /**
     *查询所有部门，不带分页
     * @param companyId
     * @return
     */
    List<Dept> findAll(String companyId);
}
