class PaperBook extends Book {
    private int stock;

    public PaperBook(String isbn, String title, int year, double price, int stock) {
        super(isbn, title, year, price);
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void reduceStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        stock -= quantity;
    }

    @Override
    public boolean isForSale() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Type: Paper, Stock: %d", stock);
    }
}