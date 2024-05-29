<<<<<<<< HEAD:milkstore/src/main/java/com/cookswp/milkstore/pojo/dtos/RoleModel/Role.java
package com.cookswp.milkstore.pojo.dtos.RoleModel;
========
package com.cookswp.milkstore.pojo.dtos.UserModel;
>>>>>>>> refs/heads/HuuHien:milkstore/src/main/java/com/cookswp/milkstore/pojo/dtos/UserModel/Role.java

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
