package com.service.apiservice.repository.impl;

import com.service.apiservice.model.MenuDTO;
import com.service.apiservice.repository.MenuRepository;
import com.service.apiservice.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;

@Repository
@Slf4j
public class MenuRepositoryImpl implements MenuRepository {
    @Autowired
    DataSource dataSource;

    public Boolean saveMenu(MenuDTO menuDTO) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        try{
            connection = dataSource.getConnection();
            callableStatement = connection.prepareCall("{call MENU_ADD(?,?,?)}");
            callableStatement.setString(1, menuDTO.getMenuName());
            callableStatement.setString(2, menuDTO.getRole());
            callableStatement.setInt(3, 1);
            callableStatement.execute();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }finally {
            DBUtils.closeConnection(callableStatement, connection, log);
        }
        return true;
    }
}
