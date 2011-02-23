package war.webapp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Orientation;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import war.webapp.model.DayDuty;

public class GeneratorToExcel{

	private Locale locale;
	private String filename;
	private List<DayDuty> duties;



	public GeneratorToExcel(Locale locale, List<DayDuty> duties, String filename, String floor, String month) {
		this.locale = locale;
		this.filename = filename;
		this.duties = duties;

	}

	public GeneratorToExcel() {
	}

	public void download(String fileName, HttpServletResponse response, HttpSession session) throws IOException{
		BufferedInputStream from = null;
		try {
		   File file = new File(fileName); // Р В·Р Т‘Р ВµРЎРѓРЎРЉ fileName - РЎС“Р В¶Р Вµ РЎР‚Р ВµР В°Р В»РЎРЉР Р…РЎвЂ№Р в„– Р С—РЎС“РЎвЂљРЎРЉ РЎвЂљР С‘Р С—Р В° file:///se3511_pool2/s/home/results/user/12.txt
		   if (file.exists()) {
		         int pz = fileName.lastIndexOf('/');
		         String shortFileName=fileName.substring(pz+1);
		         response.setContentLength((int) file.length());
		         response.setContentType("application/octet-stream");
		         response.setDateHeader("Expires", System.currentTimeMillis());
		         response.setHeader("Cache-Control", "must-revalidate");
		         response.setHeader("Accept-Ranges", "none");
		         response.setDateHeader("Last-Modified", file.lastModified());
		         response.setHeader("Content-disposition", "attachment;filename="+shortFileName);

		         int bufferSize = 64 * 1024;
		         from = new BufferedInputStream(new FileInputStream(file), bufferSize * 2);
		         BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		         byte[] bufferFile = new byte[bufferSize];
		         int startPosition = 0;
		         int endPosition = 0;
		         for (int i = 0; ; i++)  {
		                  int len = from.read(bufferFile);
		                  if (len < 0)
		                        break;
		                endPosition = startPosition + len;  
                    out.write(bufferFile, startPosition, endPosition);
                    startPosition = endPosition;
                }
                out.flush();
                FacesContext.getCurrentInstance().responseComplete();
            } else {
                response.getWriter().println(
                        "requested file " + fileName + " not found");
            }
        } finally {
            if (from != null) {
                from.close();
                from = null;
            }
        }
    }

	private void createTemplate( WritableSheet sheet, String month, String floor, String starosta, String vospetka) throws WriteException {
		createHeader(sheet, month, floor);
		createContent(sheet, duties, starosta, vospetka);

	}

