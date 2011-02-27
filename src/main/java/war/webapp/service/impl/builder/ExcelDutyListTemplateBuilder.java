package war.webapp.service.impl.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
import war.webapp.model.DayDuty;
import war.webapp.service.builder.BaseDutyListTemplateBuilder;

public class ExcelDutyListTemplateBuilder extends BaseDutyListTemplateBuilder{

    private String filename = "temp.xsl";
    @Override
    public void createHeader(Object... params) {

        WritableSheet sheet = (WritableSheet)params[0]; 
        String month = (String)params[1]; 
        Integer floor = (Integer)params[2];
        
        WritableFont arial16ptBold =
            new WritableFont(WritableFont.TIMES, 16, WritableFont.NO_BOLD);

        WritableFont arial14pt =
            new WritableFont(WritableFont.TIMES, 14, WritableFont.NO_BOLD);

        WritableCellFormat  arial16Format = new WritableCellFormat(arial16ptBold);

        WritableCellFormat  arial14Format = new WritableCellFormat(arial14pt);

        try {
            arial16Format.setAlignment(Alignment.CENTRE);
            arial16Format.setWrap(false);

            arial14Format.setAlignment(Alignment.LEFT);
            arial14Format.setWrap(false);

            arial16Format.setOrientation(Orientation.HORIZONTAL);

            int headerRow = 0;
            int headerColumn = 4;

             sheet.mergeCells(headerColumn, headerRow, 6, headerRow);
             sheet.mergeCells(headerColumn, headerRow + 1, 6, headerRow + 1);
             sheet.mergeCells(headerColumn, headerRow + 2, 6, headerRow + 2);
             sheet.mergeCells(headerColumn, headerRow + 3, 6, headerRow + 3);

             sheet.setRowView(4, 50);

                int distCol = 1;
    //
                sheet.setColumnView(distCol-1, 10);
                sheet.setColumnView(distCol, 20);
                sheet.setColumnView(distCol + 1, 10);
                sheet.setColumnView(distCol + 2, 8);
                sheet.setColumnView(distCol + 3, 22);
                sheet.setColumnView(distCol + 4, 9);
                sheet.setColumnView(distCol + 5, 8);

             sheet.mergeCells(0, 5, 6, 5);
             sheet.mergeCells(0, 6, 6, 6);
             sheet.mergeCells(0, 7, 6, 7);

             sheet.setRowView(9, 50);

            Label label1 = new Label(0, 5, "бла бла бла", arial16Format);
            Label label2 = new Label(0, 6, "этаж " + floor + " хз что 1", arial16Format);
            Label label3 = new Label(0, 7, "хз что 2 " + month + " " +
                    Calendar.getInstance().get(Calendar.YEAR) + " хз что 3 ", arial16Format);

            sheet.setRowView(8, 50);

            Label label11 = new Label(4, 0, "         заголовок1", arial14Format);
            Label label12 = new Label(4, 1, "     заголовок2", arial14Format);
            Label label13 = new Label(4, 2, "     заголовок3", arial14Format);
            Label label14 = new Label(4, 3, "     \"_____\" __________ 2010 год", arial14Format);

            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);

            sheet.addCell(label11);
            sheet.addCell(label12);
            sheet.addCell(label13);
            sheet.addCell(label14);
            
        } catch (WriteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createContent(Object... params) {
        WritableSheet sheet = (WritableSheet)params[0];
        List<DayDuty> duties =(List<DayDuty>)params[5]; 
        String starosta = (String)params[3];
        String vosptka = (String)params[4];
        WritableFont fontHeader =
            new WritableFont(WritableFont.TIMES, 7, WritableFont.NO_BOLD);
        WritableCellFormat headerFormat = new WritableCellFormat(fontHeader);
        try {
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

            Label hpart03 = new Label(distCol +1 , distRow, "1-смена (8.00 - 16.00)", dataFormat);
            Label hpart04 = new Label(distCol + 4 , distRow, "2-смена (16.00 - 24.00)", dataFormat);
            Label hpart2 = new Label(distCol  , distRow , "хз что 21", headerFormat);
            Label hpart3 = new Label(distCol + 1 , distRow + 1, "мд...", headerFormat);
            Label hpart4 = new Label(distCol + 2 , distRow + 1, "111111", headerFormat);
            Label hpart5 = new Label(distCol + 3 , distRow + 1, "222222222", headerFormat);
            Label hpart6 = new Label(distCol + 4 , distRow + 1, "3333333333", headerFormat);
            Label hpart7 = new Label(distCol + 5 , distRow + 1, "4444444444", headerFormat);
            Label hpart8 = new Label(distCol + 6 , distRow + 1, "55555555555555", headerFormat);

            int dataRow = distRow + 2;

            for(DayDuty duty : duties){

                Calendar dutyDate = Calendar.getInstance();
                dutyDate.setTime(duty.getDate());
                Label data01 = new Label(distCol  , dataRow,""+dutyDate.get(Calendar.DAY_OF_MONTH), headerFormat);
                Label data11 = new Label(distCol + 1 , dataRow, duty.getFirstUser().getLastName() + " "
                        + duty.getFirstUser().getFirstName(), dataFormat);
                Label data12 = new Label(distCol + 2 , dataRow, duty.getFirstUser().getUniversityGroup(), dataFormat);
                Label data13 = new Label(distCol + 3 , dataRow, duty.getFirstUser().getAddress().getHostelRoom() + "", dataFormat);
                Label data21 = new Label(distCol + 4 , dataRow,duty.getSecondUser().getLastName() + " "
                        + duty.getSecondUser().getFirstName(), dataFormat);
                Label data22 = new Label(distCol + 5 , dataRow, duty.getSecondUser().getUniversityGroup(), dataFormat);
                Label data23 = new Label(distCol + 6 , dataRow, duty.getSecondUser().getAddress().getHostelRoom() + "", dataFormat);

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

            Label pdata01 = new Label(distCol  , dataRow + 1,"Какая та херня бла бла бла", pdataFormat);
            Label pdata11 = new Label(distCol + 4 , dataRow + 1, "    _________"+starosta, pdataFormat);
            Label pdata12 = new Label(distCol  , dataRow  + 3, "Еще одно поле для заполнения:", pdataFormat);
            Label pdata13 = new Label(distCol , dataRow + 4, "Последнее уж строчка этой всей канители", pdataFormat);
            Label pdata21 = new Label(distCol + 4 , dataRow + 4, "    _________" + vosptka, pdataFormat);
            
            sheet.addCell(hpart03);
            sheet.addCell(hpart04);
//          sheet.addCell(hpart1);
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
            
        } catch (WriteException ex) {
            ex.printStackTrace();
        }
   
    }

    @Override
    public void createFooter(Object... params) {
        
    }

    @Override
    public byte[] build(Object... params) {
        Integer floor=(Integer)params[0]; 
        String month = (String)params[1];
        String starosta = (String)params[2];
        String vosptka = "Фамилия_воспетки";
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru"));
        try {
            File file = new File(filename);
            WritableWorkbook workbook = Workbook.createWorkbook(file, ws);
            WritableSheet sheet = workbook.createSheet("Duty", 0);

            createTemplate(sheet, month, floor,starosta, vosptka, params[4]);

            workbook.write();
            workbook.close();
            return toByteArray(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private  byte[] toByteArray(File file) throws IOException{
        InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

}
