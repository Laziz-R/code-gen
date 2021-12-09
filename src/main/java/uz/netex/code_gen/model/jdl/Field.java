package uz.netex.code_gen.model.jdl;

import uz.netex.code_gen.model.jdl.type.Type;
import uz.netex.code_gen.util.CaseUtil;
import uz.netex.code_gen.util.CodeUtil;

public class Field {
    private Type type;
    private Name name;
    private Validation validation;
    private boolean isList;

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

    public Field setName(String camelName) {
        this.name = new Name(camelName, CaseUtil.CAMEL_CASE);
        return this;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    public Field setIsList(boolean isList) {
        this.isList = isList;
        return this;
    }

    public boolean isList() {
        return isList;
    }

    /**
     * Making Field object from string
     * @param content for example, [userId Long unique]
     * @return
     */
    public static Field make(String content) {
        Field field = new Field();

        content = CodeUtil.remove2Probels(content.replaceAll("\\s", " ")).trim();
        String[] items = content.split(" ");
        Validation validation = null;
        for (int i = 0; i < items.length; i++) {
            switch (i) {
                case 0 -> field.setName(items[0]);
                case 1 -> field.setType(new Type(items[1]));
                default -> {
                    if (validation == null)
                        validation = new Validation();
                    switch (items[i]) {
                        case "required" -> validation.setRequired(true);
                        case "unique" -> validation.setUnique(true);
                        default -> {
                            if (items[i].startsWith("max")) {
                                String number = items[i].split("\\(")[1].split("\\)")[0].trim();
                                validation.setMax(Long.parseLong(number));
                            } else if (items[i].startsWith("min")) {
                                String number = items[i].split("\\(")[1].split("\\)")[0].trim();
                                validation.setMin(Long.parseLong(number));
                            } else if (items[i].startsWith("pattern")) {
                                String pattern = items[i].split("\\(")[1].split("\\)")[0].trim();
                                validation.setPattern(pattern);
                            }
                        }
                    }
                }
            }
        }
        field.setValidation(validation);
        return field;
    }
}
