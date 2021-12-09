package uz.netex.code_gen.common;

import uz.netex.code_gen.common.maker.RelationsMaker;
import uz.netex.code_gen.model.jdl.*;
import uz.netex.code_gen.model.jdl.Enum;
import uz.netex.code_gen.util.CodeUtil;

import java.util.ArrayList;
import java.util.List;

public class JdlCode {
    private final String code;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Enum> enums = new ArrayList<>();
    public static List<Relationship> relations = new ArrayList<>();

    public JdlCode(String code) {
        this.code = CodeUtil.removeComments(code);
    }

    public void define() {
        int cursor = 0;
        while (cursor < code.length()) {
            int openIndex = code.indexOf("{", cursor);
            if (openIndex < 0)
                return;
            int closeIndex = CodeUtil.findCloseBrc(code, openIndex);
            if (closeIndex < 0)
                return;
            String head = code.substring(cursor, openIndex).trim();
            String body = code.substring(openIndex + 1, closeIndex).trim();

            if (head.startsWith("entity")) {
                entities.add(Entity.make(head, body));
            } else if (head.startsWith("enum")) {
                enums.add(Enum.make(head, body));
            } else if (head.startsWith("relationship")) {
                for (Relationship rel : new RelationsMaker().make(head, body)) {
                    relate(rel);
                }
            }
            cursor = closeIndex + 1;
        }
    }

    private void relate(Relationship rel) {
        Entity fromEntity = rel.getFromEntity();
        Entity toEntity = rel.getToEntity();

        Field fromField = !fromEntity.getFields().isEmpty()
                ? fromEntity.getFields().get(0)
                : null;

        Field toField = !toEntity.getFields().isEmpty()
                ? fromEntity.getFields().get(0)
                : null;

        if (rel.getType().equals(RelationType.MANY_TO_MANY)) {
            Entity thirdEntity = new Entity()
                    .setName(fromEntity.getPascalName() + toEntity.getPascalName());
            thirdEntity.getFields().add(fromField);
            thirdEntity.getFields().add(toField);
            entities.add(thirdEntity);
        }

    }
}