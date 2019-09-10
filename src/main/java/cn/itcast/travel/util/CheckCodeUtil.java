package cn.itcast.travel.util;

import cn.itcast.travel.domain.ResultInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckCodeUtil {
    public static boolean check(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //验证码校验
        String check = request.getParameter("check");
        //从session获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能用一次
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo resultInfo = new ResultInfo();
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误，注册失败!");
            //将ResultInfo对象序列化为json格式数据
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            //将json、数据写回客户端
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return false;
        }else{
            return true;
        }
    }
}
