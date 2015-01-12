package swd.DAO;

import java.sql.SQLException;
import java.util.List;
import swd.logic.Users;

public interface UserDAO {
    public void addUser(Users user) throws SQLException;//добавление пользователя
    public void updateUser(Users user) throws SQLException;//Обновить данные пользователя
    public Users getUserById(Long id) throws SQLException;//получить пользователя по id
    public Users getUserByLogin(String login) throws SQLException;//получить пользователя по login
    public List getAllUsers() throws SQLException;//получить список пользователей
    public void deleteUser(Users user) throws SQLException;//удалить пользователя
}
