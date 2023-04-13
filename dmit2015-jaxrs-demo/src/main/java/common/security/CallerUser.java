package common.security;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CallerUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Size(min = 3, max = 32, message = "Enter a username that contains {min} to {max} characters")
    @Column(length = 32, unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CallerGroup", joinColumns = {@JoinColumn(name = "username")})
    @Column(name = "groupname", nullable = false)
    private Set<String> groups = new HashSet<>();

    public String getCsvGroups() {
        return groups.stream().collect(Collectors.joining(","));
    }

    @Version
    private Integer version;

    @Column(nullable = false)
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @PrePersist
    private void beforePersist() {
        createTime = LocalDateTime.now();
    }

    @PreUpdate
    private void beforeUpdate() {
        updateTime = LocalDateTime.now();
    }

}