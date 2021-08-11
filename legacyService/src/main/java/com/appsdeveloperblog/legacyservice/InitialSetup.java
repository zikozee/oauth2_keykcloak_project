package com.appsdeveloperblog.legacyservice;

import com.appsdeveloperblog.legacyservice.data.UserEntity;
import com.appsdeveloperblog.legacyservice.data.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class InitialSetup {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        UserEntity user = new UserEntity(
                1L,
                "qswe3mg84mfjtu",
                "Ezekiel",
                "Eromosei",
                "test@test.com",
                bCryptPasswordEncoder.encode("zikozee"),
                "",
                false);

        usersRepository.save(user);
    }
}
