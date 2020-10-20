package com.srm.coreframework.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.srm.coreframework.util.DateUtil;
import com.srm.coreframework.util.ReportFieldsDTO;


@Service
public class ExcelReportGenerator {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExcelReportGenerator.class);

	public ByteArrayInputStream excelReportWithParam(List<Object[]> schoolBoardArray, List<ReportFieldsDTO> paramList)
			throws IOException {

		String[] columns = new String[paramList.size()];
		int i = 0;
		for (ReportFieldsDTO field : paramList) {
			columns[i] = field.getViewName();
			i++;
		}

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("sheet1");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < columns.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(columns[col]);
				cell.setCellStyle(headerCellStyle);
			}
			int rowIdx = 1;
			for (Object[] obj : schoolBoardArray) {
				Row row = sheet.createRow(rowIdx++);
				for (int k = 0; k < obj.length; k++) {
					if (k < columns.length) {
						if (obj[k] != null) {
							  
							if ((obj[k] instanceof Long)) {
								row.createCell(k).setCellValue((Long) obj[k]);
							} else if ((obj[k] instanceof Short)) {
								row.createCell(k).setCellValue((Short) obj[k]);
							} else if ((obj[k] instanceof BigDecimal)) {
								row.createCell(k).setCellValue(((BigDecimal) obj[k]).toString());
							} else if ((obj[k] instanceof String)) {
								row.createCell(k).setCellValue((String) obj[k]);
							} else if ((obj[k] instanceof Boolean)) {
								row.createCell(k).setCellValue((Boolean) obj[k]);
							} else if ((obj[k] instanceof Integer)) {
								row.createCell(k).setCellValue((Integer) obj[k]);
							}
							else {
								row.createCell(k).setCellValue(DateUtil.convertLocalTimeToStr((LocalDateTime) obj[k]));
							}

						}
					}
				}
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public ByteArrayInputStream excelReportWithoutParam(List<Object[]> schoolBoardArray, String[] cols)
			throws IOException {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			
			LOG.info("Inside Excel Report Generation");
			Sheet sheet = workbook.createSheet("sheet1");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < cols.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(cols[col]);
				cell.setCellStyle(headerCellStyle);
			}
			LOG.info("Excel Report Columns Generated");
			LOG.info("List Data Size-----"+schoolBoardArray.size());
			int rowIdx = 1;
			for (Object[] obj : schoolBoardArray) {
				Row row = sheet.createRow(rowIdx++);
				for (int k = 0; k < obj.length; k++) {
					if (k < cols.length) {
						if (obj[k] != null) {
							
							if ((obj[k] instanceof Long)) {
								row.createCell(k).setCellValue((Long) obj[k]);
							} else if ((obj[k] instanceof Short)) {
								row.createCell(k).setCellValue((Short) obj[k]);
							} else if ((obj[k] instanceof BigDecimal)) {
								row.createCell(k).setCellValue(((BigDecimal) obj[k]).toString());
							} else if ((obj[k] instanceof String)) {
								row.createCell(k).setCellValue((String) obj[k]);
							} else if ((obj[k] instanceof Boolean)) {
								row.createCell(k).setCellValue((Boolean) obj[k]);
							} else if ((obj[k] instanceof Integer)) {
								row.createCell(k).setCellValue((Integer) obj[k]);
							}
							else {
								row.createCell(k).setCellValue(DateUtil.convertLocalTimeToStr((LocalDateTime) obj[k]));
							}
							
						}
					}
				}
			}
			LOG.info("....Report Generated....");
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

}
