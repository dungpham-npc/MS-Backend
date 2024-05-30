<<<<<<<< HEAD:milkstore/src/main/java/com/cookswp/milkstore/pojo/dtos/UserModel/UserDTO.java
package com.cookswp.milkstore.pojo.dtos.UserModel;
========
package com.cookswp.milkstore.model;
>>>>>>>> refs/remotes/origin/HaiHau:milkstore/src/main/java/com/cookswp/milkstore/dtos/UserDTO.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String emailAddress;
    private String phoneNumber;
    private String username;
}
