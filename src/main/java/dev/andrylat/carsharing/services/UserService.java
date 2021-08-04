package dev.andrylat.carsharing.services;

import dev.andrylat.carsharing.models.User;

import java.util.List;

public interface UserService extends CrudService<User> {
    long getCustomersNumberByManagerId(long managerId);

    List<Long> getCustomersIdByManagerId(long managerId, int pageNumber, int pageSize);

    boolean assignCustomerToManager(long customerId, long managerId);

    boolean unassignCustomerFromManager(long customerId, long managerId);
    
}
