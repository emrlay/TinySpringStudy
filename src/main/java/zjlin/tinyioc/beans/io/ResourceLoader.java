package zjlin.tinyioc.beans.io;

import java.net.URL;

/**
 * Original author: Yihua.Huang
 */
public class ResourceLoader {

    public Resource getResource(String location){
        URL resource = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(resource);
    }
}