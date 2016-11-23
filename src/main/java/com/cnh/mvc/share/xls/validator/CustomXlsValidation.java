package com.cnh.mvc.share.xls.validator;

import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.XlsWrap;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/23
 */
public class CustomXlsValidation extends XlsValidation {

    private Handler handler;

    public CustomXlsValidation(Handler handler) {
        this.handler = handler;
    }

    @Override
    public XlsValidation verify(HSSFCell hssfCell) throws Exception {
        String cellValue = XlsWrap.getCellValue(hssfCell);
        Assist.threw(!handler.verify(cellValue), handler.getMessage());
        return this;
    }


    public abstract class Handler {

        private String message;

        public Handler(String message) {
            this.message = message;
        }

        public String getMessage(){
            return this.message;
        };

        public abstract boolean verify(String cellValue) throws Exception;

    }
}
