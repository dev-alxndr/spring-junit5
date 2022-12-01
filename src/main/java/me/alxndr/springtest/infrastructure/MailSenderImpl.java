package me.alxndr.springtest.infrastructure;

import me.alxndr.springtest.service.MailSender;
import org.springframework.stereotype.Component;

/**
 * @author : Alexander Choi
 * @date : 2022/12/01
 */
@Component
public class MailSenderImpl implements MailSender {
    @Override
    public boolean send() {
        return true;
    }
}
