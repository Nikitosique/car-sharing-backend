package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.UserDAO;
import dev.andrylat.carsharing.models.User;
import dev.andrylat.carsharing.services.UserService;
import dev.andrylat.carsharing.services.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public long getRecordsNumber() {
        return userDAO.getRecordsNumber();
    }

    @Override
    public List<User> getAll(int pageNumber, int pageSize) {
        Validator.validatePageNumber(pageNumber);
        Validator.validatePageSize(pageSize);

        return userDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public User getById(long id) {
        Validator.validateRecordId(id);

        return userDAO.getById(id);
    }

    @Override
    public User add(User objectToAdd) {
        return null;
    }

    @Override
    public boolean updateById(User updatedObject) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public long getCustomersNumberByManagerId(long managerId) {
        Validator.validateRecordId(managerId);

        return userDAO.getCustomersNumberByManagerId(managerId);
    }

    @Override
    public List<Long> getCustomersIdByManagerId(long managerId, int pageNumber, int pageSize) {
        Validator.validateRecordId(managerId);
        Validator.validatePageSize(pageSize);
        Validator.validatePageNumber(pageNumber);

        return userDAO.getCustomersIdByManagerId(managerId, pageNumber, pageSize);
    }

    @Override
    public boolean assignCustomerToManager(long customerId, long managerId) {
        return false;
    }

    @Override
    public boolean unassignCustomerFromManager(long customerId, long managerId) {
        return false;
    }
}
