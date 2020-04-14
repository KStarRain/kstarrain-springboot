package com.kstarrain.provider.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author: DongYu
 * @create: 2019-10-15 17:15
 * @description:
 */
public class ResponseUtils {

    /**
     * 设置response下载流
     * @param targetFileName
     * @param inputStream
     * @param response
     * @throws IOException
     */
    public static void setDownloadResponse(String targetFileName, InputStream inputStream, HttpServletResponse response) throws IOException {

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(targetFileName, StandardCharsets.UTF_8.name()));
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");

        IOUtils.copy(inputStream, response.getOutputStream());

    }

}
