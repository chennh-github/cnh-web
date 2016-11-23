package com.cnh.mvc.share.xls.validator;

import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.StringWrap;
import com.cnh.frame.wraps.XlsWrap;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/23
 */
public class Digits extends XlsValidation {

    protected String message = "请输入正确的正整数";

    @Override
    public XlsValidation verify(HSSFCell hssfCell) throws Exception {
        String cellValue = XlsWrap.getCellValue(hssfCell);
        Assist.threw(StringWrap.Validate.isDigits(cellValue), message);
        return this;
    }
}
