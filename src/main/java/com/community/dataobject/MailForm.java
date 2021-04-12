package com.community.dataobject;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

/**
 * @email chivseg-hao@qq.com
 * @author:blue
 * @date: 2021/4/12
 * @time: 19:32
 */
@Data
@Accessors(chain = true)
public class MailForm {
    // 寄件人
    private String from;

    // 收件人
    private List<String> to;

    // 主题
    private String subject;

    // 文本
    private String text;

    // 本地附件路径
    private List<String> attachmentPath;

    // 模板名
    private String templateName;

    // 模板变量
    private Map<String,Object> contextVar;
}

