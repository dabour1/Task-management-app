package com.tasks.tma.mappers;
 import com.tasks.tma.dtos.UserRequestDTO;
 import com.tasks.tma.models.User;
 import org.mapstruct.Mapper;
 import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User userDtoTouser(UserRequestDTO userRequestDTO);
}
