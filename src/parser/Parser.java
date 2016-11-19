package parser;
import javax.xml.bind.JAXBException;
import java.io.File;
/**
 * Created by Борис on 19.11.2016.
 */
public interface Parser {
    Object getObject(File file, Class c) throws JAXBException;
    void saveObject(File file, Object o) throws JAXBException;
}
