package com.itheima.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.page.PageResult;
import com.itheima.mapper.company.CompanyMapper;
import com.itheima.entity.company.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-04-15:47
 */

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    public List<Company> findAll() {
        return companyMapper.findAll();
    }

    public Company findById(String id) {
        return companyMapper.findById(id);
    }

    public void save(Company company) {
        String id = UUID.randomUUID().toString().replace("-", "").toUpperCase().toString();
        company.setId(id);
        companyMapper.save(company);
    }

    public void update(Company company) {
        companyMapper.update(company);
    }

    public void delete(String id) {
        companyMapper.delete(id);
    }


//    public PageResult findPage(int page, int size) {
//        //查询总记录数
//        long total = companyMapper.findTotal();
//
//        //查询分页的企业列表
//        List<Company> companyList = companyMapper.findPageAll((page - 1) * size, size);
//
//        //创建PageResult对象
//        PageResult pageResult = new PageResult(total, companyList, page, size);
//
//        //返回结果
//        return pageResult;
//    }

    public PageInfo findPageByHelper(int page, int size) {
        //使用他的静态方法设置分页信息
        PageHelper.startPage(page, size);

        //直接调用查询所有的方法
        List<Company> companyList = companyMapper.findAll();

        //返回
        return new PageInfo(companyList);
    }
}
