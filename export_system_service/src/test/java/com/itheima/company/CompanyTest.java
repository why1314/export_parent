//package com.itheima.company;
//
//import com.itheima.entity.company.Company;
//import com.itheima.service.company.CompanyService;
//import com.itheima.service.system.RoleService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 详情
// *
// * @author wz
// * @date 2019-05-04-16:55
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
//public class CompanyTest {
//
//    @Autowired
//    private CompanyService companyService;
//
//    @Autowired
//    private RoleService roleService;
//
//    @Test
//    public void testFindAll(){
//        List<Company> all = companyService.findAll();
//        for (Company company : all) {
//            System.out.println(company);
//        }
//    }
//
//    @Test
//    public void testSave(){
//        Company company = new Company();
//        company.setName("why");
//        companyService.save(company);
//    }
//
//
//    @Test
//    public void testSave1(){
//        List<Map> treeJson = roleService.findTreeJson("4028a1c34ec2e5c8014ec2ebf8430001");
////        for (Map map : treeJson) {
////            System.out.println(map);
////        }
//
//    }
//}
