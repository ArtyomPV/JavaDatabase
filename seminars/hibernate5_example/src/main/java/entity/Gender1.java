package entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  Gender1 {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN("U");

    private final String code;

    public static Gender1 fromCode(String code){
        if(code.equals("M")) return MALE;
        if(code.equals("F")) return FEMALE;
        return UNKNOWN;
    }

}
