package swd.DAO;

import java.sql.SQLException;
import swd.logic.Performer;

public interface PerformerDAO {
    public Performer getPerformerById(byte id) throws SQLException;//получить
    public void updatePerformer(Performer perf) throws SQLException;//Обновить данные фирмы
}
