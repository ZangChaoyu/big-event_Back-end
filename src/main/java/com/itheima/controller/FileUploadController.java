package com.itheima.controller;
import com.alibaba.fastjson.JSONObject;



import com.itheima.pojo.Result;
import com.itheima.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.python.core.PyFunction;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;


@RestController
public class FileUploadController {

    //存放图片到本地的路径
    @Value("${path.save}")
    private String path_photo_save;

    @Value("${path.python_code}")
    private String path_python_code;

    @Value("${name.python_method}")
    private String name_python_method;

    @PostMapping("/upload")
    public Result<JSONObject> upload(MultipartFile file) throws Exception {

        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的，从而防止文件覆盖借助UUID
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("C:\\Users\\Administrator\\Desktop\\files\\"+filename));
        //file_local.transferTo(new File("C:\\Users\\Administrator\\Desktop\\files\\"+filename));
        String url = AliOssUtil.uploadFile(filename,file.getInputStream());
        //创建要传输的json对象
        JSONObject jsonObject = new JSONObject();

        //下载图片
        URL url_self = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url_self.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            FileOutputStream out = new FileOutputStream(new File(path_photo_save+filename));
            String fullname = path_photo_save+filename;
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            // 下载完成，关闭输入输出流
            in.close();
            out.close();
            connection.disconnect();
            System.out.println("图片下载成功，保存路径：" + path_photo_save+filename+"开始分析");
            //用python分析图片
            PythonInterpreter interpreter = new PythonInterpreter();
            Random random =new Random();
            List<Integer> list =new ArrayList<>();
            for(int i=0;i<10;i++){
                list.add(random.nextInt(100));
            }
            interpreter.execfile(path_python_code);
            PyFunction function =interpreter.get(name_python_method,PyFunction.class);
            System.out.println("obj.toString()");
            PyObject obj = function.__call__(new PyList(Collections.singleton(fullname)));
            System.out.println(obj.toString());
            //将图片分析结果和阿里url路径放入json中
            jsonObject.put("url", url);
            jsonObject.put("result", "分析结果为 : "+ obj.toString());


        } else {
            System.out.println("无法下载图片，响应码：" + responseCode);
        }

        return Result.success(jsonObject);
    }


}
