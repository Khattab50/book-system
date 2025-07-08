import java.util.ArrayList;
import java.util.List;

public class Bookstore {
    private List<Book> inventory;
    private ShippingService shippingService;
    private MailService mailService;

    public Bookstore(ShippingService shippingService, MailService mailService) {
        this.inventory = new ArrayList<>();
        this.shippingService = shippingService;
        this.mailService = mailService;
    }

    public void addBook(Book book) {
        inventory.add(book);
    }

    public Book removeBook(String isbn) {
        Book bookToRemove = findBookByIsbn(isbn);
        if (bookToRemove != null) {
            inventory.remove(bookToRemove);
        }
        return bookToRemove;
    }

    public List<Book> removeOutdatedBooks(int yearsThreshold) {
        int currentYear = java.time.Year.now().getValue();
        List<Book> outdatedBooks = new ArrayList<>();
        List<Book> toRemove = new ArrayList<>();

        for (Book book : inventory) {
            if (currentYear - book.getYear() > yearsThreshold) {
                outdatedBooks.add(book);
                toRemove.add(book);
            }
        }

        inventory.removeAll(toRemove);
        return outdatedBooks;
    }

    public double buyBook(String isbn, int quantity, String email, String address) {
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " not found");
        }

        if (!book.isForSale()) {
            throw new IllegalArgumentException("This book is not for sale");
        }

        if (book instanceof PaperBook) {
            PaperBook paperBook = (PaperBook) book;
            paperBook.reduceStock(quantity);
            if (shippingService != null) {
                shippingService.ship(isbn, address);
            }
        } else if (book instanceof EBook) {
            EBook eBook = (EBook) book;
            if (mailService != null) {
                mailService.send(isbn, email, eBook.getFileType());
            }
        }

        return book.getPrice() * quantity;
    }

    public void printInventory() {
        System.out.println("\nCurrent Inventory:");
        if (inventory.isEmpty()) {
            System.out.println("No books in inventory");
        } else {
            for (Book book : inventory) {
                System.out.println(book);
            }
        }
    }

    private Book findBookByIsbn(String isbn) {
        for (Book book : inventory) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
}