	private void createHeader(WritableSheet sheet, String month, String floor) throws WriteException{

		WritableFont arial16ptBold =
			new WritableFont(WritableFont.TIMES, 16, WritableFont.NO_BOLD);

		WritableFont arial14pt =
			new WritableFont(WritableFont.TIMES, 14, WritableFont.NO_BOLD);

		WritableCellFormat	arial16Format = new WritableCellFormat(arial16ptBold);

		WritableCellFormat	arial14Format = new WritableCellFormat(arial14pt);

		//Р Р†РЎвЂ№РЎР‚Р В°Р Р†Р Р…Р С‘Р Р†Р В°Р Р…Р С‘Р Вµ Р С—Р С• РЎвЂ Р ВµР Р…РЎвЂљРЎР‚РЎС“
		arial16Format.setAlignment(Alignment.CENTRE);
		//Р С—Р ВµРЎР‚Р ВµР Р…Р С•РЎРѓ Р С—Р С• РЎРѓР В»Р С•Р Р†Р В°Р С� Р ВµРЎРѓР В»Р С‘ Р Р…Р Вµ Р С—Р С•Р С�Р ВµРЎвЂ°Р В°Р ВµРЎвЂљРЎРѓРЎРЏ

		arial16Format.setWrap(false);

		arial14Format.setAlignment(Alignment.LEFT);
		//Р С—Р ВµРЎР‚Р ВµР Р…Р С•РЎРѓ Р С—Р С• РЎРѓР В»Р С•Р Р†Р В°Р С� Р ВµРЎРѓР В»Р С‘ Р Р…Р Вµ Р С—Р С•Р С�Р ВµРЎвЂ°Р В°Р ВµРЎвЂљРЎРѓРЎРЏ
		arial14Format.setWrap(false);

		//Р С—Р С•Р Р†Р С•РЎР‚Р С•РЎвЂљ РЎвЂљР ВµР С”РЎРѓРЎвЂљР В°
		arial16Format.setOrientation(Orientation.HORIZONTAL);
//		arial18BoldFormat.setIndentation(0);

		int headerRow = 0;
		int headerColumn = 4;

		 sheet.mergeCells(headerColumn, headerRow, 6, headerRow);
		 sheet.mergeCells(headerColumn, headerRow + 1, 6, headerRow + 1);
		 sheet.mergeCells(headerColumn, headerRow + 2, 6, headerRow + 2);
		 sheet.mergeCells(headerColumn, headerRow + 3, 6, headerRow + 3);

		 sheet.setRowView(4, 50);

			int distCol = 1;
//			int distRow = 9;
//			String empty = "";
//
			sheet.setColumnView(distCol-1, 10);
			sheet.setColumnView(distCol, 20);
			sheet.setColumnView(distCol + 1, 10);
			sheet.setColumnView(distCol + 2, 8);
			sheet.setColumnView(distCol + 3, 22);
			sheet.setColumnView(distCol + 4, 9);
			sheet.setColumnView(distCol + 5, 8);

		//Р Т‘Р С•Р В±Р В°Р Р†Р В»Р ВµР Р…Р С‘Р Вµ РЎРЏРЎвЂЎР ВµР в„–Р С”Р С‘ Р вЂ”Р В°Р С–Р С•Р В»Р С•Р Р†Р С”Р В°
		 sheet.mergeCells(0, 5, 6, 5);
		 sheet.mergeCells(0, 6, 6, 6);
		 sheet.mergeCells(0, 7, 6, 7);

		 sheet.setRowView(9, 50);

		Label label1 = new Label(0, 5, "Р вЂњРЎР‚Р В°РЎвЂћР С‘Р С” Р Т‘Р ВµР В¶РЎС“РЎР‚РЎРѓРЎвЂљР Р†", arial16Format);
		Label label2 = new Label(0, 6, "Р С—Р С• " + floor + " РЎРЊРЎвЂљР В°Р В¶РЎС“", arial16Format);
		Label label3 = new Label(0, 7, "Р Р…Р В° " + month + " " +
				Calendar.getInstance().get(Calendar.YEAR) + " Р С–Р С•Р Т‘Р В°", arial16Format);

		sheet.setRowView(8, 50);

		Label label11 = new Label(4, 0, "         Р Р€ Р Сћ Р вЂ™ Р вЂў Р В  Р вЂ“ Р вЂќ Р С’ Р В®", arial14Format);
		Label label12 = new Label(4, 1, "     Р вЂ”Р В°Р Р†Р ВµР Т‘РЎС“РЎР‹РЎвЂ°Р В°РЎРЏ Р С•Р В±РЎвЂ°Р ВµР В¶Р С‘РЎвЂљР С‘РЎРЏ  РІвЂћвЂ“ 1", arial14Format);
		Label label13 = new Label(4, 2, "     ____________ Р СњР В°РЎС“Р С�Р С•Р Р†Р В° Р РЋ.Р вЂє.", arial14Format);
		Label label14 = new Label(4, 3, "     \"_____\" __________ 2010 Р С–.", arial14Format);

		try {
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);

			sheet.addCell(label11);
			sheet.addCell(label12);
			sheet.addCell(label13);
			sheet.addCell(label14);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		}
	}

	private void createContent(WritableSheet sheet, List<DayDuty> duties, String starosta, String vosptka) throws WriteException{
		WritableFont fontHeader =
			new WritableFont(WritableFont.TIMES, 7, WritableFont.NO_BOLD);
		WritableCellFormat headerFormat = new WritableCellFormat(fontHeader);
		headerFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		headerFormat.setAlignment(Alignment.CENTRE);
		headerFormat.setWrap(true);

		WritableFont fontData =
			new WritableFont(WritableFont.TIMES, 11, WritableFont.NO_BOLD);
		WritableCellFormat dataFormat = new WritableCellFormat(fontData);
		dataFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		dataFormat.setAlignment(Alignment.CENTRE);
		dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		dataFormat.setWrap(true);

		WritableFont fontPData =
			new WritableFont(WritableFont.TIMES, 14, WritableFont.NO_BOLD);
		WritableCellFormat pdataFormat = new WritableCellFormat(fontPData);

		int distCol = 0;
		int distRow = 10;

		sheet.mergeCells(distCol + 1, distRow, distCol + 3,distRow);
		sheet.mergeCells(distCol + 4, distRow, distCol + 6,distRow);

		sheet.mergeCells(distCol, distRow, distCol,distRow + 1);

		//Р Т‘Р С•Р В±Р В°Р Р†Р В»Р ВµР Р…Р С‘Р Вµ РЎРЏРЎвЂЎР ВµР в„–Р С”Р С‘ Р вЂ”Р В°Р С–Р С•Р В»Р С•Р Р†Р С”Р В°

		Label hpart03 = new Label(distCol +1 , distRow, "1-РЎРЏ РЎРѓР С�Р ВµР Р…Р В° (8.00 - 16.00)", dataFormat);
		Label hpart04 = new Label(distCol + 4 , distRow, "2-РЎРЏ РЎРѓР С�Р ВµР Р…Р В° (16.00 - 24.00)", dataFormat);
//		Label hpart1 = new Label(distCol , distRow + 1, "Р вЂќР ВµР Р…РЎРЉ", headerFormat);
		Label hpart2 = new Label(distCol  , distRow , "Р В§Р С‘РЎРѓР В»Р С•", headerFormat);
		Label hpart3 = new Label(distCol + 1 , distRow + 1, "Р В¤Р пїЅР С›", headerFormat);
		Label hpart4 = new Label(distCol + 2 , distRow + 1, "Р вЂњРЎР‚РЎС“Р С—Р С—Р В°", headerFormat);
		Label hpart5 = new Label(distCol + 3 , distRow + 1, "Р С™Р С•Р С�Р Р…Р В°РЎвЂљР В°", headerFormat);
		Label hpart6 = new Label(distCol + 4 , distRow + 1, "Р В¤Р пїЅР С›", headerFormat);
		Label hpart7 = new Label(distCol + 5 , distRow + 1, "Р вЂњРЎР‚РЎС“Р С—Р С—Р В°", headerFormat);
		Label hpart8 = new Label(distCol + 6 , distRow + 1, "Р С™Р С•Р С�Р Р…Р В°РЎвЂљР В°", headerFormat);

		int dataRow = distRow + 2;

		for(DayDuty duty : duties){

			Calendar dutyDate = duty.getDate();
//			Label data00 = new Label(distCol , dataRow, dutyDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.ALL_STYLES, locale), headerFormat);
			Label data01 = new Label(distCol  , dataRow,""+dutyDate.get(Calendar.DAY_OF_MONTH), headerFormat);
			Label data11 = new Label(distCol + 1 , dataRow, duty.getFirstUser().getLastName() + " "
                    + duty.getFirstUser().getFirstName(), dataFormat);
			Label data12 = new Label(distCol + 2 , dataRow, duty.getFirstUser().getUniversityGroup(), dataFormat);
			Label data13 = new Label(distCol + 3 , dataRow, duty.getFirstUser().getAddress().getHostelRoom() + "", dataFormat);
			Label data21 = new Label(distCol + 4 , dataRow,duty.getSecondUser().getLastName() + " "
                    + duty.getSecondUser().getFirstName(), dataFormat);
			Label data22 = new Label(distCol + 5 , dataRow, duty.getSecondUser().getUniversityGroup(), dataFormat);
			Label data23 = new Label(distCol + 6 , dataRow, duty.getSecondUser().getAddress().getHostelRoom() + "", dataFormat);

//			sheet.addCell(data00);
			sheet.addCell(data01);
			sheet.addCell(data11);
			sheet.addCell(data12);
			sheet.addCell(data13);
			sheet.addCell(data21);
			sheet.addCell(data22);
			sheet.addCell(data23);

			dataRow ++;
		}

		sheet.setRowView(dataRow, 220);

		sheet.mergeCells(distCol, dataRow + 1, distCol + 2,dataRow + 1);

		sheet.mergeCells(distCol + 4, dataRow + 1, distCol + 6,dataRow + 1);

		sheet.setRowView(dataRow + 2, 50);

		sheet.mergeCells(distCol, dataRow+3, distCol + 2,dataRow+3);
		sheet.mergeCells(distCol, dataRow+4, distCol + 2,dataRow+4);

		sheet.mergeCells(distCol + 4, dataRow+4, distCol + 6,dataRow+4);

		Label pdata01 = new Label(distCol  , dataRow + 1,"CРЎвЂљР В°РЎР‚Р С•РЎРѓРЎвЂљР В° РЎРЊРЎвЂљР В°Р В¶Р В°", pdataFormat);
		Label pdata11 = new Label(distCol + 4 , dataRow + 1, "    _________"+starosta, pdataFormat);
		Label pdata12 = new Label(distCol  , dataRow  + 3, "Р РЋР С•Р С–Р В»Р В°РЎРѓР С•Р Р†Р В°Р Р…Р С•:", pdataFormat);
		Label pdata13 = new Label(distCol , dataRow + 4, "Р вЂ™Р С•РЎРѓР С—Р С‘РЎвЂљР В°РЎвЂљР ВµР В»РЎРЉ Р С•Р В±РЎвЂ°Р ВµР В¶Р С‘РЎвЂљР С‘РЎРЏ РІвЂћвЂ“1", pdataFormat);
		Label pdata21 = new Label(distCol + 4 , dataRow + 4, "    _________" + vosptka, pdataFormat);

		try {
			sheet.addCell(hpart03);
			sheet.addCell(hpart04);
//			sheet.addCell(hpart1);
			sheet.addCell(hpart2);
			sheet.addCell(hpart3);
			sheet.addCell(hpart4);
			sheet.addCell(hpart5);
			sheet.addCell(hpart6);
			sheet.addCell(hpart7);
			sheet.addCell(hpart8);

			sheet.addCell(pdata01);
			sheet.addCell(pdata11);
			sheet.addCell(pdata12);
			sheet.addCell(pdata13);
			sheet.addCell(pdata21);

		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}

	public void generate(String month, String floor, String starosta, String vosptka) {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(locale);
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filename), ws);
			WritableSheet sheet = workbook.createSheet("Duty", 0);

			createTemplate(sheet, month, floor,starosta, vosptka);

			workbook.write();
			workbook.close();
		}catch (FileNotFoundException e){
			System.err.println("Р вЂ”Р В°Р С”РЎР‚Р С•Р в„–РЎвЂљР Вµ РЎвЂћР В°Р в„–Р В» : " + filename);
		}catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}
}

