package uz.netex.code_gen.model.jdl.type;

public class Type {
    private String jName;
    private String pName;
    private String sName;
    private String jLibrary;

    public Type(String jName, String pName, String sName, String jLibrary) {
        this.jName = jName;
        this.pName = pName;
        this.sName = sName;
        this.jLibrary = jLibrary;
    }

    public Type(String jName) {
        this.jName = jName;
        for (Type type : TypeList.TYPES)
            if (jName.equals(type.jName)) {
                this.pName = type.pName;
                this.sName = type.sName;
                this.jLibrary = type.jLibrary;
                break;
            }
        if (this.pName == null) {
            this.pName = "TEXT";
            this.sName = "string";
        }
    }

    public Type asFK() {
        this.pName = "bigint";
        this.sName = "integer";
        return this;
    }

    public String getjName() {
        return jName;
    }

    public void setjName(String jName) {
        this.jName = jName;
    }

    public String getpName() {
        return this.pName;
    }

    public Type setpName(String pName) {
        this.pName = pName;
        return this;
    }

    public String getsName() {
        return this.sName;
    }

    public Type setsName(String sName) {
        this.sName = sName;
        return this;
    }

    public String getjLibrary() {
        return this.jLibrary;
    }

    public Type setjLibrary(String jLibrary) {
        this.jLibrary = jLibrary;
        return this;
    }
}
