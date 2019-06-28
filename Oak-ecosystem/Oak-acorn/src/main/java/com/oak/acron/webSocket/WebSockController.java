package com.oak.acron.webSocket;

import com.oak.acron.entity.RequestMessage;
import com.oak.acron.entity.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oak on 2018/7/5.
 * Description:
 */
@Controller
public class WebSockController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    public WebSockController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    /**
     * @SendTo 表示当服务器有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say(RequestMessage message) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        try {
            //睡眠1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResponseMessage("welcome," + message.getName() + "!"+date);
    }
    /**
     * 定时推送消息
     */
    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/callback")
    public Object  callback() throws Exception{
        // 发现消息
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
        return "callback";
    }
}
