package com.community.dto;

import com.community.exception.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data  //用来传递报错的DTO对象
public class ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 1290601389848293715L;

    private Integer code;

    private String message;

    //封装的list 不一定都是List 所以用泛型
    private T data;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static  ResultDTO okOf(){
        ResultDTO resultDTO= new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功!");
        return resultDTO;
    }
    public static <T> ResultDTO okOf(T t){
        ResultDTO resultDTO= new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功!");
        resultDTO.setData(t);
        return resultDTO;
    }

}
