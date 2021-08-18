package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.UserDAO;
import dev.andrylat.carsharing.exceptions.AssignmentException;
import dev.andrylat.carsharing.models.User;
import dev.andrylat.carsharing.services.UserService;
import dev.andrylat.carsharing.services.validators.UserValidator;
import dev.andrylat.carsharing.services.validators.ObjectValidator;
import dev.andrylat.carsharing.services.validators.ParametersValidator;
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
        ParametersValidator.validatePageNumber(pageNumber);
        ParametersValidator.validatePageSize(pageSize);

        return userDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public User getById(long id) {
        ParametersValidator.validateRecordId(id);

        return userDAO.getById(id);
    }

    @Override
    public User add(User objectToAdd) {
        ObjectValidator<User> validator = new UserValidator();
        validator.validate(objectToAdd);

        return userDAO.add(objectToAdd);
    }

    @Override
    public boolean updateById(User updatedObject) {
        ObjectValidator<User> validator = new UserValidator();
        validator.validate(updatedObject);

        return userDAO.updateById(updatedObject);
    }

    @Override
    public boolean deleteById(long id) {
        ParametersValidator.validateRecordId(id);

        return userDAO.deleteById(id);
    }

    @Override
    public long getCustomersNumberByManagerId(long managerId) {
        ParametersValidator.validateRecordId(managerId);
        checkUserIsManager(managerId);

        return userDAO.getCustomersNumberByManagerId(managerId);
    }

    @Override
    public List<Long> getCustomersIdByManagerId(long managerId, int pageNumber, int pageSize) {
        ParametersValidator.validateRecordId(managerId);
        ParametersValidator.validatePageSize(pageSize);
        ParametersValidator.validatePageNumber(pageNumber);
        checkUserIsManager(managerId);

        return userDAO.getCustomersIdByManagerId(managerId, pageNumber, pageSize);
    }

    @Override
    public boolean checkUserAssignedToManager(long customerId, long managerId) {
        ParametersValidator.validateRecordId(customerId);
        ParametersValidator.validateRecordId(managerId);
        checkUserIsCustomer(customerId);
        checkUserIsManager(managerId);

        long assignmentsNumber = userDAO.getAssignmentsNumber(customerId, managerId);

        return assignmentsNumber > 0;
    }

    @Override
    public boolean assignCustomerToManager(long customerId, long managerId) {
        ParametersValidator.validateRecordId(customerId);
        ParametersValidator.validateRecordId(managerId);
        checkUserIsCustomer(customerId);
        checkUserIsManager(managerId);

        boolean isAssigned = checkUserAssignedToManager(customerId, managerId);

        if (isAssigned) {
            throw new AssignmentException("Unable to assign: this customer is already assigned to this manager");
        }

        return userDAO.assignCustomerToManager(customerId, managerId);
    }

    @Override
    public boolean unassignCustomerFromManager(long customerId, long managerId) {
        ParametersValidator.validateRecordId(customerId);
        ParametersValidator.validateRecordId(managerId);
        checkUserIsCustomer(customerId);
        checkUserIsManager(managerId);

        boolean isAssigned = checkUserAssignedToManager(customerId, managerId);

        if (!isAssigned) {
            throw new AssignmentException("Unable to unassign: this customer isn't assigned to this manager");
        }

        return userDAO.unassignCustomerFromManager(customerId, managerId);
    }

    private void checkUserIsCustomer(long customerId) {
        ParametersValidator.validateRecordId(customerId);

        User customer = userDAO.getById(customerId);

        if (!"customer".equals(customer.getType())) {
            throw new AssignmentException("There is no customer with such id");
        }
    }

    private void checkUserIsManager(long managerId) {
        ParametersValidator.validateRecordId(managerId);

        User manager = userDAO.getById(managerId);

        if (!"manager".equals(manager.getType())) {
            throw new AssignmentException("There is no manager with such id");
        }
    }

}
