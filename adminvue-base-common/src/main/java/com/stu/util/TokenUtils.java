package com.stu.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Flobby
 * @program : exam-demo
 * @description :
 * @create : 2023-10-25 18:11
 **/
@Slf4j
public class TokenUtils {

    /**
     * 生成 AccessToken
     */
    public static String generator() {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 获取 AccessToken
     */
//    public static String getAccessToken(HttpServletRequest request) {
//        String accessToken = request.getHeader("Authorization");
//        if (StrUtil.isBlank(accessToken)) {
//            accessToken = request.getParameter("accessToken");
//        }
//        return accessToken;
//    }
    /**
     * 封装一个从 token 中提取 userId 的方法
     * @param token
     * @return
     */
    public static Integer getUserIdFromToken(String token) {
        log.info("token" + token);
        Integer userId = 0;
        String noToken = "no-token";
        if (!noToken.equals(token)) {
            JSONObject jsonObject = JwtUtil.getJSONObject(token);
            log.info("解析到 token 的 json 数据为：{}", jsonObject);
            userId = Integer.parseInt(jsonObject.get("id").toString());
        } else {
            log.info("没有 token");
        }
        return userId;
    }
}
