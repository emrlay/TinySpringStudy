package zjlin.tinyioc.beans.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Resource是spring内部定位资源的接口。
 * Original author: Yihua.Huang
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
}