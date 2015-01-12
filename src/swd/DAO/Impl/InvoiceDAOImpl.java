package swd.DAO.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import swd.DAO.InvoiceDAO;
import swd.logic.Invoice;
import swd.util.HibernateUtil;

public class InvoiceDAOImpl implements InvoiceDAO {

    @Override
    public void addInvoice(Invoice invoice) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(invoice);
            session.getTransaction().commit();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }

    @Override
    public void updateInvoice(Invoice invoice) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(invoice);
            session.getTransaction().commit();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }

    @Override
    public Invoice getInvoiceById(Long id) throws SQLException {
        Invoice invoice = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            invoice = (Invoice) session.get(Invoice.class, id);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return invoice;
    }

    @Override
    public List getAllInvoices() throws SQLException {
        List<Invoice> invoice = new ArrayList<Invoice>();
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            invoice = session.createCriteria(Invoice.class).list();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return invoice;
    }

    @Override
    public void deleteInvoice(Invoice invoice) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(invoice);
            session.getTransaction().commit();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }
}
