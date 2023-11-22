package project;

import java.util.ArrayList;
import java.util.List;

public class Table extends Todo{
    List<String> name;

    public Table(List<String> name) {
        this.name = name;
    }

    public Table(){
        name = new ArrayList<>();
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }
}
