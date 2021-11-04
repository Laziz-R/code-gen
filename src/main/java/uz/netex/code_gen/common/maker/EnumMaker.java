package uz.netex.code_gen.common.maker;

import uz.netex.code_gen.model.jdl.Enum;

public class EnumMaker {

    private final Enum enum1;

    public EnumMaker() {
        enum1 = new Enum();
    }

    public Enum make(String head, String body) {
        headSolver(head);
        bodySolver(body);
        return enum1;
    }

    private void headSolver(String head) {
        String name = head.split(" ", 2)[1].trim();
        enum1.setName(name);
    }

    private void bodySolver(String body) {
        for (String line : body.split("\n")) {
            line = line.trim();
            if (!line.equals("")) {
                enum1.getFields().add(line);
            }
        }
    }

}
