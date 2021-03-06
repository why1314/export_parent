package com.itheima.mapper.cargo.eproduct;

import com.itheima.entity.cargo.eproduct.ExportProduct;
import com.itheima.entity.cargo.eproduct.ExportProductExample;

import java.util.List;

public interface ExportProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_export_product
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_export_product
     *
     * @mbg.generated
     */
    int insert(ExportProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_export_product
     *
     * @mbg.generated
     */
    int insertSelective(ExportProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_export_product
     *
     * @mbg.generated
     */
    List<ExportProduct> selectByExample(ExportProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_export_product
     *
     * @mbg.generated
     */
    ExportProduct selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_export_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ExportProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_export_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ExportProduct record);
}