package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.RentSessionDAO;
import dev.andrylat.carsharing.models.RentSession;
import dev.andrylat.carsharing.services.RentSessionService;
import dev.andrylat.carsharing.services.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentSessionsServiceImpl implements RentSessionService {
    private final RentSessionDAO rentSessionDAO;

    @Autowired
    public RentSessionsServiceImpl(RentSessionDAO rentSessionDAO) {
        this.rentSessionDAO = rentSessionDAO;
    }

    @Override
    public long getRecordsNumber() {
        return rentSessionDAO.getRecordsNumber();
    }

    @Override
    public List<RentSession> getAll(int pageNumber, int pageSize) {
        Validator.validatePageNumber(pageNumber);
        Validator.validatePageSize(pageSize);

        return rentSessionDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public RentSession getById(long id) {
        Validator.validateRecordId(id);

        return rentSessionDAO.getById(id);
    }

    @Override
    public RentSession add(RentSession objectToAdd) {
        return null;
    }

    @Override
    public boolean updateById(RentSession updatedObject) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

}
