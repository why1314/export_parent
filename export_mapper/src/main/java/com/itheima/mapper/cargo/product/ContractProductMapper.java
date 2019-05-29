package com.itheima.mapper.cargo.product;


import com.itheima.entity.cargo.cproduct.ContractProduct;
import com.itheima.entity.cargo.cproduct.ContractProductExample;
import com.itheima.vo.ContractProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractProductMapper {

    //删除
    int deleteByPrimaryKey(String id);

    //保存
    int insertSelective(ContractProduct record);

    //条件查询
    List<ContractProduct> selectByExample(ContractProductExample example);

    //id查询
    ContractProduct selectByPrimaryKey(String id);

    //更新
    int updateByPrimaryKeySelective(ContractProduct record);

    List<ContractProductVo> findCproductByShipTime(@Param("companyId") String companyId, @Param("inputDate") String inputDate);
}