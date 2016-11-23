package com.cnh.frame.wraps;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/23
 */
public class UrlWrap {


    /**
     * currentUrl是否为parentUrls的子url
     *
     * @param currentUrl  当前url
     * @param parentUrls  包含的urls
     * @param excludeUrls 排除在外的urls
     * @return
     */
    public static boolean isChildOf(String currentUrl, String[] parentUrls, String[] excludeUrls) {
        if (StringUtils.isNotBlank(currentUrl)) {
            List<String> parentUrlList = Arrays.asList(parentUrls);
            List<String> excludeUrlList = Arrays.asList(excludeUrls);

            for (String excludeUrl : excludeUrlList) {
                if (excludeUrl.indexOf(currentUrl) == 0) {
                    return false;
                }
            }

            for (String parentUrl : parentUrlList) {
                if (parentUrl.indexOf(currentUrl) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

}
