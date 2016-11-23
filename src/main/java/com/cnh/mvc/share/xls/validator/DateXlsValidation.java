package com.cnh.mvc.share.xls.validator;

import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.DateWrap;
import com.cnh.frame.wraps.XlsWrap;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/23
 */
public abstract class DateXlsValidation extends XlsValidation {

    protected String format;

    @Override
    public XlsValidation verify(HSSFCell hssfCell) throws Exception {
        String cellValue = XlsWrap.getCellValue(hssfCell);
        Assist.notNull(DateWrap.parse(cellValue, format), message);
        return this;
    }

    public XlsValidation setFormat(String format) {
        this.format = format;
        return this;
    }
}
