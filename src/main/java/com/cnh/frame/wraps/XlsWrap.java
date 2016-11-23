package com.cnh.frame.wraps;

import com.cnh.frame.crud.exception.BaseException;
import com.cnh.frame.crud.utils.Assist;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.*;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
public class XlsWrap {

    /**
     * 如果单元格为null则初始化一个
     * @param row
     * @param cellNum
     * @return
     */
    public static Cell initCellIfNull(Row row, int cellNum) {
        Cell cell = row.getCell(cellNum);
        if (cell == null) {
            cell = row.createCell(cellNum);
        }
        return cell;
    }


    /**
     * 取得单元格，且必须不为null，否则抛出异常
     * @param row
     * @param cellNum
     * @param errorMsg
     * @return
     * @throws BaseException
     */
    public static Cell getCellMustNotNull(Row row, int cellNum, String errorMsg) throws BaseException {
        Cell cell = row.getCell(cellNum);
        Assist.notNull(cell, errorMsg);
        Assist.threw(StringUtils.isBlank(getCellValue(cell)), errorMsg);
        return cell;
    }


    public static String getCellValueMustNotNull(Row row, int cellNum, String errorMsg) throws BaseException {
        return getCellValue(getCellMustNotNull(row, cellNum, errorMsg));
    }

    /**
     * 取单元格的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        if(cell == null) {
            return "" ;
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        String value = null ;
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC :
                value = String.valueOf(cell.getNumericCellValue()) ;
                break ;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue() ;
                break ;
            case Cell.CELL_TYPE_FORMULA:
                double numericValue = cell.getNumericCellValue() ;
                value = String.valueOf(numericValue) ;
                break ;
            case Cell.CELL_TYPE_BLANK:
                value = StringUtils.EMPTY ;
                break ;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue()) ;
                break ;
            case Cell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue()) ;
                break ;
            default:value = StringUtils.EMPTY ;break ;
        }
        return StringUtils.trim(value);
    }

    /**
     * 设置正确单元格的文本
     * @param workbook
     * @param cell
     * @param text
     */
    public static void setRightCellText (Workbook workbook, Cell cell, String text) {
        cell.setCellStyle(getRightCellStyle(workbook));

        text = String.valueOf(text);
        if (text != null) {
            RichTextString richString = null;
            try {
                richString = new HSSFRichTextString(text);
                richString.applyFont(getRightCellFont(workbook));
            } catch (ClassCastException cce) {
//                richString = new XSSFRichTextString(text);
//                richString.applyFont(getRightCellFont(workbook));
            }
            cell.setCellValue(richString);
        }
    }

    /**
     * 设置错误单元格的文本
     * @param workbook
     * @param cell
     * @param text
     */
    public static void setWrongCellText (Workbook  workbook, Cell cell, String text) {
        cell.setCellStyle(getRightCellStyle(workbook));

        text = String.valueOf(text);
        if (text != null) {
            RichTextString richString = null;
            try {
                richString = new HSSFRichTextString(text);
                richString.applyFont(getWrongCellFont(workbook));
            } catch (ClassCastException cce) {
//                richString = new XSSFRichTextString(text);
//                richString.applyFont(getWrongCellFont(workbook));
            }
            cell.setCellValue(richString);
        }
    }

    /**
     * 正确单元格的样式
     * @param workbook
     * @return
     */
    public static CellStyle getRightCellStyle(Workbook  workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setFont(getRightCellFont(workbook));

        return style;
    }

    /**
     * 正确单元格的字体
     * @param workbook
     * @return
     */
    public static Font getRightCellFont (Workbook  workbook) {
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_NORMAL);
        return font;
    }

    /**
     * 错误单元格的样式
     * @param workbook
     * @return
     */
    public static CellStyle getWrongCellStyle(Workbook  workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setFont(getWrongCellFont(workbook));

        return style;
    }

    /**
     * 错误单元格的字体
     * @param workbook
     * @return
     */
    public static Font getWrongCellFont (Workbook  workbook) {
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        return font;
    }

    /**
     * 单元格内容是否为空
     * @param cell
     * @return
     */
    public static boolean isCellBlank (Cell cell) {
        return cell == null || StringUtils.isBlank(getCellValue(cell));
    }


}
