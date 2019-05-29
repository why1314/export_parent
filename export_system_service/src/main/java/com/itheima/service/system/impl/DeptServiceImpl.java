package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.system.Dept;
import com.itheima.mapper.system.DeptMapper;
import com.itheima.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-07-10:45
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    public PageInfo findAll(String companyId, int page, int size) {

        //设置分页信息
        PageHelper.startPage(page, size);

        //查询所有
        List<Dept> deptList = deptMapper.findAll(companyId);

        //返回
        return new PageInfo(deptList);
    }

    public List<Dept> findAll(String companyId) {

        return deptMapper.findAll(companyId);
    }

    public Dept findById(String deptId) {
        Dept dept = deptMapper.findById(deptId);
        return dept;
    }

    public void update(Dept dept) {
        deptMapper.update(dept);
    }

    public void save(Dept dept) {
        String id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        dept.setId(id);
        deptMapper.save(dept);
    }

    public void delete(String deptId) {
        deptMapper.delete(deptId);

    }
}
