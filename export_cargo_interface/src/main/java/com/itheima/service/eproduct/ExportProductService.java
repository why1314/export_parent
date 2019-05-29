package com.itheima.service.eproduct;


import com.itheima.entity.cargo.eproduct.ExportProduct;
import com.itheima.entity.cargo.eproduct.ExportProductExample;

import java.util.List;

public interface ExportProductService {

	ExportProduct findById(String id);

	void save(ExportProduct exportProduct);

	void update(ExportProduct exportProduct);

	void delete(String id);

	List<ExportProduct> findAll(ExportProductExample example);


}
