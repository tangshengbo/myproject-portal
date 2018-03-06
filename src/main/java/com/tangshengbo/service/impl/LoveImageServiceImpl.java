package com.tangshengbo.service.impl;

import com.tangshengbo.dao.LoveImageMapper;
import com.tangshengbo.model.LoveImage;
import com.tangshengbo.service.LoveImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tangshengbo on 2018/3/6.
 */
@Service("loveImageService")
public class LoveImageServiceImpl implements LoveImageService {

    @Autowired
    private LoveImageMapper loveImageMapper;

    @Override
    public void save(LoveImage loveImage) {
        loveImageMapper.insertSelective(loveImage);
    }

    @Override
    public List<LoveImage> listLoveImage() {
        return loveImageMapper.listLoveImage();
    }
}
