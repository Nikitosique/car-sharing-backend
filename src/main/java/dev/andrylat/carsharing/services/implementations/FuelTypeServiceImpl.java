package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.FuelTypeDAO;
import dev.andrylat.carsharing.models.FuelType;
import dev.andrylat.carsharing.services.FuelTypeService;
import dev.andrylat.carsharing.services.validators.FuelTypeValidator;
import dev.andrylat.carsharing.services.validators.ObjectValidator;
import dev.andrylat.carsharing.services.validators.ParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FuelTypeServiceImpl implements FuelTypeService {
    private final FuelTypeDAO fuelTypeDAO;

    @Autowired
    public FuelTypeServiceImpl(FuelTypeDAO fuelTypeDAO) {
        this.fuelTypeDAO = fuelTypeDAO;
    }

    @Override
    public long getRecordsNumber() {
        return fuelTypeDAO.getRecordsNumber();
    }

    @Override
    public List<FuelType> getAll(int pageNumber, int pageSize) {
        ParametersValidator.validatePageNumber(pageNumber);
        ParametersValidator.validatePageSize(pageSize);

        return fuelTypeDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public FuelType getById(long id) {
        ParametersValidator.validateRecordId(id);

        return fuelTypeDAO.getById(id);
    }

    @Override
    public FuelType add(FuelType objectToAdd) {
        ObjectValidator<FuelType> validator = new FuelTypeValidator();
        validator.validate(objectToAdd);

        return fuelTypeDAO.add(objectToAdd);
    }

    @Override
    public boolean updateById(FuelType updatedObject) {
        ObjectValidator<FuelType> validator = new FuelTypeValidator();
        validator.validate(updatedObject);

        return fuelTypeDAO.updateById(updatedObject);
    }

    @Override
    public boolean deleteById(long id) {
        ParametersValidator.validateRecordId(id);

        return fuelTypeDAO.deleteById(id);
    }

}
