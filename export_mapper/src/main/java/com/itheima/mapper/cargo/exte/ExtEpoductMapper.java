package com.itheima.mapper.cargo.exte;

import com.itheima.entity.cargo.exte.ExtEpoduct;
import com.itheima.entity.cargo.exte.ExtEpoductExample;

import java.util.List;

public interface ExtEpoductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    int insert(ExtEpoduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    int insertSelective(ExtEpoduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    List<ExtEpoduct> selectByExampleWithBLOBs(ExtEpoductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    List<ExtEpoduct> selectByExample(ExtEpoductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    ExtEpoduct selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ExtEpoduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(ExtEpoduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_ext_eproduct
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ExtEpoduct record);
}