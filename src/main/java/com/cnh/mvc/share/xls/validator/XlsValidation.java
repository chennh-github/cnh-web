package com.cnh.mvc.share.xls.validator;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/23
 */
public abstract class XlsValidation {

    protected String message;

    /**
     *  验证HSSFCell是否合法
     * @param hssfCell
     * @return
     * @throws Exception
     */
    public abstract XlsValidation verify(HSSFCell hssfCell) throws Exception;

    public XlsValidation setMessage(String message) {
        this.message = message;
        return this;
    };

}
