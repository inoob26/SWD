package swd.DAO.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import swd.DAO.BankDAO;
import swd.logic.Bank;
import swd.util.HibernateUtil;

public class BankDAOImpl implements BankDAO {

    @Override
    public void addBank(Bank bank) throws SQLException {
        Session session = null;
    	try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(bank);
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
    public void updateBank(Bank bank) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(bank);
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
    public Bank getBankById(short id) throws SQLException {
        Session session = null;
        Bank bank = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            bank = (Bank)session.get(Bank.class, id);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return bank;
    }

    @Override
    public List getAllBanks() throws SQLException {
        Session session = null;
        List<Bank> banks = new ArrayList<Bank>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            banks = session.createCriteria(Bank.class).list();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return banks; 
    }
    
    @Override
    public List getAllBanksName() throws SQLException {
        Session session = null;
        List<Bank> banks = new ArrayList<Bank>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            banks = session.createSQLQuery("SELECT Name FROM `BANK`").list();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return banks;
    }

    @Override
    public void deleteBank(Bank bank) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(bank);
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
