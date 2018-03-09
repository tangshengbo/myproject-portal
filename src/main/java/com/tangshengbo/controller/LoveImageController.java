package com.tangshengbo.controller;

import com.alibaba.fastjson.JSON;
import com.tangshengbo.core.BeanCopier;
import com.tangshengbo.core.RandomUtils;
import com.tangshengbo.model.CanvasImage;
import com.tangshengbo.model.LoveImage;
import com.tangshengbo.service.LoveImageService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Tangshengbo on 2018/3/6.
 */
@Controller
@RequestMapping("/love")
public class LoveImageController {

    private static Logger logger = LoggerFactory.getLogger(LoveImageController.class);

    private static final String BASE_IMG_URL = "/img/love/";

    @Autowired
    private LoveImageService loveImageService;

    @RequestMapping(value = "/show_square", method = {RequestMethod.GET, RequestMethod.POST})
    public String showSquare(Model model) {
        model.addAttribute("loveImageList", loveImageService.listLoveImage());
        return "love_image_square";
    }

    @RequestMapping(value = "/show_canvas", method = {RequestMethod.GET, RequestMethod.POST})
    public String showCanvas() {
        return "love_image_canvas";
    }

    @RequestMapping(value = "/upload_view", method = RequestMethod.GET)
    public String uploadView(String name) {
        if (StringUtils.isNotEmpty(name) && "tsb".equals(name)) {
            return "upload";
        }
        return "redirect:/404.jsp";
    }

    @RequestMapping(value = "/show_images", method = {RequestMethod.GET})
    public String showImages(Model model) {
        model.addAttribute("loveImageList", loveImageService.listLoveImage());
        return "love_show_images";
    }

    @RequestMapping(value = "/delete-image", method = {RequestMethod.POST})
    @ResponseBody
    public String deleteImage(@RequestParam(value = "url") String url, HttpServletRequest request) {
        logger.info("{}", url);
        String path = request.getServletContext().getRealPath("/");
        loveImageService.deleteImage(url);
        return new File(path + url).delete() ? "delete success" : "delete fail";
    }

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    @ResponseBody
    public String getImages(HttpServletRequest request) throws Exception {
        List<CanvasImage> imageList = new ArrayList<>();
        String context = request.getContextPath();
        List<CanvasImage> canvasImageList = CanvasImage.canvasImages();
        List<LoveImage> loveImageList = loveImageService.listLoveImage();
        for (LoveImage loveImage : loveImageList) {
            CanvasImage canvasImage = buildCanvasImage(context, canvasImageList, loveImage);
            if (Objects.isNull(canvasImage)) {
                continue;
            }
            imageList.add(canvasImage);
        }

        String json = JSON.toJSONString(imageList);
        logger.info("json:{}", json);
        return json;
    }

    /**
     * 构建画板对象
     *
     * @param context
     * @param canvasImageList
     * @param loveImage
     * @return
     * @throws Exception
     */
    private CanvasImage buildCanvasImage(String context, List<CanvasImage> canvasImageList, LoveImage loveImage)
            throws Exception {
        if (canvasImageList.size() > 0) {
            CanvasImage srcCanvasImage = RandomUtils.getRandomElement(canvasImageList);
            CanvasImage destCanvasImage = BeanCopier.copy(srcCanvasImage, CanvasImage.class);
            BeanUtils.copyProperties(destCanvasImage, srcCanvasImage);
            destCanvasImage.setImg(context + loveImage.getImgUrl());
            canvasImageList.remove(srcCanvasImage);
            return destCanvasImage;
        }
        return null;
    }

    //上传文件会自动绑定到MultipartFile中
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request,
                         @RequestParam("file") MultipartFile file) throws Exception {
        //如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            //上传文件路径
            String path = request.getServletContext().getRealPath(BASE_IMG_URL);
            //上传文件名
            String filename = file.getOriginalFilename();
            BufferedImage fileType = ImageIO.read(file.getInputStream());
            if (Objects.isNull(fileType)) {
                return "Please upload the picture!";
            }
            File filePath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            loveImageService.save(new LoveImage(BASE_IMG_URL + filename));
            return "upload success";
        } else {
            return "upload error";
        }
    }
}
