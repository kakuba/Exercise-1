package pl.spring.demo.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class BookRestService {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/books-by-title", method = RequestMethod.GET)
    public List<BookTo> findBooksByTitle(@RequestParam("titlePrefix") String titlePrefix) {
    	if (titlePrefix.equals(" ")) {
    		return bookService.findAllBooks();
    	}
        return bookService.findBooksByTitle(titlePrefix);
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public BookTo saveBook(@RequestBody BookTo book) {
        return bookService.saveBook(book);
    }
    
    @RequestMapping(value = "/book", method = RequestMethod.DELETE)
    public BookTo deleteBook(@RequestBody BookTo book, Map<String, Object> params) {
    	return bookService.deleteBook2(book);
    }
    
    @RequestMapping(value = "/book", method = RequestMethod.PUT)
    public BookTo putBook(@RequestBody BookTo book) {
    	return bookService.putBook(book);
    }
    
}
