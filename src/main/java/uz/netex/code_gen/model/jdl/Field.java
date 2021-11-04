package uz.netex.code_gen.model.jdl;

import uz.netex.code_gen.model.jdl.type.Type;
import uz.netex.code_gen.util.CaseUtil;

public class Field {
    private Type type;
    private Name name;
    private Validation validation;

    public Type getType() {
        return this.type;
    }

    public Field setType(Type type) {
        this.type = type;
        return this;
    }

    public Name getName() {
        return this.name;
    }

    public Field setName(Name name) {
        this.name = name;
        return this;
    }

    public Field setName(String camelName){
        this.name = new Name(camelName, CaseUtil.CAMEL_CASE);
        return this;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }
}
