package uz.netex.code_gen.common;


import uz.netex.code_gen.common.maker.EntityMaker;
import uz.netex.code_gen.common.maker.EnumMaker;
import uz.netex.code_gen.common.maker.RelationsMaker;
import uz.netex.code_gen.model.jdl.Entity;
import uz.netex.code_gen.model.jdl.Field;
import uz.netex.code_gen.model.jdl.Enum;
import uz.netex.code_gen.model.jdl.Relationship;
import uz.netex.code_gen.model.jdl.type.Type;
import uz.netex.code_gen.model.sql.ForeignKey;
import uz.netex.code_gen.util.CodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Field fromField, toField;

        if (fromEntity.getFields().isEmpty()) {
            fromField = new Field()
                    .setName(toEntity.getName().getCamelCase() + "Id");
        } else {
            fromField = fromEntity.getFields().get(0);
        }

        if (toEntity.getFields().isEmpty()) {
            toField = new Field()
                    .setName(toEntity.getName().getCamelCase() + "Id");
        } else {
            fromField = fromEntity.getFields().get(0);
        }

        for (Entity e : entities) {
            if (e.getName().equals(fromEntity.getName())) {
                for (Field f : e.getFields()) {
                    if ()
                }
            }
        }

        switch (rel.getType()) {
            case ONE_TO_ONE:
            case MANY_TO_ONE:
                fromEntity.getFields().add(fromFK.getFromField());
                foreignKeys.add(fromFK);
                break;
            case ONE_TO_MANY:
                toEntity.getFields().add(toFK.getFromField());
                foreignKeys.add(toFK);
                break;
            case MANY_TO_MANY:
                Entity e = new Entity();
                e.getFields().add(fromFK.getFromField());
                e.getFields().add(toFK.getFromField());
                entities.add(e);

                foreignKeys.add(fromFK);
                foreignKeys.add(toFK);
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

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }

}