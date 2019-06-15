package com.wangzh.app.weixin.pa.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description: poi 3.17版本 excel辅助类
 * @Auther:wangzh
 * @Date: 2019/06/06 09:50
 */

public abstract class ExcelUtils {
    /**
     * create cell
     *
     * @param row         行
     * @param columnIndex 列索引
     * @param type        列类型
     * @param style       列样式
     * @param value       string 类型
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int columnIndex, CellType type, HSSFCellStyle style, String value) {
        if (StringUtils.isBlank(value))
            value = "";
        HSSFCell cell = createCell(row, columnIndex, type, style);
        cell.setCellValue(value);
        return cell;
    }

    /**
     * create cell
     *
     * @param row         行
     * @param columnIndex 列索引
     * @param type        列类型
     * @param style       列样式
     * @param value       bool 类型
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int columnIndex, CellType type, HSSFCellStyle style, Boolean value) {
        if (null == value)
            value = false;
        HSSFCell cell = createCell(row, columnIndex, type, style);
        cell.setCellValue(value);
        return cell;
    }

    /**
     * create cell
     *
     * @param row         行
     * @param columnIndex 列索引
     * @param type        列类型
     * @param style       列样式
     * @param value       double 类型
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int columnIndex, CellType type, HSSFCellStyle style, Double value) {
        if (null == value)
            value = 0D;
        HSSFCell cell = createCell(row, columnIndex, type, style);
        cell.setCellValue(value);
        return cell;
    }

    /**
     * create cell
     *
     * @param row         行
     * @param columnIndex 列索引
     * @param type        列类型
     * @param style       列样式
     * @param value       date 类型
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int columnIndex, CellType type, HSSFCellStyle style, Date value) {
        if (null == value)
            value = new Date();
        HSSFCell cell = createCell(row, columnIndex, type, style);
        cell.setCellValue(value);
        return cell;
    }

    /**
     * create cell
     *
     * @param row         行
     * @param columnIndex 列索引
     * @param type        列类型
     * @param style       列样式
     * @param value       Calendar 类型
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int columnIndex, CellType type, HSSFCellStyle style,
                                      Calendar value) {
        if (null == value)
            value = Calendar.getInstance();
        HSSFCell cell = createCell(row, columnIndex, type, style);
        cell.setCellValue(value);
        return cell;
    }

    /**
     * create cell
     *
     * @param row         行
     * @param columnIndex 列索引
     * @param type        列类型
     * @param style       列样式
     * @param value       RichTextString 类型
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int columnIndex, CellType type, HSSFCellStyle style,
                                      RichTextString value) {
        HSSFCell cell = createCell(row, columnIndex, type, style);
        cell.setCellValue(value);
        return cell;
    }

    /**
     * create cell
     *
     * @param row         行
     * @param columnIndex 列索引
     * @param type        列类型
     * @param style       列样式
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int columnIndex, CellType type, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(columnIndex, type);
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * HSSF 单元格样式
     *
     * @param book excel book
     * @param type 表头 标题 内容
     * @author wangzh 2019/06/05 10:33
     */
    public static HSSFCellStyle createCellStyle(HSSFWorkbook book, int type) {
        HSSFCellStyle style = book.createCellStyle();
        HSSFFont font = book.createFont();
        font.setFontName("Consolas");// 字体
        font.setColor(IndexedColors.BLACK.index);// 字体颜色
        font.setFontHeightInPoints((short) 12);// 字体大小
        switch (type) {
            case 1:
                style.setAlignment(HorizontalAlignment.CENTER);
                font.setBold(true);// 粗体
                font.setFontHeightInPoints((short) 16);// 字体大小
                style.setFont(font);
                break;
            case 2:
                style.setAlignment(HorizontalAlignment.CENTER);
                font.setBold(true);// 粗体
                style.setFont(font);
                style.setBorderTop(BorderStyle.THIN);// 边框细线
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setTopBorderColor(IndexedColors.BLACK.index);// 边框颜色
                style.setBottomBorderColor(IndexedColors.BLACK.index);
                style.setRightBorderColor(IndexedColors.BLACK.index);
                style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                break;
            case 3:
                style.setAlignment(HorizontalAlignment.LEFT);
                style.setFont(font);
                break;
            case 4:
                style.setAlignment(HorizontalAlignment.LEFT);
                font.setFontHeightInPoints((short) 11);// 字体大小
                style.setFont(font);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setTopBorderColor(IndexedColors.BLACK.index);
                style.setBottomBorderColor(IndexedColors.BLACK.index);
                style.setRightBorderColor(IndexedColors.BLACK.index);
                style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                break;
            default:
                break;
        }
        return style;
    }
}
