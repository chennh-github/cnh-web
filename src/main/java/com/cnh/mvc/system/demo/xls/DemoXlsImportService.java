package com.cnh.mvc.system.demo.xls;

import com.cnh.mvc.share.xls.service.XlsImportService;
import com.cnh.mvc.system.demo.entity.Demo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/25
 */
@Service
public class DemoXlsImportService extends XlsImportService<Demo> {


    @Override
    protected boolean shouldImport(Map<String, Object> paramMap) throws Exception {
        return true;
    }

    @Override
    protected Demo verifyHSSFRow(Workbook workbook, Row row, Map<String, Object> paramMap) throws Exception {
        return null;
    }

    @Override
    protected void doImportList(List<Demo> list) {

    }
}
