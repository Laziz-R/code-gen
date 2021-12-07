package uz.netex.code_gen.common.maker;

import uz.netex.code_gen.model.jdl.*;
import uz.netex.code_gen.model.jdl.type.Type;
import uz.netex.code_gen.util.CodeUtil;

import java.util.ArrayList;
import java.util.List;

public class RelationsMaker {

    private RelationType type;
    private final List<Relationship> relations;

    public RelationsMaker() {
        relations = new ArrayList<>();
    }

    public List<Relationship> make(String head, String body) {
        headSolver(head);
        bodySolver(body);
        return relations;
    }

    private void headSolver(String head) {
        String name = head.split(" ", 2)[1].trim();
        switch (name.toLowerCase()) {
            case "onetoone":
                type = RelationType.ONE_TO_ONE;
                break;
            case "onetomany":
                type = RelationType.ONE_TO_MANY;
                break;
            case "manytoone":
                type = RelationType.MANY_TO_ONE;
                break;
            case "manytomany":
                type = RelationType.MANY_TO_MANY;
                break;
            default:
                break;
        }
    }

    private void bodySolver(String body) {
        for (String line : body.split("\n")) {
            Relationship relation = new Relationship();

            line = line.trim();

            String[] items = CodeUtil.remove2Probels(line).split(" to ");
            if (items.length > 1) {
                Entity fromEntity = defOneSide(items[0]);
                Entity toEntity = defOneSide(items[1]);

                if (!fromEntity.getFields().isEmpty()) {
                    Field fromField = fromEntity.getFields().get(0);
                    switch (type) {
                        case ONE_TO_ONE:
                        case MANY_TO_ONE:
                        case MANY_TO_MANY:
                            fromField.setType(new Type(toEntity.getPascalName()).setpName("Long"));
                            break;
                        case ONE_TO_MANY:
                            fromField.setType(new Type(toEntity.getName().getPascalCase() + "List"));
                            break;
                    }
                }

                if (!toEntity.getFields().isEmpty()) {
                    Field toField = toEntity.getFields().get(0);
                    switch (type) {
                        case ONE_TO_ONE:
                        case ONE_TO_MANY:
                        case MANY_TO_MANY:
                            toField.setType(new Type(fromEntity.getName().getPascalCase()));
                            break;
                        case MANY_TO_ONE:
                            toField.setType(new Type(fromEntity.getName().getPascalCase() + "List"));
                            break;
                    }
                }
                relations.add(relation);
            }
        }
    }

    private Entity defOneSide(String item) {
        Entity entity = new Entity();
        item = item.replaceAll("[{}]", " ").trim();
        String[] slices = item.split("\\s", 2);
        entity.setName(slices[0].trim());
        if (slices.length > 1) {
            Field field = new Field();
            field.setName(slices[1].split("\\s")[0]);
            if (slices[1].endsWith("required"))
                field.setValidation(new Validation().setRequired(true));
            entity.getFields().add(field);
        }
        return entity;
    }

}
