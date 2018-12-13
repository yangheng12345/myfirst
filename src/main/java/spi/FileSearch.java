package spi;

import java.util.List;

public class FileSearch implements Search {
    @Override
    public List serch(String keyword) {
        System.out.println("spi.FileSearch");
        return null;
    }
}
