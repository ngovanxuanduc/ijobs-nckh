package com.immortal.internship.service;

import com.immortal.internship.payload.response.AfterLoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface CommonService {
    AfterLoginResponse loadInformation();
}
