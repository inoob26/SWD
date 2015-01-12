package swd.DAO;

import java.sql.SQLException;
import java.util.List;
import swd.logic.Service;

public interface ServiceDAO {
    public void addService(Service service) throws SQLException;//добавление услуги
    public void updateService(Service service) throws SQLException;//Обновить данные услуг
    public Service getServiceById(short id) throws SQLException;//получить услугу по id
    public List getAllServices() throws SQLException;//получить список услуг
    public List getAllServicesName() throws SQLException;//получить список имен
    public void deleteService(Service service) throws SQLException;//удалить услугу
}
