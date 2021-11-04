package uz.netex.code_gen.model.jdl;

import uz.netex.code_gen.util.CaseUtil;

import java.util.ArrayList;
import java.util.List;

public class Enum {
    private Name name;
    private List<String> fields = new ArrayList<>();

    public Name getName() {
        return this.name;
    }

    public Enum setName(Name name) {
        this.name = name;
        return this;
    }

    public Enum setName(String upperName) {
        this.name = new Name(upperName, CaseUtil.UPPER_CASE);
        return this;
    }

    public List<String> getFields() {
        return this.fields;
    }

    public Enum setFields(List<String> fields) {
        this.fields = fields;
        return this;
    }
}
