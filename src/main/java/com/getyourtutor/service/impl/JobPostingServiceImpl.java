package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.JobPosting;
import com.getyourtutor.domain.entity.JobPostingStatus;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.repository.JobPostingRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public JobPosting createJobPosting(JobPosting jobPosting, String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found with username: " + username));

        jobPosting.setCreatedBy(user);
        jobPosting.setStatus(JobPostingStatus.OPEN);

        return jobPostingRepository.save(jobPosting);
    }
}
