package com.tangshengbo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * Created by Tangshengbo on 2019/1/21
 */
@Controller
@SessionAttributes("articleId")
public class FollowMeController {

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);
    private static final String[] sensitiveWords = new String[]{"k1", "s2"};

    @ModelAttribute("comment")
    private String replaceSensitiveWords(String comment) throws IOException {
        if (comment != null) {
            logger.info("原始comment：" + comment);
            for (String sw : sensitiveWords) {
                comment = comment.replaceAll(sw, "");
                logger.info("去敏感词后comment：" + comment);
            }
        }
        return comment;
    }

    @RequestMapping(value = {"/articles/{articleId}/comment"})
    public String doComment(@PathVariable String articleId, RedirectAttributes attributes, Model model) throws Exception {
        attributes.addFlashAttribute("comment", model.asMap().get("comment"));
        model.addAttribute("articleId", articleId);
        // 此处将评论内容保存到数据库
        return "redirect:/showArticle";
    }

    @RequestMapping(value = {"/showArticle"}, method = {RequestMethod.GET})
    public String showArticle(Model model, SessionStatus sessionStatus) throws Exception {
        String articleId = (String) model.asMap().get("articleId");
        logger.info("articleId:{}", articleId);
        model.addAttribute("articleTitle", articleId + "号文章标题");
        model.addAttribute("article", articleId + "号文章内容");
        sessionStatus.setComplete();
        return "article";
    }
}
