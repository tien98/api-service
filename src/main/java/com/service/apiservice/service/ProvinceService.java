package com.service.apiservice.service;

import com.service.apiservice.model.DistrictDTO;
import com.service.apiservice.model.ProvinceDTO;
import com.service.apiservice.model.WardDTO;

import java.util.List;

public interface ProvinceService {
    List<ProvinceDTO> getList();
    List<DistrictDTO> getDistrictListByProvinceId(Integer provinceId);
    List<WardDTO> getWardListByProvinceId(Integer districtId);
}
