package com.srm.coreframework.report;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srm.coreframework.util.CommonConstant;


@Service
public class ReportGeneratorFactory  {

	@Autowired
	PdfReportGenerator pdfReportGenerator;

	@Autowired
	ExcelReportGenerator excelReportGenerator;

	public ByteArrayInputStream excelPDFReportWithoutParam(List<Object[]> commonDomainArray, String[] cols, String type)
			throws IOException {
		ByteArrayInputStream byteArrayInputStream = null;
		if (CommonConstant.EXCEL.equalsIgnoreCase(type)) {// Excel Class
			byteArrayInputStream = excelReportGenerator.excelReportWithoutParam(commonDomainArray, cols);
		} else if (CommonConstant.PDF.equalsIgnoreCase(type)) {// PDF Class
			byteArrayInputStream = pdfReportGenerator.pdfReportWithoutParam(commonDomainArray, cols);
		}

		return byteArrayInputStream;
	}
}
