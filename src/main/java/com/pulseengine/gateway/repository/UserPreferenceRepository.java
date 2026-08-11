package com.pulseengine.gateway.repository;

import com.pulseengine.gateway.model.UserPreference;
import com.pulseengine.gateway.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {

    @Query("SELECT uc.channel FROM UserChannel uc " +
            "JOIN UserPreference up ON uc.userPreferenceId = up.id " +
            "WHERE up.userId = :userId AND up.tenantId = :tenantId AND uc.isEnabled = true")
    List<String> findSubscribedChannel(@Param("userId") String userId,
                                       @Param("tenantId") String tenantId);


    // If you are fetching the UserPreference entity instead, use this format:
    Optional<UserPreference> findByUserIdAndTenantId(String userId, String tenantId);
}