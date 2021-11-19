package uz.netex.code_gen.common;


import uz.netex.code_gen.common.maker.EntityMaker;
import uz.netex.code_gen.common.maker.EnumMaker;
import uz.netex.code_gen.common.maker.RelationsMaker;
import uz.netex.code_gen.model.jdl.*;
import uz.netex.code_gen.model.jdl.Enum;
import uz.netex.code_gen.util.CodeUtil;

import java.util.ArrayList;
import java.util.List;

public class JdlCode {
    private final String code;
    private List<Entity> entities = new ArrayList<>();
    private List<Enum> enums = new ArrayList<>();
    private List<Relationship> relations = new ArrayList<>();
    private List<String> others = new ArrayList<>();

    public JdlCode(String code) {
        this.code = CodeUtil.removeComments(code);
    }

    public String getCode() {
        return code;
    }

    public void define() {
        int cursor = 0;
        while (cursor < code.length()) {
            int openIndex = code.indexOf("{", cursor);
            if (openIndex < 0)
                return;
            int closeIndex = CodeUtil.findCloseBkt(code, openIndex);
            if (closeIndex < 0)
                return;
            String head = code.substring(cursor, openIndex).trim();
            String body = code.substring(openIndex + 1, closeIndex).trim();

            if (head.startsWith("entity")) {
                entities.add(new EntityMaker().make(head, body));
            } else if (head.startsWith("enum")) {
                enums.add(new EnumMaker().make(head, body));
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

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Enum> getEnums() {
        return this.enums;
    }

    public void setEnums(List<Enum> enums) {
        this.enums = enums;
    }

    public List<String> getOthers() {
        return this.others;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }

    private Entity getByName(Name eName) {
        for (Entity e : entities)
            if (e.getName().equals(eName))
                return e;
        return new Entity();
    }

}