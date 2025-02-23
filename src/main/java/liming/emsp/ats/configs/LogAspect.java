package liming.emsp.ats.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Log Aspect
 *
 */
@Aspect
@Component
public class LogAspect {
    private ObjectMapper mapper;

    public LogAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private final static Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(public * liming.emsp.ats.controller..*.*(..)) " )
    public Object controllerMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String[] paramNames = ((MethodSignature) signature).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        String requestBody = getRequestBody(paramNames, paramValues);
        log.info("API pre invoke:  method: {}, url: {}, request: {}, client ip: {}", request.getMethod(),
                request.getRequestURL(), requestBody, request.getRemoteAddr());
        Object result = null;
        try {
            result = joinPoint.proceed(paramValues);
        } catch (Throwable e) {
            log.info("API invoke with Exception:  method: {}, url: {}, request: {}, client ip: {}, the method took {} ms, Exception: {}", request.getMethod(),
                    request.getRequestURL(), requestBody, request.getRemoteAddr(), System.currentTimeMillis() - start, e);
            throw e;
        }
        log.info("API invoke successfully:  method: {}, url: {}, response: {}, client ip: {}, the method took {} ms", request.getMethod(),
                request.getRequestURL(), result, request.getRemoteAddr(), System.currentTimeMillis() - start);
        return result;
    }


    public String getRequestBody(String[] paramNames, Object[] paramValues) {
        StringBuilder requestBody = new StringBuilder();
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength > 0) {
            requestBody.append("[");
            try {
                for (int i = 0; i < paramLength ; i++) {
                    Object paramValue = paramValues[i];
                    if (paramValue instanceof HttpServletRequest || paramValue instanceof HttpServletResponse){
                        continue; // Infinite recursion (StackOverflowError)
                    }else {
                        requestBody.append(paramNames[i]).append("=").append(mapper.writeValueAsString(paramValue)).append(",");
                    }
                }
                requestBody.deleteCharAt(requestBody.length()-1);
                requestBody.append("]");
            }catch (Exception e){
                log.warn("LogAspect failed to parse method parameter!",e);
            }
        }
        return requestBody.toString();
    }
}
