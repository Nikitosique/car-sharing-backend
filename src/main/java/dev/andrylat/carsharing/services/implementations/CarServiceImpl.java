package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarDAO;
import dev.andrylat.carsharing.models.Car;
import dev.andrylat.carsharing.services.CarService;
import dev.andrylat.carsharing.services.validators.CarValidator;
import dev.andrylat.carsharing.services.validators.ObjectValidator;
import dev.andrylat.carsharing.services.validators.ParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarServiceImpl implements CarService {
    private final CarDAO carDAO;

    @Autowired
    public CarServiceImpl(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    @Override
    public long getRecordsNumber() {
        return carDAO.getRecordsNumber();
    }

    @Override
    public List<Car> getAll(int pageNumber, int pageSize) {
        ParametersValidator.validatePageNumber(pageNumber);
        ParametersValidator.validatePageSize(pageSize);

        return carDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public Car getById(long id) {
        ParametersValidator.validateRecordId(id);

        return carDAO.getById(id);
    }

    @Override
    public Car add(Car objectToAdd) {
        ObjectValidator<Car> validator = new CarValidator();
        validator.validate(objectToAdd);

        return carDAO.add(objectToAdd);
    }

    @Override
    public boolean updateById(Car updatedObject) {
        ObjectValidator<Car> validator = new CarValidator();
        validator.validate(updatedObject);

        return carDAO.updateById(updatedObject);
    }

    @Override
    public boolean deleteById(long id) {
        ParametersValidator.validateRecordId(id);

        return carDAO.deleteById(id);
    }

}
