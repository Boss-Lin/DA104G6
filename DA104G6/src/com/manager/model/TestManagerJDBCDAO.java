package com.manager.model;

import java.io.*;

public class TestManagerJDBCDAO {

	public static void main(String[] args) throws IOException {

//		ManagerJDBCDAO dao = new ManagerJDBCDAO();
		ManagerJNDIDAO dao = new ManagerJNDIDAO();
// 新增
//		ManagerVO ManagerVO1 = new ManagerVO();
//		ManagerVO1.setMg_name("4魚");
//		ManagerVO1.setMg_title("MANAGER");
//		ManagerVO1.setMg_spec("nothing");
//		ManagerVO1.setMg_account("DA104");
//		ManagerVO1.setMg_password("123456");
//		System.out.println("成功新增");
//		byte[] pic = getPictureByteArray("items/FC_Bayern.png");// getPictureByteArray方法在下面
//		ManagerVO1.setMg_profile_pic(pic);
//
//// 修改
//		ManagerVO ManagerVO2 = new ManagerVO();
//		ManagerVO2.setMg_name("David");
//		ManagerVO2.setMg_title("MANAGER");
//		ManagerVO2.setMg_spec("sleep");
//		ManagerVO2.setMg_account("DD104");
//		ManagerVO2.setMg_password("654321");
//		byte[] pic2 = getPictureByteArray("items/FC_Real_Madrid.png");// getPictureByteArray方法在下面
//		ManagerVO2.setMg_profile_pic(pic2);
//		ManagerVO2.setMg_no("MG021");

// 刪除
//		 dao.delete("MG022");

// 查詢
		ManagerVO ManagerVO3 = dao.findByPrimaryKey("MG001");
		System.out.print(ManagerVO3.getMg_no() + ",");
		System.out.print(ManagerVO3.getMg_name() + ",");
		System.out.print(ManagerVO3.getMg_title() + ",");
		System.out.print(ManagerVO3.getMg_spec() + ",");
		System.out.print(ManagerVO3.getMg_account() + ",");
		System.out.print(ManagerVO3.getMg_password() + ",");
//		System.out.println(ManagerVO3.getMg_profile_pic());
//		readPicture(ManagerVO3.getMg_profile_pic());
		System.out.println("---------------------");

//查全部
//		List<ManagerVO> list = dao.getAll();
//		for (ManagerVO aManager : list) {
//			System.out.print(aManager.getMg_no() + ",");
//			System.out.print(aManager.getMg_name() + ",");
//			System.out.print(aManager.getMg_title() + ",");
//			System.out.print(aManager.getMg_spec() + ",");
//			System.out.print(aManager.getMg_account() + ",");
//			System.out.print(aManager.getMg_password() + ",");
//			System.out.print(aManager.getMg_profile_pic());
//			System.out.println();
//		}

		// 更改密碼

//		ManagerVO managerVO3 = new ManagerVO();
//
//		managerVO3.setMg_password("234567");
//		managerVO3.setMg_no("MG003");
//
//		dao.pswUpdate(managerVO3);

	}

	// write pic(byte)
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// ByteArrayOutputStream輸出到一個BYTE陣列裡面
		byte[] buffer = new byte[8192];// 分段讀取，自訂緩衝
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();// 將水管中的資料回傳
	}

	// read pic(byte)
	public static void readPicture(byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream("Output/2.png");
		fos.write(bytes);
		fos.flush();
		fos.close();
	}

}
