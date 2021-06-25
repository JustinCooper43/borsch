package com.nayax.borsch.service.impl;

import com.nayax.borsch.repository.impl.ProfileRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    ProfileRepositoryImplementation profileRepositoryImplementation;

}
