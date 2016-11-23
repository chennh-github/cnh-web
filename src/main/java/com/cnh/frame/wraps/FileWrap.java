package com.cnh.frame.wraps;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
public class FileWrap {

    private static Logger LOGGER = LoggerFactory.getLogger(FileWrap.class);

    /**
     * 判断是否为合法的后缀名
     * @param multipartRequest
     * @param exts
     * @return
     * @throws Exception
     */
    public static boolean isValidExt(MultipartHttpServletRequest multipartRequest, String[] exts) throws Exception{
        List<String> extList = new ArrayList<String>();
        if (exts == null) {
            throw new Exception("exts can not be null.");
        } else {
            for (String ext: exts) {
                extList.add(StringUtils.trim(ext.toLowerCase()));
            }
        }

        if (multipartRequest != null) {
            for (Iterator<String> iterator = multipartRequest.getFileNames(); iterator.hasNext();) {
                String name = iterator.next();
                MultipartFile multipartFile = multipartRequest.getFile(name);
                String originalFileName = multipartFile.getOriginalFilename();
                int index = -1;
                if ((index = originalFileName.lastIndexOf(".")) == -1) {
                    return false;
                } else {
                    String ext = originalFileName.substring(index + 1).toLowerCase();
                    if (!extList.contains(ext)) {
                        return false;
                    }
                }
            }
        } else {
            throw new Exception("multipartRequest can not be null.");
        }
        return true;
    }

