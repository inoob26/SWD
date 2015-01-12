package swd.DAO;

import java.sql.SQLException;
import java.util.List;

import swd.logic.Firm;

public interface FirmDAO {
    public void addFirm(Firm firm) throws SQLException;//добавление фирмы
    public void updateFirm(Firm firm) throws SQLException;//Обновить данные фирмы
    public Firm getFirmById(Long id) throws SQLException;//получить фирму по id
    public List getAllFirms() throws SQLException;//получить все фирмы
    public void deleteFirm(Firm firm) throws SQLException;//удалить фирму
}