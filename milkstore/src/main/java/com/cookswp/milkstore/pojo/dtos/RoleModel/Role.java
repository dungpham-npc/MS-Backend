<<<<<<<< HEAD:milkstore/src/main/java/com/cookswp/milkstore/pojo/dtos/UserModel/Role.java
package com.cookswp.milkstore.pojo.dtos.UserModel;
========
package com.cookswp.milkstore.pojo.dtos.RoleModel;
>>>>>>>> refs/remotes/origin/HaiHau:milkstore/src/main/java/com/cookswp/milkstore/pojo/dtos/RoleModel/Role.java

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;
}
