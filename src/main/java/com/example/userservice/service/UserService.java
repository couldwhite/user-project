package com.example.userservice.service;

import com.example.userservice.entity.PgUser;
import com.example.userservice.feignclient.CompanyServiceFeignClient;
import com.example.userservice.model.UserDTO;
import com.example.userservice.repository.PgUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PgUserRepository pgUserRepository;
    private final CompanyServiceFeignClient companyServiceFeignClient;

    public String getUserName (Long id){
        PgUser pgUser = pgUserRepository.getPgUserById(id);
        return pgUser.getName();
    }

    public List<UserDTO> getUsers(){
        List<PgUser> pgUsers = pgUserRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (PgUser pgUser:pgUsers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(pgUser.getId());
            userDTO.setEnabled(pgUser.isEnabled());
            userDTO.setLogin(pgUser.getLogin());
            userDTO.setPassword(pgUser.getPassword());
            userDTO.setCompanyId(pgUser.getCompanyId());
            userDTO.setEmail(pgUser.getEmail());
            String name = companyServiceFeignClient.getNameById(pgUser.getCompanyId());
            userDTO.setCompanyName(name);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public void changeStatusUser(Long id){
        PgUser user = pgUserRepository.getPgUserById(id);
        user.setEnabled(!user.isEnabled());
        pgUserRepository.save(user);
    }

    public void changeUserInfo(UserDTO dto){
        PgUser user = pgUserRepository.getPgUserById(dto.getId());
        if (user==null){
            throw new EntityNotFoundException( "Такого пользователя еще нет" );
        }
        boolean isExist = companyServiceFeignClient.existById(dto.getCompanyId());
        if(!isExist){
            throw new EntityNotFoundException( "Компании с таким айди %s не существует".formatted(dto.getCompanyId()));
        }
        user.setName(dto.getName());
        user.setCompanyId(dto.getCompanyId());
        user.setEmail(dto.getEmail());
        pgUserRepository.save(user);
    }

    public Long createUser(UserDTO dto){
        boolean isExist = companyServiceFeignClient.existById(dto.getCompanyId());
        if(!isExist){
            throw new EntityNotFoundException( "Компании с таким айди %s не существует".formatted(dto.getCompanyId()));
        }
        return pgUserRepository.save(dto.toEntity()).getId();
    }

    public boolean findUser (Long id){
        PgUser pgUser = pgUserRepository.getPgUserById(id);
        return pgUser != null && pgUser.isEnabled();
    }
}
