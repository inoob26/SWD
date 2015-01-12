package swd.DAO.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import swd.DAO.UserDAO;
import swd.logic.Users;
import swd.util.HibernateUtil;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(Users user) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }

    @Override
    public void updateUser(Users user) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }

    @Override
    public Users getUserById(Long id) throws SQLException {
        Users user = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            user = (Users) session.get(Users.class, id);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return user;
    }
    
    @Override
    public Users getUserByLogin(String login) throws SQLException {
        Users user = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            user = (Users) session.get(Users.class, login);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return user;
    }

    @Override
    public List getAllUsers() throws SQLException {
        List<Users> users = new ArrayList<Users>();
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createCriteria(Users.class).list();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if(session.isOpen() || session != null){
                session.close();
            }
        }
        return users;
    }

    @Override
    public void deleteUser(Users user) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
        } finally {
            if(session.isOpen() || session != null){
                session.close();
            }
        }
    }

    
    
}
