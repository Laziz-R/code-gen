package uz.netex.code_gen.model.jdl;

import uz.netex.code_gen.common.JdlCode;
import uz.netex.code_gen.model.jdl.type.Type;
import uz.netex.code_gen.util.CodeUtil;

import java.util.ArrayList;
import java.util.List;

public class Relationship2 {
    private RelationType type;
    private Entity fromEntity;
    private Entity toEntity;
    private Field fromField;
    private Field toField;

    public Relationship2(RelationType type) {
        this.type = type;
    }

    public Relationship2 setFromEntity(Entity fromEntity) {
        this.fromEntity = fromEntity;
        return this;
    }

    public Relationship2 setToEntity(Entity toEntity) {
        this.toEntity = toEntity;
        return this;
    }

    public Relationship2 setFromField(Field fromField) {
        this.fromField = fromField;
        return this;
    }

    public Relationship2 setToField(Field toField) {
        this.toField = toField;
        return this;
    }

    public static List<Relationship2> make(String head, String body) {
        List<Relationship2> relations = new ArrayList<>();

        // head
        String name = head.split(" ", 2)[1].trim();
        RelationType relType = switch (name.toLowerCase()) {
            case "onetoone" -> RelationType.ONE_TO_ONE;
            case "onetomany" -> RelationType.ONE_TO_MANY;
            case "manytoone" -> RelationType.MANY_TO_ONE;
            case "manytomany" -> RelationType.MANY_TO_MANY;
            default -> null;
        };

        // body

        for (String line : body.lines().toList()) {
            Relationship2 relation = new Relationship2(relType);
            line = line.trim();

            String[] items = CodeUtil.remove2Probels(line).split(" to ");
            if (items.length > 1) {
                String[] from = items[0].split("\\{");
                String[] to = items[1].split("\\{");

                relation
                        .setFromEntity(findByName(from[0]))
                        .setToEntity(findByName(to[0]));

                if (from.length > 1) {
                    from[1] = from[1].replace("}", "");
                    Field field = new Field();

                }
//                Entity fromEntity = defOneSide(items[0]);
//                Entity toEntity = defOneSide(items[1]);
//
//                if (!fromEntity.getFields().isEmpty()) {
//                    Field fromField = fromEntity.getFields().get(0);
//                    switch (type) {
//                        case ONE_TO_ONE, MANY_TO_ONE, MANY_TO_MANY -> fromField.setType(new Type(toEntity.getPascalName()).setpName("Long"));
//                        case ONE_TO_MANY -> fromField.setType(new Type(toEntity.getName().getPascalCase() + "List"));
//                    }
//                }
//
//                if (!toEntity.getFields().isEmpty()) {
//                    Field toField = toEntity.getFields().get(0);
//                    switch (type) {
//                        case ONE_TO_ONE, ONE_TO_MANY, MANY_TO_MANY -> toField.setType(new Type(fromEntity.getName().getPascalCase()));
//                        case MANY_TO_ONE -> toField.setType(new Type(fromEntity.getName().getPascalCase() + "List"));
//                    }
//                }
                relations.add(relation);
            }
        }
        return relations;
    }

    private static Entity findByName(String eName) {
        for (Entity e : JdlCode.entities)
            if (e.getPascalName().equals(eName))
                return e;
        return null;
    }
}
