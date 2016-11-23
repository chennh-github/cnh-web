package com.cnh.mvc.share.xls.service;

import com.cnh.frame.wraps.RequestWrap;
import com.cnh.mvc.share.xls.handler.IXlsExportHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
public abstract class XlsExportService<T> implements IXlsExportHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(XlsExportService.class);

    /**
     * 处理导出
     * @param paramMap
     * @return
     */
    protected abstract String doExport(Map<String, Object> paramMap) throws Exception;

    @Override
    public String exportXls(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> paramMap = RequestWrap.getMap(request);
        String xlsPath = this.doExport(paramMap);

        LOGGER.info("export xmsPath={}", xlsPath);
        return xlsPath;
    }
}
