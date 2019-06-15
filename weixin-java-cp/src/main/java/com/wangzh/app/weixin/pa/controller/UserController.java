package com.wangzh.app.weixin.pa.controller;

import com.wangzh.app.commons.result.R;
import com.wangzh.app.weixin.pa.model.entity.InventorySummaryEntity;
import com.wangzh.app.weixin.pa.model.entity.InventorySummaryExcel;
import com.wangzh.app.weixin.pa.model.entity.UserInfoEntity;
import com.wangzh.app.weixin.pa.service.system.UserInfoService;
import com.wangzh.app.weixin.pa.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Description:
 * @CreatedDate:2019-03-26 10:15
 * @Author:wangzh
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户基础信息
     *
     * @param userID
     * @return
     */
    @GetMapping(value = "/info", produces = "application/json;charset=UTF-8")
    public R getUserInfo(@RequestParam(name = "userID") Integer userID) {
        logger.info("[getUserInfo] 参数：userID ->{}", userID);
        UserInfoEntity userInfo = userInfoService.getUserInfoByID(userID);
        return R.ok("success").put("data", userInfo);
    }

    @PostMapping(value = "/excel")
    public R exportSummaryToExcel(HttpServletRequest request, HttpServletResponse response) {
        List<InventorySummaryEntity> list = getInventorySummaryList("8845");

        try {
            // 进销差额合计
            Map<String, DoubleSummaryStatistics> collect = list.stream()
                    .collect(Collectors.groupingBy(InventorySummaryEntity::getDrugType,
                            Collectors.summarizingDouble(InventorySummaryEntity::getStorageDiversityMoney)));
            // 求和
            double summaryMoney;
            List<InventorySummaryEntity> summaryList;
            DoubleSummaryStatistics summaryStatistics;
            Map<String, String> drugTypeMap = setDrugTypeList();
            List<InventorySummaryExcel> excelList = new ArrayList<>();
            for (Map.Entry<String, String> entry : drugTypeMap.entrySet()) {
                summaryStatistics = collect.get(entry.getKey());
                if (null == summaryStatistics)
                    continue;
                summaryMoney = summaryStatistics.getSum();
                // 过滤类型数据
                summaryList = list.stream().filter(x -> x.getDrugType().equals(entry.getKey()))
                        .collect(Collectors.toList());
                excelList.add(setInventorySummaryExcel(entry.getKey(), entry.getValue(), summaryMoney, summaryList));
            }
            // 生成数据流
            createSummaryExcelData("8845", "东莞市万江医院西药房盘点汇总表", excelList, response);
            return R.ok("success");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("[exportSummaryToExcel]导出excel失败：{}", ex.getMessage());
            return R.error(-18, "导出失败");
        }
    }

    private List<InventorySummaryEntity> getInventorySummaryList(String summaryID) {
        List<InventorySummaryEntity> list = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            list.add(setInventorySummary(summaryID, "1001" + i, "虫草" + i, "P"));
        }
        for (int j = 0; j < 3; j++) {
            list.add(setInventorySummary(summaryID, "2001" + j, "花朵" + j, "C"));
        }
        return list;
    }

    private InventorySummaryEntity setInventorySummary(String summaryID, String drugID, String drugName, String drugType) {
        InventorySummaryEntity entity = new InventorySummaryEntity();
        entity.setSummaryID(summaryID);
        entity.setStorageDeptID("66739");
        entity.setDrugID(drugID);
        entity.setDrugName(drugName);
        entity.setDrugType(drugType);
        entity.setStorageDiversityMoney(10.45);
        return entity;
    }


    /**
     * @param summaryID
     * @param excelTitle
     * @param list
     * @param response
     * @throws Exception
     */
    private void createSummaryExcelData(String summaryID, String excelTitle, List<InventorySummaryExcel> list,
                                        HttpServletResponse response) throws Exception {
        if (null == list || list.size() <= 0)
            return;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        // 表头样式
        HSSFCellStyle styleTitle = ExcelUtils.createCellStyle(workbook, 1);
        // 标题样式
        HSSFCellStyle styleHeader = ExcelUtils.createCellStyle(workbook, 2);
        // 提示区域样式
        HSSFCellStyle style1 = ExcelUtils.createCellStyle(workbook, 3);
        // 数据样式
        HSSFCellStyle style = ExcelUtils.createCellStyle(workbook, 4);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String[] excelHeader = {"药品编码", "药品名称", "规格", "散装单位", "库存数", "盘点数", "零售价", "零售金额", "进货价", "进货金额", "进销差额"};

        // 横向合并第一行单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excelHeader.length - 1));
        // 第一行
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) (15.625 * 40));
        row.setHeightInPoints(40F);// 行高
        HSSFCell cell = ExcelUtils.createCell(row, 0, CellType.STRING, styleTitle, excelTitle);

        // 第二行（空一行）
        row = sheet.createRow(2);
        cell = ExcelUtils.createCell(row, 0, CellType.STRING, style1, "单据编号：" + summaryID);
        cell = ExcelUtils.createCell(row, 2, CellType.STRING, style1, "盘点人：");
        cell = ExcelUtils.createCell(row, 6, CellType.STRING, style1, "打印时间：" + date.format(dateTimeFormatter));

        // 第三行 表头标题
        row = sheet.createRow(3);
        for (int i = 0; i < excelHeader.length; i++) {
            cell = ExcelUtils.createCell(row, i, CellType.STRING, styleHeader, excelHeader[i]);
            // 设置列宽50
            sheet.setColumnWidth(i, (int) ((50 + 0.72) * 128));
        }

        // 数据行
        int rowIndex = 0;
        List<InventorySummaryEntity> summaryList;
        for (int i = 0; i < list.size(); i++) {
            summaryList = list.get(i).getList();
            logger.info("[createSummaryExcelData] i：{}", i);
            row = sheet.createRow(i + 4 + rowIndex);
            cell = ExcelUtils.createCell(row, 0, CellType.STRING, style,
                    list.get(i).getTypeName() + "（合计：" + setRoundScale(list.get(i).getSummaryMoney(), 2) + "元）");

            logger.info("[createSummaryExcelData] summaryList length：{}", summaryList.size());
            if (null == summaryList || summaryList.size() <= 0)
                continue;
            rowIndex += summaryList.size();
            logger.info("[createSummaryExcelData] rowIndex：{}", rowIndex);
            for (int j = 0; j < summaryList.size(); j++) {
                row = sheet.createRow(j + i + 5);
                cell = ExcelUtils.createCell(row, 0, CellType.STRING, style, summaryList.get(j).getDrugID());
                cell = ExcelUtils.createCell(row, 1, CellType.STRING, style, summaryList.get(j).getDrugName());
                cell = ExcelUtils.createCell(row, 2, CellType.STRING, style, summaryList.get(j).getDrugSpecs());
                // 散装单位
                cell = ExcelUtils.createCell(row, 3, CellType.STRING, style, summaryList.get(j).getDrugBulkUnit());
                // 库存数
                cell = ExcelUtils.createCell(row, 4, CellType.NUMERIC, style, summaryList.get(j).getStorageQTY());
                // 盘点数
                cell = ExcelUtils.createCell(row, 5, CellType.NUMERIC, style, summaryList.get(j).getInventoryQTY());
                // 零售价
                cell = ExcelUtils.createCell(row, 6, CellType.NUMERIC, style,
                        summaryList.get(j).getStorageRetailPrice());
                // 零售金额
                cell = ExcelUtils.createCell(row, 7, CellType.NUMERIC, style,
                        summaryList.get(j).getStorageRetailMoney());
                // 进货价
                cell = ExcelUtils.createCell(row, 8, CellType.NUMERIC, style,
                        summaryList.get(j).getStoragePurchasePrice());
                // 进货金额
                cell = ExcelUtils.createCell(row, 9, CellType.NUMERIC, style,
                        summaryList.get(j).getStoragePurchaseMoney());
                // 进销差额
                cell = ExcelUtils.createCell(row, 10, CellType.NUMERIC, style,
                        summaryList.get(j).getStorageDiversityMoney());
            }
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(excelTitle.getBytes("UTF-8"), "ISO8859-1") + date.format(dateTimeFormatter) + ".xls");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
        outputStream.close();

    }


    /**
     * 保留小数位
     */
    private double setRoundScale(double value, int fix) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(fix, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 设置导出数据源
     *
     * @param drugType
     * @param typeName
     * @param summaryMoney
     * @param list
     * @author wangzh 2019/06/14 15:35
     */
    private InventorySummaryExcel setInventorySummaryExcel(String drugType, String typeName, double summaryMoney,
                                                           List<InventorySummaryEntity> list) {
        InventorySummaryExcel model = new InventorySummaryExcel();
        model.setDrugType(drugType);
        model.setTypeName(typeName);
        model.setSummaryMoney(summaryMoney);
        model.setList(list);
        return model;
    }

    /**
     * 药品类型
     *
     * @author wangzh 2019/06/04 09:30
     */
    private Map<String, String> setDrugTypeList() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("M", "材料");
        map.put("P", "西药");
        map.put("C", "中草药");
        map.put("Z", "中成药");
        map.put("Y", "疫苗");
        map.put("T", "化验材料");
        map.put("F", "放射材料");
        map.put("Q", "其他材料");
        map.put("D", "低值易耗品");
        map.put("ZR", "植入器材");
        map.put("SM", "自主耗材");
        map.put("GZ", "高值耗材");
        map.put("ZX", "中成药（西）");
        map.put("ZZ", "中成药（中）");
        return map;
    }
}
