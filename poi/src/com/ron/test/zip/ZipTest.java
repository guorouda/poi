package com.ron.test.zip;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.selectors.FilenameSelector;
import org.apache.tools.ant.types.selectors.OrSelector;


public class ZipTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
         Project pj = new Project();
         Zip zip = new Zip();
         zip.setProject(pj);
         zip.setDestFile(new File("F:/1.zip"));//打包完的目标文件
         
         FileSet fileSet = new FileSet();
         fileSet.setProject(pj);
         fileSet.setDir(new File("F:/logs"));//需要打包的路径
//         fileSet.setIncludes("test.log");//文件过滤  只 包含所有.doc文件
         
         FilenameSelector a = new FilenameSelector();  
         a.setName("test.log");  
           
         FilenameSelector b = new FilenameSelector();  
         b.setName("2222222222-ddd/**/*");  
           
         OrSelector or = new OrSelector();   
         or.addFilename(a);  
         or.addFilename(b);  
           
         fileSet.addOr(or);  
         zip.addFileset(fileSet);
         
         zip.execute();
	}

}


