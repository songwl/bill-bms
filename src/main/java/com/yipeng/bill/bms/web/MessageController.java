package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/Message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/WriteMail")
    public String WriteMail(ModelMap modelMap) {
        List<String> bumen = messageService.getBumen();
        List<String> users = messageService.getUsers();
        modelMap.put("bumen", bumen);
        modelMap.put("Addressee", users);
        return "/Message/WriteMail";
    }

}
