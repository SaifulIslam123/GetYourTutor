package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.JobApplication;

public interface JobService {
    Job createJobFromApplication(JobApplication application);
}
