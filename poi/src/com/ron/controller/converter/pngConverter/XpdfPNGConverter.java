package com.ron.controller.converter.pngConverter;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class XpdfPNGConverter {
	
	public static Logger log = Logger.getLogger(XpdfPNGConverter.class);
	
    // PDF文件名
    private File pdffile;
    
    // 转换器的存放位置，默认在c:xpdftestxpdf下面
    private String CONVERTOR_STORED_PATH = "";
    
    // 转换器的名称，默认为pdftotext
    private String CONVERTOR_NAME = "pdftopng";

    // 构造函数，参数为pdf文件的路径
    public XpdfPNGConverter(String pdffile) throws IOException {
        this.pdffile = new File(pdffile);
    }

    // 将pdf转为文本文档，参数为目标文件的路径
    public Process toPNG(String targetfile) throws IOException {
        return toPNG(targetfile, true);
    }

    // 将pdf转为文本文档，参数1为目标文件的路径，
    // 参数2为true则表示使用PDF文件中的布局
    public Process toPNG(String targetfile, boolean isLayout) throws IOException {
        File destinationFile = new File(targetfile);
        if (!destinationFile.exists()) {
            destinationFile.mkdir();
        }
        
        String[] cmd = getCmd(new File(targetfile + File.separator + "image"), isLayout);
        return Runtime.getRuntime().exec(cmd);
    }

    // 获取PDF转换器的路径
    public String getCONVERTOR_STORED_PATH() {
        return CONVERTOR_STORED_PATH;
    }

    // 设置PDF转换器的路径
    public void setCONVERTOR_STORED_PATH(String path) {
        if (!path.trim().endsWith("\\")) {
            path = path.trim() + "\\";
        }
        this.CONVERTOR_STORED_PATH = path;
    }

    // 解析命令行参数
    private String[] getCmd(File targetfile, boolean isLayout) {

        // 命令字符
        String command = CONVERTOR_STORED_PATH + CONVERTOR_NAME;
        // PDF文件的绝对路径
        String source_absolutePath = pdffile.getAbsolutePath();
        // 输出文本文件的绝对路径
        String target_absolutePath = targetfile.getAbsolutePath();
        
        String rc_absolutePath = "//opt//xpdfbin-linux-3.04//bin64//xpdfrc";
        
        // 保持原来的layout
//        String layout = "-layout";
        // 设置编码方式
        String encoding = "-enc";
        String character = "GBK";
        
        String rc = "-cfg";
        String xpdfrc = "xpdfrc";
        // 设置不打印任何消息和错误
//        String mistake = "-q";
        // 页面之间不加入分页
//        String nopagebrk = "-nopgbrk";

        // 如果isLayout为false，则设置不保持原来的layout
//        if (!isLayout) {
//            layout = "";
//        }
        
//        return new String[] { command, layout, encoding, character, mistake, nopagebrk, source_absolutePath, target_absolutePath };
        return new String[] { command, source_absolutePath, target_absolutePath };
    }
    
    public static void main(String[] args){
    	try {
			XpdfPNGConverter xpdf = new XpdfPNGConverter("e:\\73.pdf");
			Process p = xpdf.toPNG("e:\\73");
			p.waitFor();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
