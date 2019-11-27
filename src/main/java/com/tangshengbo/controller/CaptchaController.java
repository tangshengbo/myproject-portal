package com.tangshengbo.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Tangshengbo
 *
 * @author Tangshengbo
 * @date 2019/11/27
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private Producer captchaProducer;

    /**
     * 获取图片验证码
     *
     * @return 验证码
     * @throws IOException
     */
    @GetMapping("getCaptchaCode")
    public void getCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //生成验证码文本
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        logger.info("生成验证码文本===={}", capText);
        //利用生成的字符串构建图片
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 前端输入的验证码与生成的对比
     *
     * @param captchaCode 用户输入验证码
     */
    @RequestMapping("/checkCaptchaCode")
    public void checkCaptchaCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("captchaCode") String captchaCode)
            throws IOException {
        logger.info("页面输入验证码===={}", captchaCode);

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        String generateCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String result;
        if (captchaCode.equals(generateCode)) {
            result = "验证成功";
        } else {
            result = "输入错误";
        }
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
    }

}
