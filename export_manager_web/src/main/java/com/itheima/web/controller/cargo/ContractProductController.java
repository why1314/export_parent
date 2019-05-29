package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.cargo.factory.Factory;
import com.itheima.entity.cargo.factory.FactoryExample;
import com.itheima.entity.cargo.cproduct.ContractProduct;
import com.itheima.service.factory.FactoryService;
import com.itheima.service.product.ContractProductService;
import com.itheima.web.controller.BaseController;
import com.itheima.web.utils.FileUploadUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 详情 货物的Controller
 *
 * @author wz
 * @date 2019-05-15-16:43
 */
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private FactoryService factoryService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * 显示该合同下的货物信息
     *
     * @param contractId
     * @return
     */
    @RequestMapping("/list")
    public String list(String contractId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        //查询获得货物的分页数据
        PageInfo pageInfo = contractProductService.findAll(contractId, page, size);

        // 存入请求域中
        request.setAttribute("page", pageInfo);

        request.setAttribute("contractId", contractId);

        //获得生产货物厂家的信息
        FactoryExample example = new FactoryExample();
        example.createCriteria().andCtypeEqualTo("货物");

        //获取生产货物的厂家信息
        List<Factory> factoryList = factoryService.findAll(example);
        request.setAttribute("factoryList", factoryList);

        //转发到相应页面
        return "cargo/product/product-list";
    }


    /**
     * 上传货物
     *
     * @param contractId
     * @return
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId) {
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-import";
    }


    //TODO

    /**
     * 批量导入货物信息
     *
     * @param contractId
     * @param file
     * @throws IOException
     */
    @RequestMapping("/import")
    public String importProduct(String contractId, MultipartFile file) throws IOException {

        //根据上传文件解析创建excel对象
//        Workbook wb = WorkbookFactory.create(file.getInputStream());
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());


        //读取第一个sheet页
        Sheet sheet = wb.getSheetAt(0);

        //获取行的迭代器
        //Iterator<Row> iterator = sheet.iterator();

        //创建集合，内部封装货物对象
        List<ContractProduct> productList = new ArrayList<>();

        //遍历获取货物信息
        Row row = null;
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            //取出每一行
            row = sheet.getRow(rowNum);

            //遍历获得行内单元格数据
            Object[] objects = new Object[10];
            for (int i = 1; i < 10; i++) {
                objects[i] = getValue(row.getCell(i));
            }
            //数据封装
            ContractProduct product = new ContractProduct(objects, companyId, companyName);
            product.setContractId(contractId);
            productList.add(product);
        }

        for (ContractProduct product : productList) {
            System.out.println(product);
        }
        //数据获取完毕
        contractProductService.batchSave(productList);

        return "redirect:/cargo/contract/list.do";

    }


    /**
     * 将单元格内的值进行类型转换
     * @param cell
     * @return
     */
    public Object getValue(Cell cell) {
        CellType type = cell.getCellType();
        Object cellValue = null;
        switch (type) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case NUMERIC://数值与日期均是这种类型
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue();
                } else {
                    cellValue = cell.getNumericCellValue();
                }
                break;


        }

        return cellValue;
    }


    /**
     * 前往修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        ContractProduct contractProduct = contractProductService.findById(id);

        request.setAttribute("contractProduct", contractProduct);

        //获得生产货物厂家的信息
        FactoryExample example = new FactoryExample();
        example.createCriteria().andCtypeEqualTo("货物");

        //获取生产货物的厂家信息
        List<Factory> factoryList = factoryService.findAll(example);
        request.setAttribute("factoryList", factoryList);
        //转发到修改页面
        return "cargo/product/product-update";
    }


    /**
     * 对货物进行添加或修改
     *
     * @param contractProduct
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) throws Exception {

        //设置货物所属公司
        contractProduct.setCompanyId(companyId);
        contractProduct.setCompanyName(companyName);

        if (UtilFuns.isEmpty(contractProduct.getId())) {
            //为null
            //判断是否选择了文件上传
            String imgPath = "";
            if (productPhoto != null) {
                String upload = fileUploadUtil.upload(productPhoto);
                imgPath = "http://" + upload;
            }
            //设置物品图片上传后的地址
            contractProduct.setProductImage(imgPath);
            contractProductService.save(contractProduct);
        } else {
            contractProductService.update(contractProduct);
        }

        //重定向
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }


    /**
     * 删除相关合同，对该合同进行update操纵，使得合同状态为不可见
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id, String contractId) {

        //删除货物
        contractProductService.delete(id);

        //重定向
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }


}
