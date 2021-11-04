package uz.netex.code_gen.common.maker;

import org.apache.log4j.Logger;
import uz.netex.code_gen.model.jdl.Entity;
import uz.netex.code_gen.model.jdl.Field;
import uz.netex.code_gen.model.jdl.Name;
import uz.netex.code_gen.model.jdl.Validation;
import uz.netex.code_gen.model.jdl.type.Type;
import uz.netex.code_gen.util.CaseUtil;
import uz.netex.code_gen.util.CodeUtil;

public class EntityMaker {
    private static final Logger LOGGER = Logger.getLogger(EntityMaker.class);

    private final Entity entity;

    public EntityMaker() {
        entity = new Entity();
    }

    public Entity make(String head, String body) {
        headSolver(head);
        bodySolver(body);
        return entity;
    }

    private void headSolver(String head) {
        String name = head.split(" ", 2)[1].trim();
        entity.setName(name);
    }

    private void bodySolver(String body) {
        for(String item: body.split("\n")){
           if(item.trim().equals("")) continue;
           entity.getFields().add(makeField(item));
        }
    }

    private Field makeField(String content) {
        Field field = new Field();

        content = CodeUtil.remove2Probels(content.replaceAll("\\s", " "));
        String[] items = content.trim().split(" ");
        Validation validation = null;
        for (int i = 0; i < items.length; i++) {
            items[i] = items[i].trim();
            switch (i) {
                case 0:
                    field.setName(items[0]);
                    break;
                case 1:
                    field.setType(new Type(items[1]));
                    break;
                default:
                    try {
                        if (validation == null)
                            validation = new Validation();
                        if (items[i].equals("required")) {
                            validation.setRequired(true);
                        } else if (items[i].equals("unique")) {
                            validation.setUnique(true);
                        } else if (items[i].startsWith("max")) {
                            String number = items[i].split("\\(")[1].split("\\)")[0].trim();
                            validation.setMax(Long.parseLong(number));
                        } else if (items[i].startsWith("min")) {
                            String number = items[i].split("\\(")[1].split("\\)")[0].trim();
                            validation.setMin(Long.parseLong(number));
                        } else if (items[i].startsWith("pattern")) {
                            String pattern = items[i].split("\\(")[1].split("\\)")[0].trim();
                            validation.setPattern(pattern);
                        }
                    } catch (Exception e) {
                        LOGGER.error(e);
                    }
            }
        }
        field.setValidation(validation);
        return field;
    }

}
