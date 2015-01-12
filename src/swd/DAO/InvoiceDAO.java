package swd.DAO;

import java.sql.SQLException;
import java.util.List;
import swd.logic.Invoice;

public interface InvoiceDAO {
    public void addInvoice(Invoice invoice) throws SQLException;//добавление услуги
    public void updateInvoice(Invoice invoice) throws SQLException;//Обновить данные услуг
    public Invoice getInvoiceById(Long id) throws SQLException;//получить услугу по id
    public List getAllInvoices() throws SQLException;//получить список услуг
    public void deleteInvoice(Invoice invoice) throws SQLException;//удалить услугу
}
