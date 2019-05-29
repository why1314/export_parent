package com.itheima.service.company;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.company.Company;

import java.util.List;

/**
 * 详情:企业的业务层接口
 *
 * @author wz
 * @date 2019-05-04-15:35
 */
public interface CompanyService {
    /**
     * 查询所有
     *
     * @return
     */
    List<Company> findAll();

    /**
     * 查询特定id的企业
     *
     * @param id
     * @return
     */
    Company findById(String id);


    /**
     * 保存
     *
     * @param company
     */
    void save(Company company);


    /**
     * 更新
     *
     * @param company
     */
    void update(Company company);


    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     *带有分页的企业列表查询
     * @param page
     * @param size
     * @return
     */
//    PageResult findPage(int page,int size);


    /**
     * 使用mybatis的PageHelper插件实现分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo findPageByHelper(int page, int size);
}
