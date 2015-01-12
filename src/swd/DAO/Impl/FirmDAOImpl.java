package swd.DAO.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import swd.util.HibernateUtil;
import swd.DAO.FirmDAO;
import swd.logic.Firm;

public class FirmDAOImpl implements FirmDAO {
    @Override
    public void addFirm(Firm firm) throws SQLException{
    	Session session = null;
    	try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(firm);
            session.getTransaction().commit();
    	}catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }

    @Override
    public void updateFirm(Firm firm) throws SQLException {
       Session session = null;
       try{
           session = HibernateUtil.getSessionFactory().openSession();
           session.beginTransaction();
           session.update(firm);
           session.getTransaction().commit();
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
       }
       finally{
           if(session.isOpen() || session != null){
                session.close();
            }
       }
    }

    @Override
    public Firm getFirmById(Long id) throws SQLException {
        Session session = null;
        Firm firm = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            firm = (Firm) session.get(Firm.class, id);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return firm;
    }

    @Override
    public List getAllFirms() throws SQLException {
        Session session = null;
        List<Firm> firms = new ArrayList<Firm>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            firms = session.createCriteria(Firm.class).list();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return firms;
    }

    @Override
    public void deleteFirm(Firm firm) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(firm);
            session.getTransaction().commit();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }
}