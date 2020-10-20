package com.srm.repg.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.srm.repg.form.RealEstateDTO;
import com.srm.repg.form.RealEstateDetailsDTO;
import com.srm.repg.form.SampleDataDTO;



@Service
public class ExcelReadDataService {
	
	@Value("${uploadfilePath}")
	private String uploadPath;
	
	public static final String SAMPLE_XLSX_FILE_PATH = "D://test.xlsx";
	
	@Transactional
	public List<RealEstateDTO> getListOfData(String date){
		List<RealEstateDTO> listRealEstateDTO=new ArrayList<>();
		 
		try{
			String files="";
			File folder= new File(uploadPath+date+"/");
			File[] listOfFiles = folder.listFiles();
			if(listOfFiles!=null){
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			    	files=file.getName();
			    }
			}
		
			String filePath=folder.getAbsoluteFile()+"\\"+files;
			 FileInputStream inputStream = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(inputStream);
			 DataFormatter dataFormatter = new DataFormatter();
			 int sheetNo=0;
			 for(Sheet sheet: workbook) {
				 sheetNo++;
				 RealEstateDTO realEstateDTO=new RealEstateDTO();
				 if(sheetNo==1){
					 realEstateDTO.setColorCode("Gray"); 
				 }
				 if(sheetNo==2){
					 realEstateDTO.setColorCode("Blue"); 
				 }
				 if(sheetNo==3){
					 realEstateDTO.setColorCode("Yellow"); 
				 }
				 if(sheetNo==4){
					 realEstateDTO.setColorCode("Red"); 
				 }
				 if(sheetNo==5){
					 realEstateDTO.setColorCode("Green"); 
				 }
				 if(sheetNo==6){
					 realEstateDTO.setColorCode("Black"); 
				 }
				 if(sheetNo==7){
					 realEstateDTO.setColorCode("DarkBlue"); 
				 }
				 if(sheetNo==8){
					 realEstateDTO.setColorCode("DarkYellow"); 
				 }
				 if(sheetNo==9){
					 realEstateDTO.setColorCode("DarkRed"); 
				 }
				 if(sheetNo==10){
					 realEstateDTO.setColorCode("DarkGreen"); 
				 }
				 List<RealEstateDetailsDTO>listDetails=new ArrayList<>(); 
				 for (Row row: sheet) {
					
						/*// it will give you count of row which is used or filled
						short lastcolumnused = row.getLastCellNum();
						int colnum = 0;
						for (int i = 0; i < lastcolumnused; i++) {
						String name=row1.getCell(i).getStringCellValue();
						if (name.equalsIgnoreCase("住所")) {
						colnum = i;
						break;
						}
						}*/
					 RealEstateDetailsDTO realEstateDetailsDTO=new RealEstateDetailsDTO();
			        	if(row.getRowNum()>0){
			        		
			            for(int i=0;i<=27;i++) {
			            	 String cellValue = dataFormatter.formatCellValue(row.getCell(i));
			            	if(i==2){
			            		realEstateDetailsDTO.setPropertyName(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	if(i==3){
			            		realEstateDetailsDTO.setPrice(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	if(i==4){
			            		realEstateDetailsDTO.setSalesAmount(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	if(i==5){
			            		realEstateDetailsDTO.setPrice1(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	
			            	if(i==6){
			            		realEstateDetailsDTO.setOccuipedArea(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	if(i==7){
			            		realEstateDetailsDTO.setLandArea(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	if(i==8){
			            		realEstateDetailsDTO.setUnitPrice(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	if(i==9){
			            		realEstateDetailsDTO.setBuildingArea(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	if(i==10){
			            		realEstateDetailsDTO.setUnitPrice1(new String(cellValue.getBytes(StandardCharsets.UTF_8)));	
			            	}
			            	if(i==11){
			            		realEstateDetailsDTO.setPrefectures(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==12){
			            		realEstateDetailsDTO.setLocation1(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==13){
			            		realEstateDetailsDTO.setLocation2(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==14){
			            		realEstateDetailsDTO.setLocation3(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	realEstateDetailsDTO.setAddress(realEstateDetailsDTO.getPrefectures()+","+realEstateDetailsDTO.getLocation1()+","+realEstateDetailsDTO.getLocation2()+","+realEstateDetailsDTO.getLocation3());
			            	if(i==15){
			            		realEstateDetailsDTO.setAlongtheLine(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==16){
			            		realEstateDetailsDTO.setStation(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==17){
			            		realEstateDetailsDTO.setTraffic(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==18){
			            		realEstateDetailsDTO.setFloorPlan(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==19){
			            		realEstateDetailsDTO.setAge(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==20){
			            		realEstateDetailsDTO.setBuildingCoverage(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==21){
			            		realEstateDetailsDTO.setFloorAreaRatio(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==22){
			            		realEstateDetailsDTO.setRent(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			            	if(i==23){
			            		realEstateDetailsDTO.setYield(new String(cellValue.getBytes(StandardCharsets.UTF_8)));
			            	}
			                if(i==26){
			                	String cellValues = dataFormatter.formatCellValue(row.getCell(26));
			                	if(!cellValues.isEmpty())
			                	realEstateDetailsDTO.setLatitude(cellValues);
			                }
			                if(i==27){
			                	String cellValue1 = dataFormatter.formatCellValue(row.getCell(27));
			                	if(!cellValue1.isEmpty())
			                	realEstateDetailsDTO.setLongitude(cellValue1);
			                	 if(realEstateDetailsDTO.getLongitude()==null && realEstateDetailsDTO.getLatitude()==null){
						                if(realEstateDetailsDTO.getAddress()!=null ){
					                		String[]latLong=getLatLongPositions(realEstateDetailsDTO.getAddress());
					                		if(latLong.length!=0){
					                			realEstateDetailsDTO.setLatitude(latLong[0]);
					                			realEstateDetailsDTO.setLongitude(latLong[1]);
					                		}
					                	}
						                } 
			                }
			               
			            }
			            listDetails.add(realEstateDetailsDTO);
			            Cell cells=row.createCell(26) ;
			        	 cells.setCellValue(realEstateDetailsDTO.getLatitude());
			        	 Cell cell1=row.createCell(27) ;
			        	 cell1.setCellValue(realEstateDetailsDTO.getLongitude());
			            }else{
			            	 Cell cells=row.createCell(26) ;
				        	 cells.setCellValue("緯度");
				        	 Cell cell1=row.createCell(27) ;
				        	 cell1.setCellValue("経度");	
			            }
			        	
			        	 
			        }
				 realEstateDTO.setListDetails(listDetails);
				 listRealEstateDTO.add(realEstateDTO);
		       }
			 inputStream.close();
			 FileOutputStream outputStream = new FileOutputStream(filePath);
	            workbook.write(outputStream);
	            outputStream.close();
			}
	            
		}catch(Exception e){
			e.printStackTrace();
		}
		return listRealEstateDTO;
		
	}
	
	public List<SampleDataDTO>getListOfDatas(){
		List<SampleDataDTO>list=new ArrayList<>();
		SampleDataDTO sampleDataDTO=new SampleDataDTO();
		sampleDataDTO.setLattitude("13.0827");
		sampleDataDTO.setLongtitude("80.2707");
		sampleDataDTO.setAddress("Chennai,India");
		SampleDataDTO sampleDataDTO1=new SampleDataDTO();
		sampleDataDTO1.setLattitude("9.9252");
		sampleDataDTO1.setLongtitude("78.1198");
		sampleDataDTO1.setAddress("Maduari,India");
		list.add(sampleDataDTO);
		list.add(sampleDataDTO1);
		
		
		return list;
	}
	
	public static String[] getLatLongPositions(String address) throws Exception
	  {
	    int responseCode = 0;
	    String api = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&key=AIzaSyCz_Mwp9Gg1-vJYyHHikkaUMTcIzZLxVY4";
	    URL url = new URL(api);
	    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	    httpConnection.connect();
	    responseCode = httpConnection.getResponseCode();
	    if(responseCode == 200)
	    {
	      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
	      Document document = builder.parse(httpConnection.getInputStream());
	      XPathFactory xPathfactory = XPathFactory.newInstance();
	      XPath xpath = xPathfactory.newXPath();
	      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
	      String status = (String)expr.evaluate(document, XPathConstants.STRING);
	      if(status.equals("OK"))
	      {
	         expr = xpath.compile("//geometry/location/lat");
	         String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         expr = xpath.compile("//geometry/location/lng");
	         String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         return new String[] {latitude, longitude};
	      }
	      else
	      {
	         throw new Exception("Error from the API - response status: "+status);
	      }
	    }
	    return null;
	  }

}
