package bear.section01.subsection02.sector;

import bear.section01.common.BookDAO;
import bear.section01.common.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookServiceSetter")
public class Bookservice {


    /*
          스프링이 BookService 빈을 생성한 뒤, ,BookDAO 타입의 빈을 찾아 이 필드에 주입.
          여기서는 BookDAO 의 구현체인 BookDaoIMPL 빈이 주입
     */

    private BookDAO bookDAO;
    // final로 설정할 수가 없어 불변성 보장 안됨.

    //Spring이 BookService 빈을 생성한 뒤, BooKDAO 타입의 빈을 인자로 전달해 setter 호출.

    @Autowired
    public void setBookDAO(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    public List<BookDTO> selectAllBooks(){

        return bookDAO.selectBookList();
    }

    public BookDTO searchBookBySequence(int sequance){

        return bookDAO.selectOneBook(sequance);
    }
}
