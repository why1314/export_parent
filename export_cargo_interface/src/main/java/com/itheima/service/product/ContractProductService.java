package com.itheima.service.product;


import com.github.pagehelper.PageInfo;
import com.itheima.entity.cargo.cproduct.ContractProduct;
import com.itheima.vo.ContractProductVo;

import java.util.List;

/**
 * 业务层接口
 */
public interface ContractProductService {

	/**
	 * 保存
	 */
	void save(ContractProduct contractProduct);

	/**
	 * 更新
	 */
	void update(ContractProduct contractProduct);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	ContractProduct findById(String id);

	/**
	 * 分页查询
	 */
	PageInfo findAll(String contractId, int page, int size);

	/**
	 * 根据出货时间查询本公司的出货表信息
	 * @param companyId
	 * @param inputDate
	 * @return
	 */
    List<ContractProductVo> findCproductByShipTime(String companyId, String inputDate);

	/**
	 * 批量保存货物
	 * @param productList
	 */
	void batchSave(List<ContractProduct> productList);
}
