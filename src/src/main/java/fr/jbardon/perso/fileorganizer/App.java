package fr.jbardon.perso.fileorganizer;

import static java.lang.System.getProperty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class App 
{
public static void main(String[] args) throws FileNotFoundException,
      IOException, org.xml.sax.SAXException {
 
	System.out.println(getProperty("user.dir"));
    Path file = Paths.get("../sample/organized/2015/03/31-03-2015-1.txt");
    
    BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

    System.out.println("creationTime: " + attr.creationTime());
    System.out.println("lastAccessTime: " + attr.lastAccessTime());
    System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

    System.out.println("isDirectory: " + attr.isDirectory());
    System.out.println("isOther: " + attr.isOther());
    System.out.println("isRegularFile: " + attr.isRegularFile());
    System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
    System.out.println("size: " + attr.size());
    
  }
}
