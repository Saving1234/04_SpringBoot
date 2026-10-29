package bear.section01.subsection01.field;

import bear.section01.common.BookDAO;
import bear.section01.common.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookServiceField")
public class Bookservice {


    /*
          스프링이 BookService 빈을 생성한 뒤, ,BookDAO 타입의 빈을 찾아 이 필드에 주입.
          여기서는 BookDAO 의 구현체인 BookDaoIMPL 빈이 주입
     */
    @Autowired
    private BookDAO bookDAO;

    public List<BookDTO> selectAllBooks(){

        return bookDAO.selectBookList();
    }

    public BookDTO searchBookBySequence(int sequance){

        return bookDAO.selectOneBook(sequance);
    }
}
