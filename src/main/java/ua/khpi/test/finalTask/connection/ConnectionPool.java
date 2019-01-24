package ua.khpi.test.finalTask.connection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.khpi.test.finalTask.connection.util.DBConnector;
import ua.khpi.test.finalTask.connection.util.DBResourceManager;
import ua.khpi.test.finalTask.exception.ConnectionException;

public class ConnectionPool {
	private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock instanceLock = new ReentrantLock(true);
    private static ReentrantLock poolLock = new ReentrantLock(true);
    private static ConnectionPool instance;
    private final int POOL_SIZE;
    private final int CONNECTION_TIMEOUT;
    private Condition isFree = poolLock.newCondition();
    private ArrayDeque<ProxyConnection> connectionPool;
    private AtomicInteger connectionsCreatedCount;

    private ConnectionPool() {
        POOL_SIZE = initPoolSize();
        CONNECTION_TIMEOUT = initTimeout();
        connectionPool = new ArrayDeque<>();
        connectionsCreatedCount = new AtomicInteger(0);
    }

   
    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            instanceLock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    
    public ProxyConnection getConnection() throws ConnectionException, SQLException {

        poolLock.lock();
        try {
            while (connectionPool.isEmpty()) {
                if (connectionsCreatedCount.get() != POOL_SIZE) {
                    Connection connection = DBConnector.getConnection();
                    connectionsCreatedCount.getAndIncrement();
                    return new ProxyConnection(connection);
                }
                isFree.await(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
                if (connectionPool.isEmpty()) {
                    throw new ConnectionException("connection timeout exceeded, connection isn't available now, try later.");
                }
            }
            LOGGER.log(Level.TRACE, "connection was acquired from connection pool.");
            return connectionPool.poll();
        } catch (InterruptedException e) {
            throw new ConnectionException("current thread was interrupted, can't get connection.", e);
        } finally {
            poolLock.unlock();
        }
    }

    
    void releaseConnection(ProxyConnection connection) {
        poolLock.lock();
        try {
            connectionPool.offer(connection);
            isFree.signal();
            LOGGER.log(Level.TRACE, "connection was released to connection pool.");
        } finally {
            poolLock.unlock();
        }
    }


    
    private int initPoolSize() {
        int value;
        try {
            value = Integer.parseInt(DBResourceManager.getProperty(DBResourceManager.POOL_INITIAL_SIZE));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "pool size isn't a number, check database resources.configuration file.", e);
            throw new RuntimeException("pool size isn't a number, check database resources.configuration file.", e);
        }
        if (value <= 0) {
            LOGGER.log(Level.ERROR, "pool size can't be less or equals zero, check database resources.configuration file.");
            throw new RuntimeException("pool size can't be less or equals zero, check database resources.configuration file.");
        }
        return value;
    }

   
    
    private int initTimeout() {
        int value;
        try {
            value = Integer.parseInt(DBResourceManager.getProperty(DBResourceManager.POOL_TIMEOUT));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "connection timeout isn't a number, check database resources.configuration file.", e);
            throw new RuntimeException("connection timeout isn't a number, check database resources.configuration file.", e);
        }
        if (value <= 0) {
            LOGGER.log(Level.ERROR, "timeout can't be less or equals zero, check database resources.configuration file.");
            throw new RuntimeException("timeout can't be less or equals zero, check database resources.configuration file.");
        }
        return value;
    }

}