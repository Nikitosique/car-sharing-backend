package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.BodyTypeDAO;
import dev.andrylat.carsharing.models.BodyType;
import dev.andrylat.carsharing.services.BodyTypeService;
import dev.andrylat.carsharing.services.validators.BodyTypeValidator;
import dev.andrylat.carsharing.services.validators.ObjectValidator;
import dev.andrylat.carsharing.services.validators.ParametersValidator;
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
        ParametersValidator.validatePageNumber(pageNumber);
        ParametersValidator.validatePageSize(pageSize);

        return bodyTypeDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public BodyType getById(long id) {
        ParametersValidator.validateRecordId(id);

        return bodyTypeDAO.getById(id);
    }

    @Override
    public BodyType add(BodyType objectToAdd) {
        ObjectValidator<BodyType> validator = new BodyTypeValidator();
        validator.validate(objectToAdd);

        return bodyTypeDAO.add(objectToAdd);
    }

    @Override
    public boolean updateById(BodyType updatedObject) {
        ObjectValidator<BodyType> validator = new BodyTypeValidator();
        validator.validate(updatedObject);

        return bodyTypeDAO.updateById(updatedObject);
    }

    @Override
    public boolean deleteById(long id) {
        ParametersValidator.validateRecordId(id);

        return bodyTypeDAO.deleteById(id);
    }

}
