package spi;

public class SearchTest {
    public static void main(String[] args) {
        Search search = SearchFactory.newSearch();
        search.serch("java spi spi.FileSearch");
    }
}
