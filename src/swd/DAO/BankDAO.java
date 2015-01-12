package swd.DAO;

import java.sql.SQLException;
import java.util.List;
import swd.logic.Bank;

public interface BankDAO {
    public void addBank(Bank bank) throws SQLException;//добавление банка
    public void updateBank(Bank bank) throws SQLException;//Обновить данные банка
    public Bank getBankById(short id) throws SQLException;//получить банк по id
    public List getAllBanks() throws SQLException;//получить список банков
    public List getAllBanksName() throws SQLException;//получить список наименований банков
    public void deleteBank(Bank bank) throws SQLException;//удалить банк    
}
