package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.FuelTypeDAO;
import dev.andrylat.carsharing.models.FuelType;
import dev.andrylat.carsharing.services.FuelTypeService;
import dev.andrylat.carsharing.services.validators.Validator;
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
        Validator.validatePageNumber(pageNumber);
        Validator.validatePageSize(pageSize);

        return fuelTypeDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public FuelType getById(long id) {
        Validator.validateRecordId(id);

        return fuelTypeDAO.getById(id);
    }

    @Override
    public FuelType add(FuelType objectToAdd) {
        return null;
    }

    @Override
    public boolean updateById(FuelType updatedObject) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

}
