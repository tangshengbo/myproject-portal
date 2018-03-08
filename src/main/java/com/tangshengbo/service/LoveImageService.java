package com.tangshengbo.service;

import com.tangshengbo.model.LoveImage;

import java.util.List;

/**
 * Created by Tangshengbo on 2018/3/6.
 */
public interface LoveImageService {

    void save(LoveImage loveImage);

    List<LoveImage> listLoveImage();

    void deleteImage(String url);
}
