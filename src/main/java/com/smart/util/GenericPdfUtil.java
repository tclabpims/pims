package com.smart.util;

import java.awt.*;
import java.io.*;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.lowagie.text.pdf.BaseFont;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

/**
 * Created by zcw on 2016/9/14.
 */
public final class GenericPdfUtil {

    private GenericPdfUtil() {
    }

    public static synchronized void html2Pdf(String fileName, String html){
        File pdfFile = new File("d:\\"+fileName);
        System.out.println(html);
        StringReader strReader = new StringReader(html);
        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            PD4ML pd4ml = new PD4ML();
            pd4ml.setPageInsets(new Insets(10, 20, 10, 20));
            pd4ml.setHtmlWidth(794);
            Dimension dimension = new Dimension(794, 1123);
            //pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
            pd4ml.setPageSize(dimension);
            pd4ml.useTTF("java:fonts", true);
            pd4ml.setDefaultTTFs("MSYH", "Arial", "Courier New");
            pd4ml.enableDebugInfo();
            pd4ml.render(strReader, fos);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static synchronized void createPdf(String fileName, String html){
        OutputStream os = null;
        try {
            os = new FileOutputStream("d:\\"+fileName);
            ITextRenderer renderer = new ITextRenderer(800,600);
            System.out.println("====>"+html);
            renderer.setDocumentFromString(html);

//            renderer.setDocument(html);
//            Document doc = new Document(PageSize.A5.rotate(),10,10,10,10);
//            PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream("d:\\"+fileName));
//            writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
//            doc.open();
//            InputSource is = new InputSource(new BufferedReader(new StringReader(html)));
//            org.w3c.dom.Document dom = XMLResource.load(is).getDocument().get;
//
//
            //解决中文支持问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            renderer.getDotsPerPoint();
            fontResolver.addFont("C:/WINDOWS/Fonts/SimSun.ttc",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
            fontResolver.addFont("C:/WINDOWS/Fonts/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
            // 解决图片的相对路径问题
            //BaseFont baseFont = BaseFont.createFont("STSong-Light",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            //renderer.getSharedContext().setBaseURL("file:/D:/Work/Demo2do/Yoda/branch/Yoda%20-%20All/conf/template/");
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
           os.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(os!=null)
                try {
                    os.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

        }

    }
}
