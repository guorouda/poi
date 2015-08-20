package com.ron.view;

import sun.swing.StringUIClientPropertyKey;

import com.ron.Command;
import com.ron.dao.DAOFactory;
import com.ron.dao.DeptDAO;
import com.ron.dao.UserDAO;
import com.ron.model.Dept;
import com.ron.model.User;
import com.ron.utils.FileUtils;
import com.ron.utils.StringUtil;

public class DeptAction extends Command {

	public String list() {
		String node = req.getParameter("node");
        DeptDAO deptDAO = DAOFactory.getInstance().getDAOImpl(DeptDAO.class);
        String s = deptDAO.getChildren(node, username);
        
		return s;
	}
	
	public String save(){
		String uporgcode = req.getParameter("uporgcode");
		String orgcode = req.getParameter("orgcode");
		String orgjc = req.getParameter("orgjc");
		uporgcode = StringUtil.getLeaf(uporgcode);
		
		
		boolean flag = false;
		String result = null;
        
        DeptDAO deptDAO = DAOFactory.getInstance().getDAOImpl(DeptDAO.class);
        Dept dept = deptDAO.getDeptByOrgcode(orgcode);
        
        if(dept.getOrgcode() == null){
        	dept.setOrgcode(orgcode);
        	dept.setOrgjc(orgjc);
        	dept.setUporgcode(uporgcode);
        	
        	flag = deptDAO.create(dept);
        }else{
        	flag = deptDAO.update(dept, uporgcode, orgjc);
        }
        
        if(flag){
	        result = "{'success': true, 'message':'" + "成功" + "'}";
        }else{   
	        result = "{'success': false}";
        }
        
		return result;
	}
	
	public String delete(){
		String orgcode = req.getParameter("orgcode");
        DeptDAO deptDAO = DAOFactory.getInstance().getDAOImpl(DeptDAO.class);
        Dept dept = new Dept();
        dept.setOrgcode(StringUtil.getLeaf(orgcode));
        boolean flag = deptDAO.delete(dept);
        String result = "";
		
        if(flag){
	        result = "{'success': true, 'message':'" + "成功" + "'}";
        }else{   
	        result = "{'success': false, 'message':'" + "删除失败，" + "'}";
        }
		return result;
	}

}
