package swd.documents;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import swd.DAO.Factory;
import swd.DesktopApi;
import swd.logic.Firm;
import swd.logic.Invoice;
import swd.logic.Rull;
import swd.logic.S_I;

public class CreatePDF {
    
    private File file;
    private BaseFont bf;
    private Font f_title;
    private Font f_text;
    private Font f_table_text;
    private Font f_s_text;
    private Font fontNormal;
    private String encoding;
    
    private Firm firm;
    private S_I si;
    private Invoice inv;
    //private Rull rull;
    
    public CreatePDF(Long f_id, Long si_id, Long inv_id){
        try {
            firm = Factory.getInstance().getFirmDAO().getFirmById(f_id);
            si = Factory.getInstance().getS_IDAO().getS_IById(si_id);
            //rull = Factory.getInstance().getRullDAO().getIRullById(firm.getRule_id());
            inv = Factory.getInstance().getInvoiceDAO().getInvoiceById(inv_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void setFont() throws DocumentException, IOException{
        try{
            //подключаем файл шрифта, который поддерживает кириллицу
            ///usr/share/fonts/truetype/msttcorefonts/
            bf = BaseFont.createFont("/fonts/Times_New_Roman.ttf", BaseFont.IDENTITY_H , BaseFont.EMBEDDED); 
            fontNormal = FontFactory.getFont(("/fonts/arialuni.ttf"), encoding,BaseFont.EMBEDDED);
            f_title = new Font(bf, 14 );
            f_text = new Font(bf,9);
            f_table_text = new Font(bf,8);
            f_s_text = new Font(bf, 8 , Font.ITALIC );
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void make_invoice() throws DocumentException, IOException{
        setFont();
        Document doc = new Document(PageSize.A4);
        Desktop d = Desktop.getDesktop();
        try{
            
            
            file = new File("invoice.pdf");
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            Paragraph title = new Paragraph();
            title.setAlignment(Element.ALIGN_CENTER);
            title.setFont(f_title);
            title.add("Счет фактура №" + inv.getId().toString() + " от " + inv.getDate().toString());
            
            Paragraph p_bna = new Paragraph();
            p_bna.setAlignment(Element.ALIGN_CENTER);
            p_bna.setFont(f_s_text);
            p_bna.add("(БИН, наименование и адрес)");
            
            doc.add(title);
            
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Поставщик: ",f_text));
            doc.add(new Paragraph("БИН/ИИН и адрес места нахождения поставщика: ",f_text));
            doc.add(new Paragraph("ИИК поставщика: ",f_text));
            doc.add(new Paragraph("Договор (контракт) на поставку товаров (работ,услуг): " + 
                                    firm.getContract().toString(),f_text));
            doc.add(new Paragraph("Условия оплаты по договору (контракту): "  
                                    /*rull.getName()*/ ,f_text));
            doc.add(new Paragraph("Пункт назначения поставляемых товаров (работ, услуг) ",f_text));
            doc.add(new Paragraph(" "));
            
            doc.add(new Paragraph("Поставка товаров (работ, услуг) осуществлена по доверенности: ",f_text));
            doc.add(new Paragraph("Способ отправления: ",f_text));
            doc.add(new Paragraph("Товарно-транспортная накладная: ",f_text));
            doc.add(new Paragraph(" "));
            
            doc.add(new Paragraph("Грузоотправитель: ",f_text));
            doc.add(p_bna);
            doc.add(new Paragraph(" "));
            
            doc.add(new Paragraph("Грузополучатель: " + firm.getName().toString() 
                                    + " " + firm.getAddress(),f_text));
            doc.add(p_bna);
            doc.add(new Paragraph(" "));
            
            doc.add(new Paragraph("Получатель: " + firm.getName().toString() ,f_text));
            doc.add(new Paragraph("БИН/ИИН и адрес места нахождения получателя: " + 
                                    firm.getBin_iin().toString() + " " + firm.getAddress() ,f_text));
            doc.add(new Paragraph("ИИК получателя: " + firm.getIik().toString() ,f_text));
            
            
            doc.add(create_table());
            
            Phrase ph;
            
            ph = new Phrase("Руководитель: ", f_text);
            doc.add(ph);
            ph = new Phrase("ВЫДАЛ (ответственное лицо поставщика)", f_text);
            doc.add(ph);
            
            doc.add(new Paragraph("Примечание: Без печати не действительно. Оригинал (первый экземпляр) — покупателю. Копия — поставщику",f_text));
            
            doc.close();
            
            DesktopApi.open(file);
            
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }
    
    public PdfPTable create_table() throws DocumentException{
        int n = 11;
        PdfPTable table = new PdfPTable(n);
        table.setTotalWidth(new float[]{20f,100f,30f,40f,35f,70f,30f,40f,70f,30f,30f});
        table.setLockedWidth(true);
        
        table.setSpacingBefore(5f);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("№",f_table_text));
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Наименование товаров (работ, услуг)",f_table_text));
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Ед. изм",f_table_text));
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Кол-во (объем)",f_table_text));
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Цена",f_table_text));
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Стоимость товаров (работ, услуг) без НДС",f_table_text));
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("НДС",f_table_text));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Всего стоимость реализации",f_table_text));
        cell.setRowspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Акциз",f_table_text));
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Сумма",f_table_text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Ставка",f_table_text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Сумма",f_table_text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Ставка",f_table_text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        for(int i=1;i<=n;i++){
            cell = new PdfPCell(new Phrase("" + i,f_table_text));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        
        cell = new PdfPCell(new Phrase("Всего по счету: ",f_table_text));
        cell.setColspan(5);
        table.addCell(cell);
        
        for(int i=1;i<=6;i++){
            table.addCell(new Phrase(" ",f_table_text));
        }
        
        return table;
    }
    
    public void make_report() throws DocumentException, IOException{
        Document doc = new Document(PageSize.A4.rotate());
        try{
            PdfWriter.getInstance(doc, new FileOutputStream("report.pdf"));
            doc.open();
            doc.add(new Paragraph("Create PDF report"));
            doc.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}