package swd.DAO.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import swd.DAO.S_IDAO;
import swd.logic.S_I;
import swd.util.HibernateUtil;

public class S_IDAOImpl implements S_IDAO{

    @Override
    public void addS_I(S_I s_i) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(s_i);
            session.getTransaction().commit();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }

    @Override
    public void updateS_I(S_I s_i) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(s_i);
            session.getTransaction().commit();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }

    @Override
    public S_I getS_IById(Long id) throws SQLException {
        S_I si = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            si = (S_I) session.get(S_I.class, id);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        
        return si;
    }
    
    @Override
    public S_I getS_IByInvoiceId(Long id) throws SQLException {
        S_I si = null;
        Session session = null;
        try{
            //"SELECT `Invoice_id` FROM `S_I` WHERE `Invoice_id` " + id.toString()
            session = HibernateUtil.getSessionFactory().openSession();
            si = (S_I) session.createSQLQuery("SELECT `ID`, `Service_id`, `Invoice_id`, `Firm_id`, `Count` FROM `S_I` WHERE `Invoice_id` = " + id.toString());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        
        return si;
    }

    @Override
    public List getAllS_Is() throws SQLException {
        List<S_I> si = new ArrayList<S_I>();
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            si = session.createCriteria(S_I.class).list();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return si;
    }

    @Override
    public void deleteS_I(S_I s_i) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(s_i);
            session.getTransaction().commit();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }
}
