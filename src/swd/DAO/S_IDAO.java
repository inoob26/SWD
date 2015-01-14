package swd.DAO;

import java.sql.SQLException;
import java.util.List;
import swd.logic.S_I;

public interface S_IDAO {
    public void addS_I(S_I s_i) throws SQLException;//добавить связку Услуга-Счет фактура
    public void updateS_I(S_I s_i) throws SQLException;//обновить связку
    public S_I getS_IById(Long id) throws SQLException;//получить связку по id
    public S_I getS_IByInvoiceId(Long id) throws SQLException;//получить связку по id
    public List getAllS_Is() throws SQLException;//получить список связок
    public void deleteS_I(S_I s_i) throws SQLException;//удалить связку
}
