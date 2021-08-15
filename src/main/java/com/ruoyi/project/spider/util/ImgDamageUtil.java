package com.ruoyi.project.spider.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * @Author: HongXiaoHan
 * @Description
 * @Date: 2021/5/19 18:13
 */
public class ImgDamageUtil {
    public static boolean verifyImage(File f) {
        try {
            FileInputStream fi = new FileInputStream(f);
            try {
                BufferedImage sourceImg = ImageIO.read(fi);//判断图片是否损坏
                int picWidth = sourceImg.getWidth(); //确保图片是正确的（正确的图片可以取得宽度）
                return true;
            } catch (Exception e) {
                // TODO: handle exception
                fi.close();//关闭IO流才能操作图片
                return false;
            } finally {
                fi.close();//最后一定要关闭IO流
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
            return false;
        }
    }
}
