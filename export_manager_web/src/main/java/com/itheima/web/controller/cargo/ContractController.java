package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.commons.utils.DownloadUtil;
import com.itheima.commons.utils.UtilFuns;
import com.itheima.entity.cargo.contract.Contract;
import com.itheima.entity.cargo.contract.ContractExample;
import com.itheima.service.contract.ContractService;
import com.itheima.service.product.ContractProductService;
import com.itheima.vo.ContractProductVo;
import com.itheima.web.controller.BaseController;
import com.itheima.web.exceptions.CustomeException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-13-21:41
 */
@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    @Reference
    private ContractProductService contractProductService;

    /**
     * 分页查询合同列表
     * 每个企业只能查询自己的
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) throws CustomeException {

        //查询购销合同
        //设置查询条件
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();

        switch (user.getDegree()){
            case 0://saas管理员
                throw new CustomeException("没有访问权限");
            case 1://企业管理员
                criteria.andCompanyIdEqualTo(companyId);
                break;
            case 2://管理所有下属部门和人员
                criteria.andCreateDeptLike(user.getDeptId()+"%");
                break;
            case 3://管理本部门
                criteria.andCreateDeptEqualTo(user.getDeptId());
                break;
            case 4://普通员工
                criteria.andCreateByEqualTo(user.getId());
                break;
        }
        example.setOrderByClause("create_time desc");

        //调用分页查询合同信息
        PageInfo<Contract> pageInfo = contractService.findAll(example, page, size);

        //存入请求域中
        request.setAttribute("page", pageInfo);

        //转发到相应的页面
        return "cargo/contract/contract-list";
    }

    /**
     * 前往添加页面
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }

    /**
     * 前往修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {

        //根据id查询合同信息
        Contract contract = contractService.findContractById(id);

        //将合同信息存入请求域中
        request.setAttribute("contract", contract);

        //转发到修改页面
        return "cargo/contract/contract-update";
    }


    /**
     * 对合同进行添加或修改
     *
     * @param contract
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Contract contract) {


        if (UtilFuns.isEmpty(contract.getId())) {
            //为添加
            contract.setCompanyId(companyId);
            contract.setCompanyName(companyName);

            //设置合同的创建者，创建者所属部门
            contract.setCreateBy(user.getId());
            contract.setCreateDept(user.getDeptId());

            contractService.save(contract);
        } else {
            contractService.update(contract);
        }

        //重定向
        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 展示合同信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/toView")
    public String toView(String id) {

        //根据id查询合同信息
        Contract contract = contractService.findContractById(id);

        //将合同信息存入请求域中
        request.setAttribute("contract", contract);

        //转发到修改页面
        return "cargo/contract/contract-view";
    }


    /**
     * 删除相关合同，对该合同进行update操纵，使得合同状态为不可见
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id) {


        //根据id查询合同信息
        contractService.delete(id);

        //重定向
        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 提交
     * 改变合同状态为1
     *
     * @param id
     * @return
     */
    @RequestMapping("/submit")

    public String submit(String id) {

        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(1);
        //根据id更新合同信息
        contractService.update(contract);

        //重定向
        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 取消
     * 改变合同状态为0
     *
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    public String cancel(String id) {

        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(0);
        //根据id更新合同信息
        contractService.update(contract);

        //重定向
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 前往出货表页面
     *
     * @return
     */
    @RequestMapping("/print")
    public String print() {

        return "cargo/print/contract-print";
    }


    /**
     * 读取相应的模板：模板打印
     * 将数据导出到相应的excel文件中
     * 在写方法之前先确定需求
     */
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws IOException, ParseException {
        //1.获取模板路径
        String realPath = session.getServletContext().getRealPath("/make/xlsprint/tOUTPRODUCT.xlsx");
        //2.读取excel模板 创建excel对象
        XSSFWorkbook wb = new XSSFWorkbook(realPath);

        //3.读取sheet对象
        Sheet sheet = wb.getSheetAt(0);

        //4.定义一些重复可用的的对象
        int rowIndex = 0;
        int cellIndex = 1;
        Row nRow = null;
        Cell nCell = null;
    //=================大标题===================
        //5.读取大标题row对象
        nRow = sheet.getRow(rowIndex++);

        //读取大标题单元格
        nCell = nRow.getCell(cellIndex++);

        //设置大标题内容
        String bigTitle = inputDate.replaceAll("-0", "-").replaceAll("-", "年") + "月份出货表";
        nCell.setCellValue(bigTitle);

        //设置大标题样式
        CellStyle cellStyle = this.bigTitle(wb);
        nCell.setCellStyle(cellStyle);
    //=================小标题===================

//        String[] titles = new String[]{"客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
//        //设置小标题内容
//        nRow = sheet.createRow(rowIndex++);
//
//        //设置小标题行高
//        nRow.setHeightInPoints((float) 26.25);
//        cellIndex = 1;
//        for (String title : titles) {
//            //创建单元格
//            nCell = nRow.createCell(cellIndex++);
//            //设置单元格的值
//            nCell.setCellValue(title);
//            //设置小标题的样式
//            CellStyle style = this.title(wb);
//            nCell.setCellStyle(style);
//        }

        //跳过第二行，
        rowIndex++;

        //读取第三行，获取样式
        cellIndex = 1;

        nRow = sheet.getRow(rowIndex++);
        CellStyle c1 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle c2 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle c3 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle c4 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle c5 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle c6 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle c7 = nRow.getCell(cellIndex++).getCellStyle();
        CellStyle c8 = nRow.getCell(cellIndex++).getCellStyle();
        //行高
        float heightInPoints = nRow.getHeightInPoints();

    //=================填充的内容===================
        //获取需要填充的数据
        List<ContractProductVo> productVoList = contractProductService.findCproductByShipTime(companyId, inputDate);

        for (ContractProductVo productVo : productVoList) {
            //新建数据行
            nRow = sheet.createRow(rowIndex++);
            //设置数据行高
            nRow.setHeightInPoints(heightInPoints);
            //重设列索引值
            cellIndex = 1;
            //客户
            nCell = nRow.createCell(cellIndex++);
            nCell.setCellStyle(c1);
            nCell.setCellValue(productVo.getCustomName());

            //订单号
            nCell = nRow.createCell(cellIndex++);
            nCell.setCellStyle(c2);
            nCell.setCellValue(productVo.getContractNo());

            //货号
            nCell = nRow.createCell(cellIndex++);
            nCell.setCellStyle(c3);
            nCell.setCellValue(productVo.getProductNo());

            //数量
            nCell = nRow.createCell(cellIndex++);
            nCell.setCellStyle(c4);
            nCell.setCellValue(productVo.getCnumber());

            //工厂
            nCell = nRow.createCell(cellIndex++);
            nCell.setCellStyle(c5);
            nCell.setCellValue(productVo.getFactoryName());

            //工厂交期
            nCell = nRow.createCell(cellIndex++);
            nCell.setCellStyle(c6);
            nCell.setCellValue(UtilFuns.dateTimeFormat(productVo.getDeliveryPeriod(), "yyyy-MM-dd"));

            //船期
            nCell = nRow.createCell(cellIndex++);
            nCell.setCellStyle(c7);
            nCell.setCellValue(UtilFuns.dateTimeFormat(productVo.getShipTime(), "yyyy-MM-dd"));

            //贸易条款
            nCell = nRow.createCell(cellIndex++);
            nCell.setCellStyle(c8);
            nCell.setCellValue(productVo.getTradeTerms());
        }

    //=================从内存保存到磁盘===================
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        new DownloadUtil().download(bos, response, bigTitle + ".xlsx");
        bos.close();
        wb.close();
    }



    /**
     * 功能详情：自定义格式导出到excel
     *
     * @return
     * @param:
     * @author wz
     * @date 2019-5-17 15:29
     */

//    @RequestMapping("/printExcel")
//    public void printExcel(String inputDate) throws IOException, ParseException {
//        //创建excel对象
//        XSSFWorkbook wb = new XSSFWorkbook();
//
//        //用于百万数据的打印
////        SXSSFWorkbook wb=new SXSSFWorkbook();
//        //创建sheet对象
//        Sheet sheet = wb.createSheet();
//
//        //定义一些重复可用的的对象
//        int rowIndex = 0;
//        int cellIndex = 1;
//        Row nRow = null;
//        Cell nCell = null;
//        //设置列宽
//        sheet.setColumnWidth((short) 1, (short) (256 * 26));
//        sheet.setColumnWidth((short) 2, (short) (256 * 11.38));
//        sheet.setColumnWidth((short) 3, (short) (256 * 29.13));
//        sheet.setColumnWidth((short) 4, (short) (256 * 11.75));
//        sheet.setColumnWidth((short) 5, (short) (256 * 15));
//        sheet.setColumnWidth((short) 6, (short) (256 * 10));
//        sheet.setColumnWidth((short) 7, (short) (256 * 10));
//        sheet.setColumnWidth((short) 8, (short) (256 * 8));
//
//        //=================大标题===================
//        //创建大标题row对象
//        nRow = sheet.createRow(rowIndex++);
//        //设置大标题的样式
//        //设置行高
//        nRow.setHeightInPoints(36);
//        //根据大标题行创建大标题单元格
//        nCell = nRow.createCell(cellIndex++);  //有1变2
//        //设置单元格合并
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 8));
//        //设置大标题内容
//        nCell.setCellValue(inputDate);
//        //设置大标题样式
//        CellStyle cellStyle = this.bigTitle(wb);
//        nCell.setCellStyle(cellStyle);
//        //=================小标题===================
//
//        String[] titles = new String[]{"客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
//        //设置小标题内容
//        nRow = sheet.createRow(rowIndex++);
//
//        //设置小标题行高
//        nRow.setHeightInPoints((float) 26.25);
//        cellIndex = 1;
//        for (String title : titles) {
//            //创建单元格
//            nCell = nRow.createCell(cellIndex++);
//            //设置单元格的值
//            nCell.setCellValue(title);
//            //设置小标题的样式
//            CellStyle style = this.title(wb);
//            nCell.setCellStyle(style);
//        }
//
//        //=================填充的内容===================
//        //获取需要填充的数据
//        List<ContractProductVo> productVoList = contractProductService.findCproductByShipTime(companyId, inputDate);
//
//        for (ContractProductVo productVo : productVoList) {
//            //新建数据行
//            nRow = sheet.createRow(rowIndex++);
//            //设置数据行高
//            nRow.setHeightInPoints(24);
//            //重设列索引值
//            cellIndex = 1;
//            //客户
//            nCell = nRow.createCell(cellIndex++);
//            CellStyle c1 = this.text(wb);
//            nCell.setCellStyle(c1);
//            nCell.setCellValue(productVo.getCustomName());
//
//            //订单号
//            nCell = nRow.createCell(cellIndex++);
//            CellStyle c2 = this.text(wb);
//            nCell.setCellStyle(c2);
//            nCell.setCellValue(productVo.getContractNo());
//
//            //货号
//            nCell = nRow.createCell(cellIndex++);
//            CellStyle c3 = this.text(wb);
//            nCell.setCellStyle(c3);
//            nCell.setCellValue(productVo.getProductNo());
//
//            //数量
//            nCell = nRow.createCell(cellIndex++);
//            CellStyle c4 = this.text(wb);
//            nCell.setCellStyle(c4);
//            nCell.setCellValue(productVo.getCnumber());
//
//            //工厂
//            nCell = nRow.createCell(cellIndex++);
//            CellStyle c5 = this.text(wb);
//            nCell.setCellStyle(c5);
//            nCell.setCellValue(productVo.getFactoryName());
//
//            //工厂交期
//            nCell = nRow.createCell(cellIndex++);
//            CellStyle c6 = this.text(wb);
//            nCell.setCellStyle(c6);
//            nCell.setCellValue(UtilFuns.dateTimeFormat(productVo.getDeliveryPeriod(), "yyyy-MM-dd"));
//
//            //船期
//            nCell = nRow.createCell(cellIndex++);
//            CellStyle c7 = this.text(wb);
//            nCell.setCellStyle(c7);
//            nCell.setCellValue(UtilFuns.dateTimeFormat(productVo.getShipTime(), "yyyy-MM-dd"));
//
//            //贸易条款
//            nCell = nRow.createCell(cellIndex++);
//            CellStyle c8 = this.text(wb);
//            nCell.setCellStyle(c8);
//            nCell.setCellValue(productVo.getTradeTerms());
//        }
//
//        //=================从内存保存到磁盘===================
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        wb.write(bos);
//        new DownloadUtil().download(bos, response, inputDate + "出货表.xlsx");
//        bos.close();
//        wb.close();
//
//
//    }

    //大标题的样式
    public CellStyle bigTitle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        style.setBorderTop(BorderStyle.THIN);                        //上细线
        style.setBorderBottom(BorderStyle.THIN);                    //下细线
        style.setBorderLeft(BorderStyle.THIN);                        //左细线
        style.setBorderRight(BorderStyle.THIN);                        //右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);                //横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        style.setBorderTop(BorderStyle.THIN);                        //上细线
        style.setBorderBottom(BorderStyle.THIN);                    //下细线
        style.setBorderLeft(BorderStyle.THIN);                        //左细线
        style.setBorderRight(BorderStyle.THIN);                        //右细线

        return style;
    }


}
