package com.cnh.mvc.share.xls.service;

import com.cnh.frame.wraps.XlsWrap;
import com.cnh.mvc.share.xls.handler.IXlsImportHandler;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
public abstract class XlsImportService<T> implements IXlsImportHandler {


    protected final static Logger LOGGER = LoggerFactory.getLogger(XlsImportService.class);

    /**
     * 导入验证执行前的处理函数，返回false表示不执行导入
     *
     * @param paramMap
     * @return
     */
    protected abstract boolean shouldImport(Map<String, Object> paramMap) throws Exception;

    /**
     * 验证导入Excel的每行字段合法性，返回null表示不合法
     *
     * @param workbook
     * @param row
     * @param paramMap
     * @return
     */
    protected abstract T verifyHSSFRow(Workbook workbook, Row row, Map<String, Object> paramMap) throws Exception;

    /**
     * 对合法数据的处理
     *
     * @param list
     */
    protected abstract void doImportList(List<T> list);


    @Override
    public Workbook importXls(Workbook workbook, Map<String, Object> paramMap) throws Exception {
        List<T> list = new ArrayList<T>();

        if (shouldImport(paramMap)) {
            for (int sheetIndex = 0, sheetLength = workbook.getNumberOfSheets(); sheetIndex < sheetLength; sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                if (sheet != null) {
                    for (int rowIndex = 1, rowLength = sheet.getPhysicalNumberOfRows(); rowIndex < rowLength; rowIndex++) {
                        Row row = sheet.getRow(rowIndex);
                        if (row != null) {
                            try {
                                T entity = verifyHSSFRow(workbook, row, paramMap);
                                if (null != entity) {
                                    list.add(entity);
                                }
                                XlsWrap.setRightCellText(workbook, XlsWrap.initCellIfNull(row, 0), "写入成功");
                            } catch (Exception e) {
                                XlsWrap.setWrongCellText(workbook, XlsWrap.initCellIfNull(row, 0), e.getMessage());
                            }
                        }
                    }
                }
            }
            doImportList(list);
        } else {
            LOGGER.warn("import xls canceled by user.");
        }
        return workbook;
    }
}
