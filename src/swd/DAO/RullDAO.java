package swd.DAO;

import java.sql.SQLException;
import swd.logic.Rull;

public interface RullDAO {
    public Rull getIRullById(byte id) throws SQLException;//получить услугу по id
}
