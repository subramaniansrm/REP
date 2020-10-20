package com.srm.coreframework.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
@Service
public class PdfReportGenerator  {
	private static final Logger LOG = LoggerFactory.getLogger(PdfReportGenerator.class);

	public ByteArrayInputStream pdfReportWithoutParam(List<Object[]> commondomainArray,String[] cols) {
		LOG.info("Inside Pdf Report Generation");
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfPTable table = new PdfPTable(cols.length);
            table.setSpacingBefore(30);
            table.setSpacingAfter(30);
            
            table.setWidthPercentage(75f);
            float widthArray[] = new float[cols.length];
            int colwidth = 1;
            for(int j=0;j<cols.length;j++) {
            	widthArray[j] = colwidth ;
            	colwidth++;
            }
            table.setWidths(widthArray);
            
            
    
    

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,8,Font.BOLDITALIC);
            // Header
 			for (int col = 0; col < cols.length; col++) {
 				 PdfPCell hcell;
 	            hcell = new PdfPCell(new Phrase(cols[col], headFont));
 	            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
 	           table.addCell(hcell);
 			}
 			LOG.info("Pdf Report Columns Generated");
			LOG.info("List Data Size-----"+commondomainArray.size());
 			Font fontcabecatable =  FontFactory.getFont(FontFactory.HELVETICA,8,Font.NORMAL);
 			
 			for (Object[] obj : commondomainArray) {
 				 PdfPCell cell;
				for (int k = 0; k < obj.length; k++) {
					if (obj[k] !=null) {
						    cell = new PdfPCell(new Phrase(obj[k].toString(),fontcabecatable));
			                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			                cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			                table.addCell(cell);
				}
				}
				}
			
            PdfWriter.getInstance(document, out);
       
            document.open();
   
            document.add(table);
        } catch (DocumentException ex) {
        
        	 LOG.info("Pdf Report Generation error" +ex.getMessage());
        }
        finally {
            document.close();
            LOG.info("....PDF Report Generated....");
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


}
