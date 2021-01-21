package org.foxminded.botalov.repository;

import org.foxminded.botalov.model.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer>, JpaSpecificationExecutor<Group> {
}
