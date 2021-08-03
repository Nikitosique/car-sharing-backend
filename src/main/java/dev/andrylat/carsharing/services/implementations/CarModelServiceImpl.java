package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarModelDAO;
import dev.andrylat.carsharing.models.CarModel;
import dev.andrylat.carsharing.services.CarModelService;
import dev.andrylat.carsharing.services.validators.Validator;
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
        Validator.validatePageNumber(pageNumber);
        Validator.validatePageSize(pageSize);

        return carModelDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public CarModel getById(long id) {
        Validator.validateRecordId(id);

        return carModelDAO.getById(id);
    }

    @Override
    public CarModel add(CarModel objectToAdd) {
        return null;
    }

    @Override
    public boolean updateById(CarModel updatedObject) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

}
