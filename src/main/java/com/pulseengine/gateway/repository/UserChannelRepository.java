package com.pulseengine.gateway.repository;

import com.pulseengine.gateway.model.UserChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannel, Long> {

    // Fetch all UserChannel links for this specific user preference ID
    List<UserChannel> findByUserPreferenceId(Long userPreferenceId);
}