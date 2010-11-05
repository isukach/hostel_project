package war.webapp.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Semaphore;




import jxl.*;
import jxl.format.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.biff.*;
import war.webapp.model.DayDuty;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		// TODO Auto-generated constructor stub
	}

	public void download(String fileName, HttpServletResponse response, HttpSession session) throws IOException{
		BufferedInputStream from = null;
		try {
		   File file = new File(fileName); // здесь fileName - уже реальный путь типа file:///se3511_pool2/s/home/results/user/12.txt
		   if (file.exists()) {
		         int pz = fileName.lastIndexOf('/');
		         String shortFileName=fileName.substring(pz+1);
		         response.setContentLength((int) file.length());
		         String mimeType = session.getServletContext().getMimeType(fileName);
		         response.setContentType("application/vnd.ms-excel;");
		         response.setDateHeader("Expires", System.currentTimeMillis());
		         response.setHeader("Cache-Control", "must-revalidate");
		         response.setHeader("Accept-Ranges", "none");
		         response.setDateHeader("Last-Modified", file.lastModified());
		         response.setHeader("Content-disposition", "attachment;filename="+shortFileName);

		         int bufferSize = 64 * 1024;
		         from = new BufferedInputStream(new FileInputStream(file), bufferSize * 2);
		         ServletOutputStream out = response.getOutputStream();
		         byte[] bufferFile = new byte[bufferSize];
		         for (int i = 0; ; i++)  {
		                  int len = from.read(bufferFile);
		                  if (len < 0)
		                        break;
		                  out.write(bufferFile, 0, len);
		         }
		         out.flush();
		   } else {
		        response.getWriter().println("requested file "+fileName + " not found");
		   }
		}   finally{
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

		//выравнивание по центру
		arial16Format.setAlignment(Alignment.CENTRE);
		//перенос по словам если не помещается

		arial16Format.setWrap(false);

		arial14Format.setAlignment(Alignment.LEFT);
		//перенос по словам если не помещается
		arial14Format.setWrap(false);

		//поворот текста
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

		//добавление ячейки Заголовка
		 sheet.mergeCells(0, 5, 6, 5);
		 sheet.mergeCells(0, 6, 6, 6);
		 sheet.mergeCells(0, 7, 6, 7);

		 sheet.setRowView(9, 50);

		Label label1 = new Label(0, 5, "График дежурств", arial16Format);
		Label label2 = new Label(0, 6, "по " + floor + " этажу", arial16Format);
		Label label3 = new Label(0, 7, "на " + month + " " +
				Calendar.getInstance().get(Calendar.YEAR) + " года", arial16Format);

		sheet.setRowView(8, 50);

		Label label11 = new Label(4, 0, "         У Т В Е Р Ж Д А Ю", arial14Format);
		Label label12 = new Label(4, 1, "     Заведующая общежития  № 1", arial14Format);
		Label label13 = new Label(4, 2, "     ____________ Наумова С.Л.", arial14Format);
		Label label14 = new Label(4, 3, "     \"_____\" __________ 2010 г.", arial14Format);

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
		String empty = "";

		sheet.mergeCells(distCol + 1, distRow, distCol + 3,distRow);
		sheet.mergeCells(distCol + 4, distRow, distCol + 6,distRow);

		sheet.mergeCells(distCol, distRow, distCol,distRow + 1);

		//добавление ячейки Заголовка

		Label hpart03 = new Label(distCol +1 , distRow, "1-я смена (8.00 - 16.00)", dataFormat);
		Label hpart04 = new Label(distCol + 4 , distRow, "2-я смена (16.00 - 24.00)", dataFormat);
//		Label hpart1 = new Label(distCol , distRow + 1, "День", headerFormat);
		Label hpart2 = new Label(distCol  , distRow , "Число", headerFormat);
		Label hpart3 = new Label(distCol + 1 , distRow + 1, "ФИО", headerFormat);
		Label hpart4 = new Label(distCol + 2 , distRow + 1, "Группа", headerFormat);
		Label hpart5 = new Label(distCol + 3 , distRow + 1, "Комната", headerFormat);
		Label hpart6 = new Label(distCol + 4 , distRow + 1, "ФИО", headerFormat);
		Label hpart7 = new Label(distCol + 5 , distRow + 1, "Группа", headerFormat);
		Label hpart8 = new Label(distCol + 6 , distRow + 1, "Комната", headerFormat);


		int dataRow = distRow + 2;

		for(DayDuty duty : duties){

			Calendar dutyDate = Calendar.getInstance();
			dutyDate.setTime(duty.getDate());
//			Label data00 = new Label(distCol , dataRow, dutyDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.ALL_STYLES, locale), headerFormat);
			Label data01 = new Label(distCol  , dataRow,""+dutyDate.get(Calendar.DAY_OF_MONTH), headerFormat);
			Label data11 = new Label(distCol + 1 , dataRow, duty.getFirstUser().getLastName() + " "
                    + duty.getFirstUser().getFirstName(), dataFormat);
			Label data12 = new Label(distCol + 2 , dataRow, duty.getFirstUserLocation().getUniversityGroup(), dataFormat);
			Label data13 = new Label(distCol + 3 , dataRow, duty.getFirstUserLocation().getRoom(), dataFormat);
			Label data21 = new Label(distCol + 4 , dataRow,duty.getSecondUser().getLastName() + " "
                    + duty.getSecondUser().getFirstName(), dataFormat);
			Label data22 = new Label(distCol + 5 , dataRow, duty.getSecondUserLocation().getUniversityGroup(), dataFormat);
			Label data23 = new Label(distCol + 6 , dataRow, duty.getSecondUserLocation().getRoom(), dataFormat);

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

		Label pdata01 = new Label(distCol  , dataRow + 1,"Cтароста этажа", pdataFormat);
		Label pdata11 = new Label(distCol + 4 , dataRow + 1, "    _________"+starosta, pdataFormat);
		Label pdata12 = new Label(distCol  , dataRow  + 3, "Согласовано:", pdataFormat);
		Label pdata13 = new Label(distCol , dataRow + 4, "Воспитатель общежития №1", pdataFormat);
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
			System.err.println("Закройте файл : " + filename);
		}catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}
}

