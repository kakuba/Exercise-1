package pl.spring.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.repository.BookRepository;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookTo> findAllBooks() {
        return BookMapper.map2To(bookRepository.findAll());
    }

    @Override
    public List<BookTo> findBooksByTitle(String title) {
        return BookMapper.map2To(bookRepository.findBookByTitle(title));
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
        return BookMapper.map2To(bookRepository.findBookByAuthor(author));
    }

    @Override
    @Transactional(readOnly = false)
    public BookTo saveBook(BookTo book) {
        BookEntity entity = BookMapper.map(book);
        entity = bookRepository.save(entity);
        return BookMapper.map(entity);
    }
    @Transactional(readOnly = false)
    @Override
	public BookTo deleteBook(Long id) {
		BookTo bookTo = BookMapper.map(bookRepository.findOne(id));
		bookRepository.delete(id);
		return bookTo;
	}
    @Transactional(readOnly = false)
	@Override
	public BookTo putBook(BookTo book) {
		BookEntity entity = BookMapper.map(book);
        entity = bookRepository.save(entity);
        return BookMapper.map(entity);
	}
    @Transactional(readOnly = false)
	@Override
	public BookTo deleteBook2(BookTo book) {
    	BookTo bookTo = BookMapper.map(bookRepository.findOne(book.getId()));
		bookRepository.delete(BookMapper.map(book));
		return bookTo;
	}

}
