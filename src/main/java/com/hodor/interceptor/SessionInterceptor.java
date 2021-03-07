package com.hodor.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.hodor.annotation.AccessLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author ：hodor007
 * @date ：Created in 2021/3/7
 * @description ：
 * @version: 1.0
 */
@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            //判断是否属于方法的请求
            if(handler instanceof HandlerMethod) {
                HandlerMethod hm = (HandlerMethod) handler;
                //请求的方法中是否带注解
                AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
                if(accessLimit == null) {
                    return true;
                }

                //指定时间内允许请求的次数
                int seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();

                String ip = request.getRemoteAddr();
                String key = request.getServletPath() + ":" + ip;
                System.out.println("key:" + key);

                //从redis中获取
                Integer count = (Integer) redisTemplate.opsForValue().get(key);

                if(null == count || count == -1) {
                    redisTemplate.opsForValue().set(key, 1, seconds, TimeUnit.SECONDS);
                    return true;
                }
                System.out.println("访问次数:" + count);

                if(count < maxCount) {
                    count ++;
                    redisTemplate.opsForValue().set(key, count, 0);
                    return true;
                }

                // 超出访问次数
                if (count >= maxCount) {
                    // response 返回 json 请求过于频繁请稍后再试
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    response.getWriter().write(JSONObject.toJSONString("操作过于频繁"));
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //关闭坏连接？
            RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        }
        return true;
    }
}
