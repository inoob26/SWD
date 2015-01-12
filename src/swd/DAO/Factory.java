package swd.DAO;

import swd.DAO.Impl.BankDAOImpl;
import swd.DAO.Impl.FirmDAOImpl;
import swd.DAO.Impl.InvoiceDAOImpl;
import swd.DAO.Impl.RullDAOImpl;
import swd.DAO.Impl.S_IDAOImpl;
import swd.DAO.Impl.ServiceDAOImpl;
import swd.DAO.Impl.UserDAOImpl;

public class Factory {
    private static FirmDAO firmDAO = null;
    private static RullDAO rullDAO = null;
    private static Factory instance = null;
    private static BankDAO bankDAO = null; 
    private static InvoiceDAO invoiceDAO = null; 
    private static S_IDAO s_iDAO = null;
    private static ServiceDAO serviceDAO = null;
    private static UserDAO userDAO = null;
    
    public static synchronized Factory getInstance(){
        if(instance == null){
            instance = new Factory();
        }
        return instance;
    }
    
    public FirmDAO getFirmDAO(){
        if(firmDAO == null){
            firmDAO = new FirmDAOImpl();
        }
        
        return firmDAO;
    }
    
    public RullDAO getRullDAO(){
        if(rullDAO == null){
            rullDAO = new RullDAOImpl();
        }
        
        return rullDAO;
    }
    
    public BankDAO getBankDAO(){
        if(bankDAO == null){
            bankDAO = new BankDAOImpl();
        }
        
        return bankDAO;
    }
    
    public InvoiceDAO getInvoiceDAO(){
        if(invoiceDAO == null){
            invoiceDAO = new InvoiceDAOImpl();
        }
        
        return invoiceDAO;
    }
    
    public S_IDAO getS_IDAO(){
        if(s_iDAO == null){
            s_iDAO = new S_IDAOImpl();
        }
        
        return s_iDAO;
    }
    
    public ServiceDAO getServiceDAO(){
        if(serviceDAO == null){
            serviceDAO = new ServiceDAOImpl();
        }
        
        return serviceDAO;
    }
    
    public UserDAO getUserDAO(){
        if(userDAO == null){
            userDAO = new UserDAOImpl();
        }
        
        return userDAO;
    }
}
