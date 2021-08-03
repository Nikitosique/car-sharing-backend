package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarBrandDAO;
import dev.andrylat.carsharing.models.CarBrand;
import dev.andrylat.carsharing.services.CarBrandService;
import dev.andrylat.carsharing.services.validators.Validator;
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
        Validator.validatePageNumber(pageNumber);
        Validator.validatePageSize(pageSize);

        return carBrandDAO.getAll(pageNumber, pageSize);
    }

    @Override
    public CarBrand getById(long id) {
        Validator.validateRecordId(id);

        return carBrandDAO.getById(id);
    }

    @Override
    public CarBrand add(CarBrand objectToAdd) {
        return null;
    }

    @Override
    public boolean updateById(CarBrand updatedObject) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

}
