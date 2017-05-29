package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.AppUserDAO;
import com.intetics.lukyanenko.models.AppUser;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Service
public class AppUserDAOImpl
        extends CommonDAOImpl<AppUser>
        implements AppUserDAO
{
  public void add(AppUser model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("name", model.getName());
    params.put("password", model.getPassword());
    jdbcTemplate.update(
      "insert " +
      "  into app_user (" +
      "    name," +
      "    password" +
      "  ) values (" +
      "    :name," +
      "    :password" +
      "  )",
      params
    );
  }
  
  public void update(AppUser model)
  {
    if (model.getPassword() != null)
    {
      HashMap<String, Object> params = new HashMap<>();
      params.put("name", model.getName());
      params.put("password", model.getPassword());
      jdbcTemplate.update("update app_user " +
                            "  set password = :password " +
                            "  where name = :name",
                          params
                         );
    }
  }
  
  public void delete(AppUser model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("name", model.getName());
    jdbcTemplate.update("delete from app_user_role where user_name = :name", params);
    jdbcTemplate.update("delete from app_user where name = :name", params);
  }
  
  public int generateNewID()
  {
    return 0;
  }
  
  protected String getBaseSelectSQL()
  {
    return "select au.name, " +
            "      (select count(1) " +
            "         from app_user_role aur " +
            "         where aur.user_name = au.name" +
            "           and aur.role_name = 'customer' " +
            "      ) is_customer " +
            "  from app_user au";
  }
  
  protected AppUser mapFields(ResultSet resultSet, String fieldNamePrefix)
          throws SQLException
  {
    AppUser appUser = getNewModelInstance();
    appUser.setName(resultSet.getString(fieldNamePrefix + "name"));
    appUser.setPassword(null);
    appUser.setIsNew(false);
    appUser.setIsCustomer(resultSet.getByte(fieldNamePrefix + "is_customer") != 0);
    return appUser;
  }
  
  public AppUser getByName(String name)
  {
    return get("name", name.toLowerCase());
  }
}
