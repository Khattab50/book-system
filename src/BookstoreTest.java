import java.util.List;

public class BookstoreTest {
    private final Bookstore bookstore;
    private final ShippingService shippingService;
    private final MailService mailService;

    public BookstoreTest() {
        this.shippingService = (isbn, address) ->
                System.out.printf("[SHIPPING] Book %s to %s%n", isbn, address);

        this.mailService = (isbn, email, fileType) ->
                System.out.printf("[EMAIL] %s eBook (%s) to %s%n", fileType, isbn, email);

        this.bookstore = new Bookstore(shippingService, mailService);
    }

    public void testAddBooks() {
        System.out.println("\n=== Testing Add Books ===");

        // Add different types of books
        bookstore.addBook(new PaperBook("1001", "Java Basics", 2022, 29.99, 10));
        bookstore.addBook(new EBook("1002", "Python Advanced", 2023, 24.99, "PDF"));
        bookstore.addBook(new ShowcaseBook("1003", "Quantum Computing", 2024, 0.0));

        System.out.println("Books added successfully!");
        bookstore.printInventory();
    }

    public void testRemoveBooks() {
        System.out.println("\n=== Testing Remove Books ===");

        bookstore.addBook(new PaperBook("2001", "Old Technology", 2000, 9.99, 2));
        bookstore.addBook(new PaperBook("2002", "Recent Tech", 2023, 39.99, 5));

        Book removed = bookstore.removeBook("2001");
        System.out.println("Removed book: " + removed);

        List<Book> outdated = bookstore.removeOutdatedBooks(10);
        System.out.println("Removed outdated books:");
        outdated.forEach(System.out::println);

        System.out.println("Remaining inventory:");
        bookstore.printInventory();
    }

    public void testBuyBooks() {
        System.out.println("\n=== Testing Buy Books ===");

        bookstore.addBook(new PaperBook("3001", "Popular Novel", 2023, 14.99, 3));
        bookstore.addBook(new EBook("3002", "Programming Guide", 2024, 19.99, "EPUB"));

        try {
            double total1 = bookstore.buyBook("3001", 2, "", "123 Book Lane");
            System.out.printf("Bought 2 paper books. Total: $%.2f%n", total1);

            double total2 = bookstore.buyBook("3002", 1, "buyer@example.com", "");
            System.out.printf("Bought 1 eBook. Total: $%.2f%n", total2);

            try {
                bookstore.buyBook("3001", 5, "", "456 Reader St");
            } catch (Exception e) {
                System.out.println("Expected error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        System.out.println("Inventory after purchases:");
        bookstore.printInventory();
    }

    public void runAllTests() {
        testAddBooks();
        testRemoveBooks();
        testBuyBooks();
    }
}
