package zjlin.tinyioc;


/**
 * Original author: Yihua.Huang
 */
public class OutputServiceImpl implements OutputService {

    @Override
    public void output(String text){
        System.out.println(text);
    }

}