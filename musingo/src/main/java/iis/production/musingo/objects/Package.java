package iis.production.musingo.objects;

/**
 * Created by dima on 2/21/14.
 */
public class Package {
    private String name;
    private int star;
    private int id;

    public Package(int id, String name, int star){
        this.id = id;
        this.name = name;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStar() {
        return star;
    }
}
