package ru.prusov.jbdcexample.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class Car {
    private Integer id;
    private String name;
    private Integer year;
    private Integer employee_id;
}
