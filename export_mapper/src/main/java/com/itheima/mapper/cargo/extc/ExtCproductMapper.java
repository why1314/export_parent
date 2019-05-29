package com.itheima.mapper.cargo.extc;


import com.itheima.entity.cargo.extc.ExtCproduct;
import com.itheima.entity.cargo.extc.ExtCproductExample;

import java.util.List;

public interface ExtCproductMapper {

	//删除
	int deleteByPrimaryKey(String id);

	//保存
	int insertSelective(ExtCproduct record);

	//条件查询
	List<ExtCproduct> selectByExample(ExtCproductExample example);

	//id查询
	ExtCproduct selectByPrimaryKey(String id);

	//更新
	int updateByPrimaryKeySelective(ExtCproduct record);

}