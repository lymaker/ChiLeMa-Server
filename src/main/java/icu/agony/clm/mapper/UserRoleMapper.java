package icu.agony.clm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    void insert(@Param("userId") String userId, @Param("roleId") Integer roleId);

    void delete(@Param("userId") String userId, @Param("roleId") Integer roleId);

    List<String> select(@Param("userId") String userId);

}