    /**
     * 判断是否为合法的文件大小
     * @param multipartRequest
     * @param expectingSize
     * @return
     * @throws Exception
     */
    public static boolean isValidSize(MultipartHttpServletRequest multipartRequest, long expectingSize) throws Exception{
        if (expectingSize <= 0) {
            throw new Exception("expectingSize must be a digits.");
        }
        if (multipartRequest != null) {
            for (Iterator<String> iterator = multipartRequest.getFileNames(); iterator.hasNext(); ) {
                String name = iterator.next();
                MultipartFile multipartFile = multipartRequest.getFile(name);
                if (multipartFile.getSize() > expectingSize) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 判断path对应的File/目录是否存在
     * @param path
     * @return
     * @date: 2014-7-31 下午4:31:32
     * @version: v4.3.0.00.00
     * @history:
     */
    public static boolean isExit(String path) {
        boolean b = StringUtils.isNotBlank(path) && (new File(path)).exists();
        LOGGER.debug(" File path : { " + path + " } is exits : " + b);
        return b;
    }
    /**
     * 判断filePath对应的File是否为文件
     * @param filePath
     * @return
     * @date: 2014-7-31 下午4:31:56
     * @version: v4.3.0.00.00
     * @history:
     */
    public static boolean isFile(String filePath) {
        boolean b = FileWrap.isExit(filePath) && (new File(filePath)).isFile();
        LOGGER.debug(" File path : { " + filePath + " } is File : " + b);
        return b;
    }
    /**
     * 判断dirPath对应的File是否为目录
     * @param dirPath
     * @return
     * @date: 2014-7-31 下午4:33:20
     * @version: v4.3.0.00.00
     * @history:
     */
    public static boolean isDirectory(String dirPath) {
        boolean b = FileWrap.isExit(dirPath) && (new File(dirPath)).isDirectory();
        LOGGER.debug("Directory path : { " + dirPath + " } is Directory : " + b);
        return b;
    }
    /**
     * 当且仅当filePath对应的File为文件时，删除该文件
     * @param filePath
     * @return
     * @date: 2014-7-31 下午4:33:44
     * @version: v4.3.0.00.00
     * @history:
     */
    public static boolean deleteFile(String filePath) {
        boolean b = FileWrap.isFile(filePath) && (new File(filePath)).delete();
        LOGGER.debug("Delete file : { " + filePath + " } , result : " + b);
        return b;
    }
    /**
     * 当且仅当dirPath为目录时，删除该目录(包括目录下的子文件)
     * @param dirPath
     * @return
     * @date: 2014-7-31 下午4:34:38
     * @version: v4.3.0.00.00
     * @history:
     */
    public static boolean deleteDirectory(String dirPath) {
        boolean b = FileWrap.isDirectory(dirPath);
        if(b){
            File[] fileList = new File(dirPath).listFiles();
            for (File file : fileList) {
                if(file.isFile()){
                    file.delete();
                } else if(file.isDirectory()){
                    FileWrap.deleteDirectory(file.getAbsolutePath());
                }
            }
            b = (new File(dirPath)).delete();
        }
        LOGGER.debug("Delete directory : { " + dirPath + " } , result : " + b);
        return b;
    }
    /**
     * 删除目录/文件
     * @param path
     * @return
     * @date: 2014-7-31 下午5:02:50
     * @version: v4.3.0.00.00
     * @history:
     */
    public static boolean deleteFileOrDirectory(String path){
        boolean b = FileWrap.isExit(path);
        if(b){
            if(FileWrap.isFile(path)){
                b = FileWrap.deleteFile(path);
            } else if(FileWrap.isDirectory(path)){
                b = FileWrap.deleteDirectory(path);
            }
        }
        LOGGER.debug("Delete file or directory : { " + path + " }, result : " + b);
        return b;
    }
    /**
     * 组装文件路径 = 文件相对路径{splitFlag} + 系统文件分隔符{java.io.File.separator} + 
     *              标识符{splitFlag} + "_" + System.currentTimeMillis() + "." + 后缀名{ext}
     * @param fileRelativePath
     * @param splitFlag
     * @param ext
     * @return
     * @date: 2014-7-31 下午4:38:29
     * @version: v4.3.0.00.00
     * @history:
     */
    public static String formFilePath(String fileRelativePath, String splitFlag, String ext){
        StringBuilder filePath = new StringBuilder();
        if(StringUtils.isNotBlank(fileRelativePath)){
            filePath.append(fileRelativePath).append(File.separator);
        }
        if(StringUtils.isNotBlank(splitFlag)){
            long timestamp = System.currentTimeMillis();
            String time = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date(timestamp));
            filePath.append(splitFlag).append("_" + time + "_" + System.currentTimeMillis());
        }
        if(StringUtils.isNotBlank(ext)){
            filePath.append(".").append(ext);
        }
        LOGGER.debug("Form filepath : { fileRelativePath : " + fileRelativePath + ", splitFlag : " + splitFlag + ", ext : " + ext + " } ");
        LOGGER.debug("File separator : { " + File.separator + " }");
        LOGGER.debug("To filePath : " + filePath.toString());
        return filePath.toString();
    }
    /**
     * 创建一个新的文件，为WEB文件上传时需要生成新文件而使用
     * @param fileRelativePath
     * @param splitFlag
     * @param multipartFile
     * @return
     * @date: 2014-7-31 下午4:42:07
     * @version: v4.3.0.00.00
     * @history:
     */
    public static File newFile(String fileRelativePath, String splitFlag, MultipartFile multipartFile) {
        String ext = "";
        int index = -1;
        if(null != multipartFile){
            String originalName = multipartFile.getOriginalFilename();
            if((index = originalName.lastIndexOf(".")) > -1){
                ext = originalName.substring(index + 1);
            }
        }
        String filePath = FileWrap.formFilePath(fileRelativePath, splitFlag, ext);
        File newFile = new File(filePath);
        if(!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                LOGGER.error("NewFile create new file for web fail , e : {} ", e.getMessage() );
            }
        }
        assert multipartFile != null;
        LOGGER.debug("NewFile properties: { multipartFile : " + multipartFile.toString() + ", ext : " + ext + ", filePath : " + filePath + " }");
        return newFile;
    }
    /**
     * 载入path对应的File，如果不存在则创建一个
     * @param path
     * @return
     * @throws IOException
     * @date: 2014-7-31 下午4:43:28
     * @version: v4.3.0.00.00
     * @history:
     */
    public static File newFile(String path) throws IOException {
        LOGGER.debug("NewFile path : { " + path + " }");
        if(!StringUtils.isBlank(path)){
            File file = new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            return file;
        }
        return null;
    }
    /**
     * 将输入流写入到outputFile文件中
     * @param inputStream
     * @param outputFile
     * @throws IOException
     * @date: 2014-7-31 下午4:46:13
     * @version: v4.3.0.00.00
     * @history:
     */
    public static void saveFileFromStream(InputStream inputStream, File outputFile) throws IOException  {
        FileOutputStream fos = new FileOutputStream(outputFile);
        byte[] bytes = new byte[1024];
        int byteread = -1;
        while((byteread = inputStream.read(bytes)) != -1){
            fos.write(bytes, 0, byteread);
            fos.flush();
        }
        fos.close();
        inputStream.close();
        LOGGER.debug("SaveFileFromStream properties : { cacheSize: 1024, fileName: " + outputFile.getName() + " }");
    }
    /**
     * 保存文件，为WEB的文件上传而使用
     * @param multipartFile
     * @param fileRelativePath
     * @param splitFlag
     * @throws IOException
     * @date: 2014-7-31 下午4:46:49
     * @version: v4.3.0.00.00
     * @history:
     */
    public static File saveFile(MultipartFile multipartFile, String fileRelativePath, String splitFlag) throws IOException {
        File outputFile = FileWrap.newFile(fileRelativePath, splitFlag, multipartFile);
        FileWrap.saveFileFromStream(multipartFile.getInputStream(), outputFile);
        LOGGER.debug("SaveFile properties : { multipartFile : " + multipartFile + ", fileRelativePath : " + fileRelativePath + ", splitFlag" + splitFlag + " }");
        return outputFile;
    }
    /**
     * 当且仅当fromFile为文件时，移动该文件到toFile
     * @param fromFile
     * @param toFile
     * @param delFromFile 是否删除原文件
     * @date: 2014-7-31 下午4:47:22
     * @version: v4.3.0.00.00
     * @history:
     */
    public static void moveFileTo(File fromFile, File toFile, boolean delFromFile){
        if(fromFile.isFile()){
            fromFile.renameTo(toFile);
            if(delFromFile){
                fromFile.delete();
            }
            LOGGER.debug("Move fromFile { " + fromFile.getName() + " } to { " + toFile.getName() + " }, delFromFile : " + delFromFile);
        } else {
            LOGGER.debug("Move fromFile is not a file");
        }
    }
    /**
     * 移动目录/文件
     * @param fromDir
     * @param toDir
     * @param delFromDir
     * @date: 2014-7-31 下午4:50:19
     * @version: v4.3.0.00.00
     * @history:
     */
    public static void moveDirectoryTo(File fromDir, File toDir, boolean delFromDir) {
        if(fromDir.isDirectory()){
            File[] files = fromDir.listFiles();
            assert files != null;
            for (File file : files) {
                String filePath = toDir.getAbsolutePath() + File.separator + file.getName();
                try {
                    FileWrap.moveDirectoryTo(file, FileWrap.newFile(filePath), delFromDir);
                } catch (IOException e) {
                    LOGGER.error("Move file fail: { fromFile : " + file.getName() + ", toFile : " + filePath + " }");
                    LOGGER.error("message : {}", e.getMessage());
                }
            }
        } else {
            FileWrap.moveFileTo(fromDir, toDir, delFromDir);
        }

    }
    /**
     * 移动文件/目录
     * @param fromFile
     * @param toFile
     * @date: 2014-7-31 下午4:50:43
     * @version: v4.3.0.00.00
     * @history:
     */
    public static void moveTo(File fromFile, File toFile){
        if(null == fromFile){
            LOGGER.debug("fromFile is null");
            return ;
        }
        if(null == toFile){
            LOGGER.debug("toFile is null");
            return ;
        }
        if(fromFile.isFile()){
            FileWrap.moveFileTo(fromFile, toFile, true);
        } else if(fromFile.isDirectory()){
            FileWrap.moveDirectoryTo(fromFile, toFile, true);
        }
    }
    /**
     * 移动fromPath对应的File到toPath对应的File
     * @param fromPath
     * @param toPath
     * @date: 2014-7-31 下午4:51:02
     * @version: v4.3.0.00.00
     * @history:
     */
    public static void moveTo(String fromPath, String toPath){
        if(!StringUtils.isBlank(fromPath) && StringUtils.isBlank(toPath)){
            try {
                FileWrap.moveTo(new File(fromPath), FileWrap.newFile(toPath));
            } catch (IOException e) {
                LOGGER.error("MoveTo create toFile fail , e : ", e.getMessage());
            }
        }
        LOGGER.debug("MoveTo properties : { fromPath :" + fromPath + ", toPath : " + toPath);
    }
    /**
     * 复制目录/文件
     * @param fromFile
     * @param toFile
     * @date: 2014-7-31 下午4:56:30
     * @version: v4.3.0.00.00
     * @history:
     */
    public static void copyTo(File fromFile, File toFile){
        if(null == fromFile){
            LOGGER.debug("fromFile is null");
            return ;
        }
        if(null == toFile){
            LOGGER.debug("toFile is null");
            return ;
        }
        if(fromFile.isFile()){
            FileWrap.moveFileTo(fromFile, toFile, false);
        } else if(fromFile.isDirectory()){
            FileWrap.moveDirectoryTo(fromFile, toFile, false);
        }
    }
    /**
     * 复制fromFile对应的File到toPath对应的File
     * @param fromPath
     * @param toPath
     * @date: 2014-7-31 下午4:56:49
     * @version: v4.3.0.00.00
     * @history:
     */
    public static void copyTo(String fromPath, String toPath){
        if(!StringUtils.isBlank(fromPath) && StringUtils.isBlank(toPath)){
            try {
                FileWrap.copyTo(new File(fromPath), FileWrap.newFile(toPath));
            } catch (IOException e) {
                LOGGER.error("MoveTo create toFile fail , e : ", e.getMessage());
            }
        }
        LOGGER.debug("MoveTo properties : { fromPath :" + fromPath + ", toPath : " + toPath);
    }
    /**
     * 转换工程上下文的路径为系统文件路径，通常用于获取webapp下的图片的系统文件路径
     * @param request
     * @param relativePath 包含工程上下文路径的字符串
     * @return
     * @date: 2014-10-20 下午5:10:42
     * @version: v4.3.0.00.00
     * @history:
     */
    public static String getFileSystemPathFromContext(HttpServletRequest request, String relativePath){
        String contextPath = request.getContextPath();
        if(!StringUtils.isBlank(relativePath) && !relativePath.contains(contextPath)){
            String contextFilePath = request.getSession().getServletContext().getRealPath(File.separator);
            if(null == contextFilePath || StringUtils.isBlank(contextFilePath)){
                contextFilePath = request.getSession().getServletContext().getRealPath("/");
            }
            return contextFilePath + relativePath.replace(contextPath, "");
        }else{
            return relativePath;
        }
    }

}
