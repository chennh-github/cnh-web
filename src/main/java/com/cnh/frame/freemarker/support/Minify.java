package com.cnh.frame.freemarker.support;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.wraps.CollectionWrap;
import com.cnh.frame.wraps.FileWrap;
import com.cnh.frame.wraps.RequestWrap;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 静态资源合并
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/11/24
 */
public class Minify {

    /**
     * 已映射合并的资源文件
     */
    private static final Map<String, String> mappedSource = new HashMap<String, String>();

    /**
     * 合并后文件存储目录
     */
    private static final String MINIFY_PATH = "/minify/";


    /**
     * JS
     *
     * @param srcs
     * @param srcValue
     * @return
     * @throws IOException
     */
    public static String minifyJS(List<String> srcs, String srcValue) throws IOException {
        return minify(srcValue, srcs, "js", new Handle() {
            @Override
            public String handle(String source, String content) {
                return content + ";\n\n";
            }
        });
    }

    /**
     * CSS
     *
     * @param hrefs
     * @param srcValue
     * @return
     * @throws IOException
     */
    public static String minifyCSS(List<String> hrefs, String srcValue) throws IOException {
        return minify(srcValue, hrefs, "css", new Handle() {
            @Override
            public String handle(String source, String content) {
                String result = content;
                if (StringUtils.isNotBlank(content)) {
                    Pattern p = Pattern.compile("url\\s*\\(\\s*(['|\"]?(.+?)['|\"]?)\\s*\\)", Pattern.CASE_INSENSITIVE);
                    Matcher m;
                    while ((m = p.matcher(content)).find()) {
                        String wrappedUrl = m.group(0);
                        String url = m.group(2);
                        if (StringUtils.isNotBlank(url)) {
                            content = content.replace(wrappedUrl, "");
                            result = result.replace(wrappedUrl, "'" + parseUrlToAbsolute("/" + source, url) + "'");
                        }
                    }
                }
                return result + "\n\n";
            }
        });
    }

    /**
     * 取得文件资源地址
     *
     * @param srcValue
     * @param srcs
     * @param ext
     * @param handle
     * @return
     * @throws IOException
     */
    public static String minify(String srcValue, List<String> srcs, String ext, Handle handle) throws IOException {
        String srcKey = ext + "-" + CollectionWrap.join(srcs);
        String mappedSrcValue = null;
        boolean isFileMinify = false;
        if (mappedSource.containsKey(srcKey)) {
            mappedSrcValue = mappedSource.get(srcKey);
        }
        // 如果合并文件不存在则重新合并
        if (StringUtils.isNotBlank(mappedSrcValue)) {
            if (FileWrap.isExist(RequestWrap.getRealPath(CONSTANT.WEBAPP + mappedSrcValue))) {
                isFileMinify = true;
            }
        }
        return isFileMinify ? mappedSrcValue : mergeSources(srcKey, srcValue, srcs, ext, handle);
    }

    /**
     * 合并文本资源文件
     *
     * @param srcKey
     * @param srcValue
     * @param srcs
     * @param ext
     * @param handle
     * @return
     * @throws IOException
     */
    private static String mergeSources(String srcKey, String srcValue, List<String> srcs, String ext, Handle handle) throws IOException {
        StringBuilder srcSource = new StringBuilder();
        if (StringUtils.isBlank(srcValue)) {
            srcValue = MINIFY_PATH + RequestWrap.getRequest().getRequestURI() + "/" + System.currentTimeMillis() + "." + ext;
        } else {
            srcValue = MINIFY_PATH + srcValue + "/" + System.currentTimeMillis() + "." + ext;
        }
        for (String src : srcs) {
            src = StringUtils.trim(src);
            srcSource.append(handle.handle(src, FileWrap.getFileContent(RequestWrap.getRealPath(CONSTANT.WEBAPP + src))));
        }
        FileWrap.writeToFile(RequestWrap.getRealPath(CONSTANT.WEBAPP + srcValue), srcSource.toString());
        mappedSource.put(srcKey, srcValue);
        return srcValue;
    }

    /**
     * CSS文件中的相对地址转换为绝对地址
     *
     * @param src
     * @param url
     * @return
     */
    private static String parseUrlToAbsolute(String src, String url) {
        if (StringUtils.isNotBlank(src) && StringUtils.isNotBlank(url)) {
            if (isAbsoluteUrl(url)) {
                return url;
            } else {
                List<String> srcList = new ArrayList<String>(Arrays.asList(src.split("/")));
                List<String> urlList = new ArrayList<String>(Arrays.asList(url.split("/")));
                List<String> urlNameList = new ArrayList<String>();

                srcList.remove(srcList.size() - 1);

                for (String s : urlList) {
                    if ("..".equals(s)) {
                        // 非法使用相对地址
                        if (srcList.size() <= 0) {
                            return url;
                        }
                        srcList.remove(srcList.size() - 1);
                    } else if (".".equals(s)) {

                    } else {
                        urlNameList.add(s);
                    }
                }
                srcList.addAll(urlNameList);
                return CollectionWrap.join(srcList, "/");
            }
        }
        return url;
    }

    private static boolean isAbsoluteUrl(String url) {
        return StringUtils.startsWithAny(url, "http", "https", "/");
    }


    public interface Handle {
        public String handle(String source, String content);
    }


    public static void main(String[] args) {
        String css = "url('/static/css/aaa/a.css' )";
        String[] urls = new String[]{"../../images/aaa/a.jpg", "./images/aaa/a.jpg", "/images/aaa/a.jpg", "images/aaa/a.jpg"};

        for (String url : urls) {
            System.out.println(parseUrlToAbsolute(css, url));
        }

    }

}
