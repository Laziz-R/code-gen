package uz.netex.code_gen.model.jdl;

public class Relationship {
    private RelationType type;
    private Entity fromEntity;
    private Entity toEntity;

    public Entity getFromEntity() {
        return fromEntity;
    }

    public Relationship setFromEntity(Entity fromEntity) {
        this.fromEntity = fromEntity;
        return this;
    }

    public Entity getToEntity() {
        return toEntity;
    }

    public Relationship setToEntity(Entity toEntity) {
        this.toEntity = toEntity;
        return this;
    }

    public RelationType getType() {
        return type;
    }

    public Relationship setType(RelationType type) {
        this.type = type;
        return this;
    }
}
