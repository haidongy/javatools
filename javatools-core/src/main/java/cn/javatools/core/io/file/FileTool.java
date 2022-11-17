package cn.javatools.core.io.file;

import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * 文件上传，下载等
 *
 * @author yanghd3
 */
public class FileTool {

    /**
     * 上传MultipartFile类型的文件到制定路径
     * 返回值为上传后的文件名字，自动生成，需自行保存
     *
     * <br>
     *
     * @param file 文件
     * @param outPath 上传的路径
     * @return 服务器上保存的文件名
     * @throws IOException IO异常
     */
    public static String upload(MultipartFile file, String outPath) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis()+"."+
                originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        File dest = new File(outPath + fileName);
        if (!dest.getParentFile().canExecute()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return fileName;
    }

    /**
     * 上传InputStream类型的文件到制定路径
     *
     * <br>
     *
     * @param in 文件
     * @param outPath 上传的路径及文件名
     * @throws IOException IO异常
     */
    public static void upload(InputStream in, String outPath) throws IOException {
        File outFile = new File(outPath);
        if(!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }
        OutputStream osm = new FileOutputStream(outFile);
        IOUtils.copy(in, osm);
        in.close();
        osm.close();
    }

    /**
     * 上传File类型的文件到制定路径
     * 返回值为上传后的文件名字，自动生成，需自行保存
     *
     * <br>
     *
     * @param file 文件
     * @param outPath 上传的路径
     * @return 服务器上保存的文件名
     * @throws IOException IO异常
     */
    public static String upload(File file, String outPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        return upload(multipartFile, outPath);
    }

    /**
     * 获取文件的Base64编码
     * @param file 转换的文件
     * @return Base64编码字符串
     * @throws IOException IO异常
     */
    public static String getBase64(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStream in = new BufferedInputStream(fileInputStream);
        byte[] data = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 文件下载
     *
     * @param realName 显示的文件名
     * @param path 文件存放的路径及文件名
     * @param response response
     * @throws IOException IO异常
     */
    public static void download(String realName, String path, HttpServletResponse response) throws IOException {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStream fis = new BufferedInputStream(fileInputStream);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        outputStream.write(buffer);
        outputStream.flush();
    }
}
