package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarBrandDAO;
import dev.andrylat.carsharing.models.CarBrand;
import dev.andrylat.carsharing.services.CarBrandService;
import dev.andrylat.carsharing.services.validators.CarBrandValidator;
import dev.andrylat.carsharing.services.validators.ObjectValidator;
import dev.andrylat.carsharing.services.validators.ParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarBrandServiceImpl implements CarBrandService {
    private final CarBrandDAO carBrandDAO;

    @Autowired
    public CarBrandServiceImpl(CarBrandDAO carBrandDAO) {
        this.carBrandDAO = carBrandDAO;
    }

    @Override
    public long getRecordsNumber() {
        return carBrandDAO.getRecordsNumber();
    }

    @Override
    public List<CarBrand> getAll(int pageNumber, int pageSize) {
        ParametersValidator.validatePageNumber(pageNumber);
        ParametersValidator.validatePageSize(pageSize);

        return carBrandDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public CarBrand getById(long id) {
        ParametersValidator.validateRecordId(id);

        return carBrandDAO.getById(id);
    }

    @Override
    public CarBrand add(CarBrand objectToAdd) {
        ObjectValidator<CarBrand> validator = new CarBrandValidator();
        validator.validate(objectToAdd);

        return carBrandDAO.add(objectToAdd);
    }

    @Override
    public boolean updateById(CarBrand updatedObject) {
        ObjectValidator<CarBrand> validator = new CarBrandValidator();
        validator.validate(updatedObject);

        return carBrandDAO.updateById(updatedObject);
    }

    @Override
    public boolean deleteById(long id) {
        ParametersValidator.validateRecordId(id);

        return carBrandDAO.deleteById(id);
    }

}
