package com.czl.console.backend.system.dao;

import com.czl.console.backend.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface MenuMapper extends CoreMapper<Menu>{



    /**
     * 根据角色id获得改该角色的所有menu
     * @param id roleId
     * @return menu数组
     */
    @Select("select m.* from sys_menu m left join sys_roles_menus rm on m.id = rm.menu_id where rm.role_id = #{role_id}")
    Set<Menu> findMenusByRoleId(@Param("role_id") Long id);

    /**
     * 根据角色id集合所有menu
     * @param roleIds 角色id集合
     * @return menu集合
     */
    @Select({
            "<script>",
            "select m.* from sys_menu m left outer join sys_roles_menus rm on m.id= rm.menu_id left outer join " +
            "sys_role r on r.id = rm.role_id where m.type != 2 and r.id in",
            "<foreach collection='ids' item='item' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    List<Menu> findMenusByRoleIds(@Param("ids") List<Long> roleIds);

    @Select("SELECT count(*) from sys_menu m where pid = #{pid}")
    Long countByPid(@Param("pid") Long pid);

    @Select("update sys_menu set sub_count = #{sub_count} where id = #{id}")
    void updateSubCountById(@Param("id") Long id, @Param("sub_count") Long subCount);
}
