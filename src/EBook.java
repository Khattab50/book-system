class EBook extends Book {
    private String fileType;

    public EBook(String isbn, String title, int year, double price, String fileType) {
        super(isbn, title, year, price);
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean isForSale() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Type: EBook, FileType: %s", fileType);
    }
}
class ShowcaseBook extends Book {
    public ShowcaseBook(String isbn, String title, int year, double price) {
        super(isbn, title, year, price);
    }

    @Override
    public boolean isForSale() {
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type: Showcase (Not for sale)";
    }
}
