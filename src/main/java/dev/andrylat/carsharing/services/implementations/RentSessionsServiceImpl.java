package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.RentSessionDAO;
import dev.andrylat.carsharing.models.RentSession;
import dev.andrylat.carsharing.services.RentSessionService;
import dev.andrylat.carsharing.services.validators.RentSessionValidator;
import dev.andrylat.carsharing.services.validators.ObjectValidator;
import dev.andrylat.carsharing.services.validators.ParametersValidator;
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
        ParametersValidator.validatePageNumber(pageNumber);
        ParametersValidator.validatePageSize(pageSize);

        return rentSessionDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public RentSession getById(long id) {
        ParametersValidator.validateRecordId(id);

        return rentSessionDAO.getById(id);
    }

    @Override
    public RentSession add(RentSession objectToAdd) {
        ObjectValidator<RentSession> validator = new RentSessionValidator();
        validator.validate(objectToAdd);

        return rentSessionDAO.add(objectToAdd);
    }

    @Override
    public boolean updateById(RentSession updatedObject) {
        ObjectValidator<RentSession> validator = new RentSessionValidator();
        validator.validate(updatedObject);

        return rentSessionDAO.updateById(updatedObject);
    }

    @Override
    public boolean deleteById(long id) {
        ParametersValidator.validateRecordId(id);

        return rentSessionDAO.deleteById(id);
    }

}
