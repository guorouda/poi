package com.ron.controller;

import org.apache.log4j.Logger;

import com.ron.view.PickFileAction;

public class CheckTransferedFiles {
	public static Logger log = Logger.getLogger(CheckTransferedFiles.class);
	
	protected void AutoCheck(){
//		log.info("check starting...");
		PickFileAction.fileList();
	} 

}
