package swd.DAO.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import swd.util.HibernateUtil;
import swd.DAO.ServiceDAO;
import swd.logic.Service;

public class ServiceDAOImpl implements ServiceDAO{

    @Override
    public void addService(Service service) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(service);
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
    public void updateService(Service service) throws SQLException {
        Session session = null;
        try{
           session = HibernateUtil.getSessionFactory().openSession();
           session.beginTransaction();
           session.update(service);
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
    public Service getServiceById(short id) throws SQLException {
        Session session = null;
        Service service = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            service = (Service) session.get(Service.class, id);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return service;
    }

    @Override
    public List getAllServices() throws SQLException {
        List<Service> services = new ArrayList<Service>();
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            services = session.createCriteria(Service.class).list();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return services;
    }
    
    @Override
    public List getAllServicesName() throws SQLException {
        List<Service> services = new ArrayList<Service>();
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            services = session.createSQLQuery("SELECT Name FROM `Service`").list();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        }
        finally{
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return services;
    }

    @Override
    public void deleteService(Service service) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(service);
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
