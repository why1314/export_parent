package com.itheima.service.export;


import com.github.pagehelper.PageInfo;
import com.itheima.entity.cargo.export.Export;
import com.itheima.entity.cargo.export.ExportExample;
import com.itheima.vo.ExportResult;


public interface ExportService {

    Export findById(String id);

    /**
     * 保存报运单
     * @param export
     */
    void save(Export export);


    /**
     * 更新报运单
     * @param export
     */
    void update(Export export);

    /**
     * 删除报运单
     * 删除报运商品
     * 删除报运附件
     * 更新购销合同状态
     * @param id
     */
    void delete(String id);

    /**
     * 分页查询报运单列表信息
     * @param example
     * @param page
     * @param size
     * @return
     */
	PageInfo findAll(ExportExample example, int page, int size);


    /**
     * jaxrs更新报运单
     * @param er
     */
	void updateER(ExportResult er);
}
