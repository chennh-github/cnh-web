package com.cnh.mvc.share.xls.validator;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/23
 */
public class XlsValidator {


    public static void verify(HSSFCell hssfCell, XlsValidation... xlsValidations) throws Exception{
        for (XlsValidation xlsValidation: xlsValidations) {
            xlsValidation.verify(hssfCell);
        }
    }

}
