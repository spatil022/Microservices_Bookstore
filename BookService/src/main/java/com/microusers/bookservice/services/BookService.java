package com.microusers.bookservice.services;

import com.microusers.bookservice.dto.BookDto;
import com.microusers.bookservice.exception.BookStoreException;
import com.microusers.bookservice.model.BookDetailsModel;
import com.microusers.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public BookDetailsModel addBook(BookDto book) {
        Optional<BookDetailsModel> searchBookByName = bookRepository.findByBookName(book.getBookName());
        if (searchBookByName.isPresent()) {
            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_AlREADY_PRESENT);

        }
        BookDetailsModel bookDetailsModel = new BookDetailsModel(book.getImage(),
                book.getBookName(),
                book.getAuthorName(),
                book.getDescription(),
                book.getBookPrice(),
                book.getQuantity(),
                book.getRating(),
                book.getPublishingYear()
        );

        BookDetailsModel saveBookToDataBsae = bookRepository.save(bookDetailsModel);
        return  saveBookToDataBsae;
    }


    @Override
    public List<BookDetailsModel> showAllBooks() {

        return bookRepository.findAll().
                stream().
                map(bookDetailsModel -> new BookDetailsModel(bookDetailsModel)).
                collect(Collectors.toList());
    }

    @Override
    public List<BookDetailsModel> showBookHigherToLower() {

        return bookRepository.findAll().stream()
                .sorted(Comparator.comparing(bookDetailsModel -> bookDetailsModel.bookPrice))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDetailsModel> showBookLowerToHigher() {
        List<BookDetailsModel> bookDetail =  bookRepository.findAll().stream()
                .sorted(Comparator.comparing(bookDetailsModel -> bookDetailsModel.bookPrice))
                .collect(Collectors.toList());

        Collections.reverse(bookDetail);
        return bookDetail;
    }

    @Override
    public List<BookDetailsModel> showBookNewLaunch() {
        List<BookDetailsModel> bookDetailsModelList=bookRepository.findAll().stream()
                .sorted(Comparator.comparing(bookDetails -> bookDetails.getCreatedAt()))
                .collect(Collectors.toList());
        Collections.reverse(bookDetailsModelList);
        return bookDetailsModelList;

    }

    @Override
    public int getCountOfBooks() {
        List<BookDetailsModel> totalBooks = bookRepository.findAll();
        return totalBooks.size();
    }

}