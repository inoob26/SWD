package swd.DAO.Impl;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import swd.DAO.RullDAO;
import swd.logic.Rull;
import swd.util.HibernateUtil;

public class RullDAOImpl implements RullDAO{

    @Override
    public Rull getIRullById(byte id) throws SQLException {
        Rull rull = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            rull = (Rull) session.get(Rull.class, id);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return rull;
    }
}
