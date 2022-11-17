package cn.javatools.core.io.office;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class OfficeTool {

    /**
     * word文档转换为pdf
     * @param wordPath word文档所在路径
     * @param wordName word文档名字
     * @param pdfPath pdf文档保存路径
     * @param pdfName pdf文档名字
     * @return 是否成功
     * @throws IOException IO异常
     */
    public static Boolean word2Pdf(String wordPath, String wordName, String pdfPath, String pdfName) throws IOException {
        //word转pdf
        File inputWord = new File( wordPath.concat(wordName) );
        File outputFile = new File( pdfPath.concat(pdfName) );
        InputStream docxInputStream = new FileInputStream(inputWord);
        OutputStream outputStream = new FileOutputStream(outputFile);
        IConverter converter = LocalConverter.builder().build();
        converter.convert(docxInputStream)
                .as(DocumentType.DOCX)
                .to(outputStream)
                .as(DocumentType.PDF)
                .execute();
        outputStream.close();
        return true;
    }

    /**
     * pdf文件在线预览
     *
     * @param pdfPath pdf文件路径
     * @param pdfName pdf文件名称
     * @param response response
     * @throws IOException IO异常
     */
    public static void printPdf(String pdfPath, String pdfName, HttpServletResponse response) throws IOException {
        File file = new File( pdfPath.concat(pdfName) );
        byte[] data;
        response.setHeader("Content-Disposition", "inline;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));
        FileInputStream input = new FileInputStream(file);
        data = new byte[input.available()];
        input.read(data);

        response.getOutputStream().write(data);
        input.close();
    }
}
