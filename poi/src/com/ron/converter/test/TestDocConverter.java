package com.ron.converter.test;

import com.ron.controller.converter.docConverter.DocConverter;
import com.ron.controller.converter.pdfConverter.JComPDFConverter;
import com.ron.controller.converter.pdfConverter.JacobPDFConverter;
import com.ron.controller.converter.pdfConverter.OpenOfficePDFConverter;
import com.ron.controller.converter.pdfConverter.PDFConverter;
import com.ron.controller.converter.swfConverter.SWFConverter;
import com.ron.controller.converter.swfConverter.SWFToolsSWFConverter;

public class TestDocConverter {
	public static void main(String[]args){
//		PDFConverter pdfConverter = new OpenOfficePDFConverter();
//		PDFConverter pdfConverter = new JacobPDFConverter();
//		PDFConverter pdfConverter = new JComPDFConverter();
//		SWFConverter swfConverter = new SWFToolsSWFConverter();
//		DocConverter converter = new DocConverter(pdfConverter,swfConverter);
//		String txtFile = "D:\\test\\txtTest.txt";
//		String docFile = "D:\\test\\docTest.docx";
//		String xlsFile = "D:\\test\\xlsTest.xlsx";
//		String pptFile = "D:\\test\\pptTest.pptx";
		PDFConverter pdfConverter = new OpenOfficePDFConverter();
		String inputFile = "\\root\\Documents\\22.ppt";
		pdfConverter.convert2PDF(inputFile);
//		converter.convert(txtFile);
//		converter.convert(docFile);
//		converter.convert(xlsFile);
//		converter.convert(pptFile);
	}
}
