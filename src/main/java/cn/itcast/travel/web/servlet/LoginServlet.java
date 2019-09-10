package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.CheckCodeUtil;
import cn.itcast.travel.util.MyBeanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        boolean check = CheckCodeUtil.check(request, response);
        if (!check){
            return;
        }

        ///1.获取数据
        //2.封装对象
        User user = MyBeanUtils.populateUser(request);
        //3.调用service查询
        UserService userService = new UserServiceImpl();
        User u = userService.login(user);

        ResultInfo info = new ResultInfo();
        //4.判断用户对象是否为null
        if (u == null){
            //用户名或密码错误！
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }

        //5.判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())){
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("用户尚未激活，请先激活！");
        }

        //6.判断登录成功
        if (u != null && "Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);//登录成功标记
            //登录成功
            info.setFlag(true);
        }

        //响应数据
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");

        objectMapper.writeValue(response.getOutputStream(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
