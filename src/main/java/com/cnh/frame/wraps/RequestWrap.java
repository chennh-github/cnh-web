package com.cnh.frame.wraps;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/8
 */
public class RequestWrap {


    private static final Logger LOGGER = LoggerFactory.getLogger(RequestWrap.class);


    /**
     * 转换HttpServletRequest.getParameterMap()为可用的Map<String, Object>对象
     * @param request
     * @return
     */
    public static Map<String, Object> getMap(HttpServletRequest request) {
        Map<String, String[]> requestParamMap = request.getParameterMap();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry: requestParamMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            if (values.length > 1) {
                paramMap.put(key, values);
            } else {
                paramMap.put(key, values[0]);
            }
            LOGGER.debug("request parameter: {} = {}", new Object[]{key, paramMap.get(key)});
        }
        return paramMap;
    }


    /**
     * 如果request中包含流则解析为map，否则使用parameterap解析
     * @param request
     * @return
     */
    public static Map<String, Object> getRequestMap(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader inputStreamReader;
        BufferedReader reader;
        if (request.getContentLength() > 0) {
            try {
                inputStreamReader = new InputStreamReader(request.getInputStream());
                reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                inputStreamReader.close();

                if (sb.length() > 0) {
                    return parseJSON2Map(JSONObject.fromObject(sb.toString()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getMap(request);
    }

    /**
     * json转map
     * @param json
     * @return
     */
    public static Map<String, Object> parseJSON2Map(JSONObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (json != null) {
            for (Object k: json.keySet()) {
                Object o = json.get(k);
                if (o instanceof JSONObject) {
                    map.put((String) k, parseJSON2Map((JSONObject)o));
                } else if (o instanceof JSONArray) {
                    map.put((String) k, ((JSONArray) o).toArray());
                } else {
                    map.put((String) k, o);
                }
            }
        }
        return map;
    }


    /**
     * 读取request请求的IP地址
     * @param request
     * @return
     */
    public String getIPAddress (HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static String getContextPath() {
        return getContextPath(getRequest());
    }

    /**
     * 取上下文路径
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return StringWrap.endBy(contextPath, "/");
    }

    /**
     * 拼接完整路径
     * @param request
     * @param relativePath
     * @return
     */
    public static String getFullPath(HttpServletRequest request, String relativePath) {
        String contextPath = getContextPath(request);
        return StringUtils.isBlank(relativePath) ? contextPath : (contextPath + relativePath);
    }

    public static String getFullPath(String relativePath) {
        HttpServletRequest request = getRequest();
        String contextPath = getContextPath(request);
        return StringUtils.isBlank(relativePath) ? contextPath : (contextPath + relativePath);
    }

    /**
     * 取文件的物理路径
     * @param relativePath
     * @return
     */
    public static String getRealPath (String relativePath) {
        String realPath = RequestWrap.class.getClassLoader().getResource("").getPath();
        return StringUtils.isBlank(relativePath) ? realPath : realPath + StringWrap.endBy(relativePath, File.separator);
    }

    /**
     * 取HttpServletrequest
     * @return
     */
    public static HttpServletRequest getRequest () {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 取HttpServletResponse
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

}
