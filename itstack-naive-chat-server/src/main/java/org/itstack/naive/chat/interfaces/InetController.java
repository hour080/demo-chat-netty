package org.itstack.naive.chat.interfaces;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.application.InetService;
import org.itstack.naive.chat.domain.inet.model.ChannelUserInfo;
import org.itstack.naive.chat.domain.inet.model.ChannelUserReq;
import org.itstack.naive.chat.domain.inet.model.InetServerInfo;
import org.itstack.naive.chat.infrastructure.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用于处理用户发送的 Restful 请求和解析用户输入的配置文件
 */
@Slf4j
@Controller
public class InetController {
    @Resource
    private InetService inetService;

    //设置默认访问页面，需要设置内部资源视图解析器的访问前缀和访问后缀
    @RequestMapping("/")
    public String index(){
        log.info("hello, index");
        return "index";
    }

    /**
     * 查询服务端的状态信息
     * @author hourui
     * @date 2023/1/28 22:54
     * @return
     */
    @RequestMapping("/api/queryNettyServerInfo")
    @ResponseBody
    public Result queryNettyServerInfo() {
        try{
            ArrayList<InetServerInfo> inetServerInfos = new ArrayList<>();
            inetServerInfos.add(inetService.queryNettyServerInfo());
            return Result.successResult(inetServerInfos);
        }catch (Exception e){
            log.info("查询NettyServer失败, 失败原因是: {}", e.getMessage());
            return Result.errorResult(e);
        }
    }
    @RequestMapping ("/api/queryChannelUserList")
    @ResponseBody
    public Result queryChannelUserList(String json, String page, String limit){
        try {
            ChannelUserReq req = JSON.parseObject(json, ChannelUserReq.class);
            if(req == null){
                req = new ChannelUserReq();
                //这里userId = null, userNickName也为null,查询的就是select count（*）from user
            }
            req.setPage(page, limit);
            Long userCount = inetService.queryChannelUserCount(req);
            List<ChannelUserInfo> channelUserInfoList = inetService.queryChannelUserList(req);
            log.info("查询通信管道用户信息列表完成。list：{}", JSON.toJSONString(channelUserInfoList));
            return Result.successResult(userCount, channelUserInfoList);
        } catch (Exception e) {
            log.info("查询通信管道用户信息列表失败。reason: {}", e);
            return Result.errorResult(e);
        }
    }
}
