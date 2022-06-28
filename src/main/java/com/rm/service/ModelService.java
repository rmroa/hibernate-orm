package com.rm.service;

import com.rm.dao.ModelRepository;
import com.rm.dto.ModelCreateDto;
import com.rm.dto.ModelReadDto;
import com.rm.entity.Model;
import com.rm.mapper.Mapper;
import com.rm.mapper.ModelCreateMapper;
import com.rm.mapper.ModelReadMapper;
import com.rm.validation.UpdateCheck;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final ModelReadMapper modelReadMapper;
    private final ModelCreateMapper modelCreateMapper;

    @Transactional
    public Long create(ModelCreateDto modelCreateDto) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ModelCreateDto>> validationResult = validator.validate(modelCreateDto, UpdateCheck.class);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        Model modelEntity = modelCreateMapper.mapFrom(modelCreateDto);
        return modelRepository.save(modelEntity).getId();
    }

    @Transactional
    public Optional<ModelReadDto> findById(Long id) {
        return findById(id, modelReadMapper);
    }

    @Transactional
    public <T> Optional<T> findById(Long id, Mapper<Model, T> mapper) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), modelRepository.getEntityManager().getEntityGraph("withManufacturer")
        );
        return modelRepository.findById(id, properties)
                .map(mapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Model> maybeModel = modelRepository.findById(id);
        maybeModel.ifPresent(model -> modelRepository.delete(model.getId()));
        return maybeModel.isPresent();
    }
}
