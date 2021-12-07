package uz.netex.code_gen.model.jdl;

import uz.netex.code_gen.util.CaseUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entity {
    private Name name;
    private String tableName;
    private List<Field> fields = new ArrayList<>();

    public Set<String> getLibraries() {
        Set<String> libraries = new HashSet<>();
        for (Field field : fields) {
            String lib = field.getType().getjLibrary();
            if (lib != null)
                libraries.add(lib);
        }
        return libraries;
    }

    public Name getName() {
        return this.name;
    }

    public String getPascalName() {
        return this.name.getPascalCase();
    }


    public Entity setName(String pascalName){
        this.name = new Name(pascalName, CaseUtil.PASCAL_CASE);
        return this;
    }

    public Entity setName(Name name) {
        this.name = name;
        return this;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public Entity setFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public Entity setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}
