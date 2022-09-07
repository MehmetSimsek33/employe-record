package macademia.employerecord.core.excaption.utilities.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
    ModelMapper forResponse();
    ModelMapper forRequest();
}