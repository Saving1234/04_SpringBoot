package org.example.restapi.section04;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString

public class UserDTO {

    private int no;
    private String id;
    private String pw;
    private String name;
    private Date enrollAT;

    public UserDTO(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public UserDTO linkTo() {
        return null;
    }
}
