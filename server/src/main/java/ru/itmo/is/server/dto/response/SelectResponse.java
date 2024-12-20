package ru.itmo.is.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectResponse {
    private Integer id;
    private String name;

    public SelectResponse(Integer id, Long x, Double y) {
        this.id = id;
        this.name = String.format("(%d,%f)", x, y);
    }
}
