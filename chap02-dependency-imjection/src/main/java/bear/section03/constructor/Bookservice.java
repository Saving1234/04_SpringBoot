package bear.section03.constructor;

import bear.section01.common.BookDAO;
import bear.section01.common.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
      생성자 주입 권장 이유
      1. 필수 의존성을 빠뜨린 채 객체를 생성할 수 없다.
      2. final 필드를 사용해 의존관계를 불변으로 의지 할 수 없다.
 */


@Service("bookServiceConstructor")
public class Bookservice {

    // BookDao 객체가 새로운 BookDao 객체로 변환되지 않음.
    private final BookDAO bookDAO;

    // 생성자에 붙이니, 생성자 주입.
    // bookservice 빈을 생성할 때 bookdao 타입의 빈을 생성자 인자로 전달한다.
    //@Autowired   // 스프링부트 4.3부터 @Autowired 생략해도 됨.
    public Bookservice(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<BookDTO> selectAllBooks(){

        return bookDAO.selectBookList();
    }

    public BookDTO searchBookBySequence(int sequance){

        return bookDAO.selectOneBook(sequance);
    }
}
