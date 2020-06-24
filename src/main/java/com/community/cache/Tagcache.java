package com.community.cache;


import com.community.dto.*;
import org.apache.commons.lang3.*;

import java.util.*;
import java.util.stream.*;

public class Tagcache {
    public static List<TagDTO> get() {
        ArrayList<TagDTO> tagDTOS = new ArrayList<TagDTO>();

        TagDTO major = new TagDTO();
        major.setCategoryName("大学课程");
        major.setTags(Arrays.asList("高等数学","离散数学","概率论与数理统计","线性代数与解析几何","光学","大学物理","物理实验","电工电子基础实验","电路分析基础","电路分析基础","数字电路与逻辑设计","通信原理","信号与系统","微型计算机接口技术","数据结构","算法分析与设计","关系与数据库编程","专业英语","马克思主义基本原理"));
        tagDTOS.add(major);

        TagDTO life = new TagDTO();
        life.setCategoryName("生活分享");
        life.setTags(Arrays.asList("生活照","游戏","没事","电影推荐","相机摄影","旅游行程","美妆护肤","衣物穿搭","身边趣事","小牢骚","家居生活","明星八卦","历史","考研专区"));
        tagDTOS.add(life);

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c",
                "c + + ",  "golang", "objective-c", "typescript", "shell", "c#", "swift", "sass", "bash", "ruby",
                "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel","mybatis" ,"Bootstrap","Vue" ,"spring boot","spring cloud","spring security",
                "spring mvc",
                "spring",
                "express",
                "django", "flask",
                "yii", "ruby" +
                "-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql", "memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOS.add(db);


        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode", "intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom", "emacs", "textmate", "hg"));
        tagDTOS.add(tool);


        return tagDTOS;
    }

    //拿到校验不通过的
    public static String filterInvalid(String tags) {
            String[] split = StringUtils.split(tags, ",");
            List<TagDTO> tagDTOS = get();

            List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
            String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
            return invalid;
    }
}
