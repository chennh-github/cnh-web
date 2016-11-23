package com.cnh.mvc.system.demo.xls;

import com.cnh.mvc.share.xls.service.XlsExportService;
import com.cnh.mvc.system.demo.entity.Demo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/25
 */
@Service
public class DemoXlsExportService extends XlsExportService<Demo> {

    @Override
    protected String doExport(Map<String, Object> paramMap) throws Exception {
        return null;
    }
}
