package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.BodyTypeDAO;
import dev.andrylat.carsharing.models.BodyType;
import dev.andrylat.carsharing.services.BodyTypeService;
import dev.andrylat.carsharing.services.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BodyTypeServiceImpl implements BodyTypeService {
    private final BodyTypeDAO bodyTypeDAO;

    @Autowired
    public BodyTypeServiceImpl(BodyTypeDAO bodyTypeDAO) {
        this.bodyTypeDAO = bodyTypeDAO;
    }

    @Override
    public long getRecordsNumber() {
        return bodyTypeDAO.getRecordsNumber();
    }

    @Override
    public List<BodyType> getAll(int pageNumber, int pageSize) {
        Validator.validatePageNumber(pageNumber);
        Validator.validatePageSize(pageSize);

        return bodyTypeDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public BodyType getById(long id) {
        Validator.validateRecordId(id);

        return bodyTypeDAO.getById(id);
    }

    @Override
    public BodyType add(BodyType objectToAdd) {
        return null;
    }

    @Override
    public boolean updateById(BodyType updatedObject) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

}
