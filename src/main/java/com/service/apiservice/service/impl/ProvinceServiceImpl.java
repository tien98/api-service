package com.service.apiservice.service.impl;

import com.service.apiservice.model.DistrictDTO;
import com.service.apiservice.model.ProvinceDTO;
import com.service.apiservice.model.WardDTO;
import com.service.apiservice.repository.DistrictRepository;
import com.service.apiservice.repository.ProvinceRepository;
import com.service.apiservice.repository.WardRepository;
import com.service.apiservice.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    WardRepository wardRepository;

    public List<ProvinceDTO> getList() {
        return provinceRepository.findAllByStatusIs(1);
    }

    public List<DistrictDTO> getDistrictListByProvinceId(Integer provinceId) {
        return districtRepository.findAllByProvinceId(provinceId);
    }
    public List<WardDTO> getWardListByProvinceId(Integer districtId) {
        return wardRepository.findAllByDistrictId(districtId);
    }
}
