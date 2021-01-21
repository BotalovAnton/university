package org.foxminded.botalov.repository.specification.forgroup;

import org.foxminded.botalov.model.group.Group;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GroupSpecification {

    public Specification<Group> filterGroups(GroupFilter filter) {
        Specification<Group> specification = Specification.where(null);

        if (filter.getGroupId() != null && !filter.getGroupId().isEmpty()) {
            specification = specification.and(searchByGroupId(filter.getGroupId()));
        }

        if (filter.getGroupName() != null && !filter.getGroupName().isEmpty()) {
            specification = specification.and(searchByGroupName(filter.getGroupName()));
        }

        return specification;
    }

    private Specification<Group> searchByGroupId(String groupId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), groupId);
    }

    private Specification<Group> searchByGroupName(String groupName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), groupName);
    }
}
