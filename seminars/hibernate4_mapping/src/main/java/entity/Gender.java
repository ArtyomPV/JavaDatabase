package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN("U");
    private final String code;

    public static Gender fromCode(String code){
        if(code.equals("M") ) {
            return MALE;
        }
        if(code.equals("F")) {
            return FEMALE;
        }
        if(code.equals("U")) {
            return UNKNOWN;
        }
        throw new IllegalArgumentException();
    }

}
