package com.itheima.mapper.company;

import com.github.pagehelper.PageInfo;
import com.itheima.entity.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 详情:企业的持久层接口
 *
 * @author wz
 * @date 2019-05-04-13:26
 */
public interface CompanyMapper {
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
     * 查询总记录数
     *
     * @return
     */
    long findTotal();

    /**
     * 查询带有分页的结果集
     *
     * @param startIndex
     * @param size
     * @return
     */
//    List<Company> findPageAll(@Param("startIndex") int startIndex, @Param("size") int size);

}
