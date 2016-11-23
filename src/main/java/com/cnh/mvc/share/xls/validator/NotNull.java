package com.cnh.mvc.share.xls.validator;

import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.XlsWrap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/23
 */
public class NotNull extends XlsValidation {

    protected String message = "不能为空";

    @Override
    public XlsValidation verify(HSSFCell hssfCell) throws Exception {
        Assist.notNull(hssfCell, message);
        Assist.threw(StringUtils.isBlank(XlsWrap.getCellValue(hssfCell)), message);
        return this;
    }
}
