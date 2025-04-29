package br.com.rpdesenvolve.producer.port.out;

import br.com.rpdesenvolve.producer.domain.model.Book;

public interface BookService {

  void send(Book book);
}
