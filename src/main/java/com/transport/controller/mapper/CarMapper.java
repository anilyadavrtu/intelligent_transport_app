package com.transport.controller.mapper;

import com.transport.datatransferobject.CarDTO;
import com.transport.domainobject.CarDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anil
 */
public class CarMapper {

    public static CarDO makeCarDO(CarDTO carDTO) {

        return new CarDO(
                carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.isConvertible(), carDTO.getRating(),
                carDTO.getEngineType(), carDTO.getManufacturer());
    }


    public static CarDTO makeCarDTO(CarDO carDO) {
        CarDTO.CarDTOBuilder carDTOBuilder =
                CarDTO
                        .newBuilder().setConvertible(carDO.isConvertible())
                        .setEngineType(carDO.getEngineType()).setLicensePlate(carDO.getLicensePlate()).setSeatCount(carDO.getSeatCount())
                        .setManufacturer(carDO.getManufacturer()).setRating(carDO.getRating()).setId(carDO.getId());
        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars) {
        return cars
                .stream()
                .map(CarMapper::makeCarDTO)
                .collect(Collectors.toList());
    }
}
