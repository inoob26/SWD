package swd.DAO.Impl;

import java.sql.SQLException;
import javafx.stage.Stage;
import org.hibernate.Session;
import swd.DAO.PerformerDAO;
import swd.Message;
import swd.logic.Performer;
import swd.util.HibernateUtil;


public class PerformerDAOImpl implements PerformerDAO {

    @Override
    public Performer getPerformerById(byte id) throws SQLException {
        Session session = null;
        Performer performer = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            performer = (Performer) session.get(Performer.class, id);
        }catch(Exception ex){
            Stage st = new Stage();
            new Message(st,"Ошибка I/O").showErrorMessageDialog();
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return performer;
    }

    @Override
    public void updatePerformer(Performer perf) throws SQLException {
        Session session = null;
       try{
           session = HibernateUtil.getSessionFactory().openSession();
           session.beginTransaction();
           session.update(perf);
           session.getTransaction().commit();
       }catch(Exception ex){
           Stage st = new Stage();
            new Message(st,"Ошибка I/O").showErrorMessageDialog();
       }
       finally{
           if(session.isOpen() || session != null){
                session.close();
            }
       }
    }    
}
