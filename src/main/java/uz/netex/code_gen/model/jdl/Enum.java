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

    public static Enum make(String head, String body) {
        Enum enum1 = new Enum();

        // head
        String name = head.split(" ", 2)[1].trim();
        enum1.setName(name);

        // body
        for (String line : body.lines().toList()) {
            enum1.getFields().add(line.trim());
        }
        return enum1;
    }
}
