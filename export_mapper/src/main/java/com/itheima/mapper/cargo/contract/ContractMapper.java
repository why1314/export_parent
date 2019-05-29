package com.itheima.mapper.cargo.contract;

import com.itheima.entity.cargo.contract.Contract;
import com.itheima.entity.cargo.contract.ContractExample;


import java.util.List;

public interface ContractMapper {

    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    int insertSelective(Contract record);

    List<Contract> selectByExample(ContractExample example);

    Contract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);
}