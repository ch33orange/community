package com.community.controller;

import com.community.Provider.*;
import com.community.config.*;
import com.community.dto.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.*;

@Controller
@Slf4j
public class FileController {
    @Autowired
    private UCloudProvider ucloundProvider;


    @RequestMapping(value = "/file/upload",method = RequestMethod.POST)
    @NeedLogin
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            String fileName = ucloundProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            return  fileDTO;
        } catch (Exception e) {
            log.error("file upload error.{}",e);
            e.printStackTrace();
        }
        //错误时
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/images/default.png");
        return  fileDTO;
    }
}
