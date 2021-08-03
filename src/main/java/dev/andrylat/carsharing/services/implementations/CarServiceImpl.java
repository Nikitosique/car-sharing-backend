package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarDAO;
import dev.andrylat.carsharing.models.Car;
import dev.andrylat.carsharing.services.CarService;
import dev.andrylat.carsharing.services.validators.Validator;
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
        Validator.validatePageNumber(pageNumber);
        Validator.validatePageSize(pageSize);

        return carDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public Car getById(long id) {
        Validator.validateRecordId(id);

        return carDAO.getById(id);
    }

    @Override
    public Car add(Car objectToAdd) {
        return null;
    }

    @Override
    public boolean updateById(Car updatedObject) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

}
