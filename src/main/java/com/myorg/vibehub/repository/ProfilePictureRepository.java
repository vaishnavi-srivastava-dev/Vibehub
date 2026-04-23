package com.myorg.vibehub.repository;

import com.myorg.vibehub.model.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, UUID> {
}
