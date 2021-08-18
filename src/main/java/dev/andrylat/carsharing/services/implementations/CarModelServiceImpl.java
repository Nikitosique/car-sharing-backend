package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarModelDAO;
import dev.andrylat.carsharing.models.CarModel;
import dev.andrylat.carsharing.services.CarModelService;
import dev.andrylat.carsharing.services.validators.CarModelValidator;
import dev.andrylat.carsharing.services.validators.ObjectValidator;
import dev.andrylat.carsharing.services.validators.ParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarModelServiceImpl implements CarModelService {
    private final CarModelDAO carModelDAO;

    @Autowired
    public CarModelServiceImpl(CarModelDAO carModelDAO) {
        this.carModelDAO = carModelDAO;
    }

    @Override
    public long getRecordsNumber() {
        return carModelDAO.getRecordsNumber();
    }

    @Override
    public List<CarModel> getAll(int pageNumber, int pageSize) {
        ParametersValidator.validatePageNumber(pageNumber);
        ParametersValidator.validatePageSize(pageSize);

        return carModelDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public CarModel getById(long id) {
        ParametersValidator.validateRecordId(id);

        return carModelDAO.getById(id);
    }

    @Override
    public CarModel add(CarModel objectToAdd) {
        ObjectValidator<CarModel> validator = new CarModelValidator();
        validator.validate(objectToAdd);

        return carModelDAO.add(objectToAdd);
    }

    @Override
    public boolean updateById(CarModel updatedObject) {
        ObjectValidator<CarModel> validator = new CarModelValidator();
        validator.validate(updatedObject);

        return carModelDAO.updateById(updatedObject);
    }

    @Override
    public boolean deleteById(long id) {
        ParametersValidator.validateRecordId(id);

        return carModelDAO.deleteById(id);
    }

}
