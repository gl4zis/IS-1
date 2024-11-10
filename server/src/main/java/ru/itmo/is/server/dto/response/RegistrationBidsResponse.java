package ru.itmo.is.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationBidsResponse {
    private List<String> bids;
}
