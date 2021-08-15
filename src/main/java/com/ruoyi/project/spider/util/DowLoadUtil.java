package com.ruoyi.project.spider.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


public class DowLoadUtil {
    public static List<String> getImageObj(String encode, String size, String color, int page) throws Exception {
        String newUrl = "https://image.baidu.com/search/acjson?tn=resultjson_com&logid=6676626386861249916&ipn=rj&ct=201326592&is=&fp=result&queryWord=" + encode
                + "&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=&z=" + size + "&ic=" + color + "&hd=&latest=&copyright=&word=" + encode
                + "&s=&se=&tab=&width=&height=&face=&istype=&qc=&nc=1&fr=&expermode=&force=&pn=" + page + "&rn=30&gsm=78&1621417638681=";
        URL url = new URL(newUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //设置连接时间
        urlConnection.setConnectTimeout(5000);
        urlConnection.setRequestMethod("GET");
        //伪造浏览器
        urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        urlConnection.setRequestProperty("referer", "https://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&word=" + encode);
        urlConnection.setRequestProperty("sec-fetch-dest", "empty");
        urlConnection.setRequestProperty("sec-fetch-mode", "cors");
        urlConnection.setRequestProperty("authority", "api.live.bilibili.com");
        urlConnection.setRequestProperty("cookie", "_uuid=CEFF0C7B-6D58-0398-21C6-3FC333B36C6E48488infoc; buvid3=EE47F094-4FB9-4B32-B4FE-8FB7F0231BBB155819infoc; sid=9oh8mjjy; CURRENT_FNVAL=16; rpdid=|(um|||kRRmR0J'ul)JJ)~mRk; LIVE_BUVID=AUTO2715833879432735; Hm_lvt_8a6e55dbd2870f0f5bc9194cddf32a02=1583388227,1584433577,1584705699; LNG=zh-CN; DedeUserID=317805262; DedeUserID__ckMd5=dd7f816655d50616; SESSDATA=dea1b0d5%2C1610541690%2C16c42*71; bili_jct=1743f5d024e8f3bdc9160fddd5df0fab; CURRENT_QUALITY=116; bp_t_offset_317805262=416656761666394310; bp_video_offset_317805262=418920548729240912; _dfcaptcha=8e8e95d2749239534d3cebfe8edbca7e; PVID=2");

        InputStream in = urlConnection.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String s = null;
        StringBuffer buffer = new StringBuffer();
        while ((s = bufferedReader.readLine()) != null) {
            buffer.append(s);
        }
        List<String> list = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(buffer.toString());
        JSONArray data = jsonObject.getJSONArray("data");
        for (Object o : data) {
            Map map = (Map) o;
            String objURL = (String) map.get("objURL");
            list.add(objURL);
        }
        return list;
    }



    //命令行下载
    public static void commandLineDownload(String antistop, String encode, OutputStream outputStream, InputStream
            inputStream, String path, String size, String color, int name, int c, int p) throws Exception {
        try {
            encode = URLEncoder.encode(antistop, "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("解码失败");
        }
        System.out.println("                                                                        [系统-------------下载系统启动-------------系统]");

        for (int page = 0; page <= p; page += 30) {
            try {
                List<String> imageObj = getImageObj(encode, size, color, page);
                for (String str : imageObj) {
                    //解析objurl
                    if (str != null) {
                        String parse = ParseImageUtil.parse(str);
                        try {
                            //将目标url在ParseImageUtil类的parse解析解析
                            HttpURLConnection connection = (HttpURLConnection) new URL(parse).openConnection();
                            //设置连接时间5秒
                            connection.setConnectTimeout(5000);
                            inputStream = connection.getInputStream();
                                /*     String fileName = ParseImageUtil.getFileName(i.getObjURL());
                                if (fileName.indexOf("?") != -1) {
                                    fileName = fileName.substring(0, fileName.indexOf("?"));
                                }
                                if (fileName.indexOf("@") != -1) {
                                    fileName = fileName.substring(0, fileName.indexOf("@"));
                                }*/
                            File file = new File(path + "\\" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
                            outputStream = new FileOutputStream(file);
                            byte[] bytes = new byte[1024];
                            int len;
                            while ((len = inputStream.read(bytes)) != -1) {
                                //下载图片
                                outputStream.write(bytes, 0, len);
                            }
                            System.out.println("                                     [目标-------------" + file.getName() + "-------------目标]");
                            System.out.println("                                             [完成-------------下载第" + name + "张图片-------------完成]");
                            name++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            //释放资源
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("                                                            连接失败");
            }
        }
        File file = new File(path);
        File[] list = file.listFiles();
        System.out.println("                                          [系统-------------无效文件删除系统启动-------------系统]");
        for (File file1 : list) {
            boolean flag = ImgDamageUtil.verifyImage(file1);
            if (!flag) {
                file1.delete();
                c++;
                System.out.println("                                             [无效文件]" + file1.getName() + "删除");
            }
        }
        System.out.println("                                                            下载完成");
        System.out.println("                                                         [有效文件]:" + (name - c) + "张");
    }


    //gui下载
    public static void DowloadGUI(String antistop, String encode, OutputStream outputStream, InputStream
            inputStream, String path,String size, String color, int name, int c, int p, JTextArea area) {
        try {
            encode = URLEncoder.encode(antistop, "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("解码失败");
        }

        for (int page = 30; page <= p; page += 30) {
            try {
                List<String> imageObj = getImageObj(encode, size, color, page);
                area.append("                                                                  [系统-------------下载系统启动-------------系统]\n");
                for (String str : imageObj) {
                    //解析objurl
                    if (str != null) {
                        String parse = ParseImageUtil.parse(str);
                        try {
                            //将目标url在ParseImageUtil类的parse解析解析
                            HttpURLConnection connection = (HttpURLConnection) new URL(parse).openConnection();
                            //设置连接时间5秒
                            connection.setConnectTimeout(5000);

                            try {
                                inputStream = connection.getInputStream();
                            } catch (IOException e) {
                                continue;
                            }
                            String fileName = ParseImageUtil.getFileName(str);
                            if (fileName.indexOf("?") != -1) {
                                fileName = fileName.substring(0, fileName.indexOf("?"));
                            }
                            if (fileName.indexOf("@") != -1) {
                                fileName = fileName.substring(0, fileName.indexOf("@"));
                            }
//                                String type = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + UUID.randomUUID().toString().replace("-", "") + ".jpg";

                            File file = new File(path + "\\" + newFileName);
                            outputStream = new FileOutputStream(file);
                            byte[] bytes = new byte[1024];
                            int len;
                            area.append("                                     [目标-------------" + newFileName + "-------------目标]\n");
                            area.append("                                                                          [大小-------------" + connection.getContentLengthLong() + "-------------大小]\n");
                            while ((len = inputStream.read(bytes)) != -1) {
                                //下载图片
                                outputStream.write(bytes, 0, len);
                                area.append("▊");
                            }
                            area.append("\n");
                            area.append("                                                                      [完成-------------下载第" + name + "张图片-------------完成]\n\n");
                            name++;
                        } catch (IOException e) {
                            e.printStackTrace();
                            area.append("                                                                   连接服务器失败\n");
                        } finally {
                            //释放资源
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    area.append("                                                                   连接服务器失败\n");
                                }
                            }
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    area.append("                                                                   连接服务器失败\n");
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                area.append("                                                            连接失败\n");
            }
        }
        File file = new File(path);
        File[] list = file.listFiles();
        area.append("                                                                   [系统-------------无效文件删除系统启动-------------系统]\n");
        for (File file1 : list) {
            boolean flag = ImgDamageUtil.verifyImage(file1);
            if (!flag) {
                file1.delete();
                c++;
                area.append("                                             [无效文件]" + file1.getName() + "删除");
            }
        }
        area.append("                                                                                             下载完成\n");
        area.append("                                                                                        [有效文件]:" + (name - c) + "张\n");
    }
}
