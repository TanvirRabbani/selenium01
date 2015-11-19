package com.ebfs.util;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;

import jxl.Workbook;
import jxl.format.*;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.WritableImage;
import jxl.write.biff.RowsExceededException;

/**
 * Logging class that makes use of the Jexcel API to write test logs to
 * Excel files
 * 
 * @author Tanvir Rabbani
 * @version 1.0
 * 2007-10-10 - Added javadoc commenting for methods and removed main method.  This
 *  object must be instantiated now.  Don't use static unless you have to!
 */
public class SummaryReport {
	private static int num = 1;
	private static int step = 1;
	private static final String directory = "C:\\";
	private static final String relResulXLtDir="./SummaryReport/";
	private static final String relSnapShotDir="./Snapshot/";
	private static WritableWorkbook workLog;
	private static WritableSheet workSheet;
	private static WritableFont font;
	private static WritableCellFormat fontColor;
	private static File fileXLS;  //log file
	private static int failCounter;
	
	/**
	 * Default constructor
	 */
	public SummaryReport() {
		this("TestLog");
	}
	
	/**
	 * Constructor that allows the log file name to be specified.  
	 * 
	 * @param name Name of the log file
	 */
	public SummaryReport(String name){
		this(name, directory);
	}
	
	/**
	 * Constructor to specify the name of the log file and its path
	 * 
	 * @param name Name of the log file
	 * @param path Path for the log file
	 */
	public SummaryReport(String name, String path) {
		// Used to created the xls log file
		SimpleDateFormat date = new SimpleDateFormat("EEE, MMM d ''yy hh_mm_ss a");
		String formatedDate = date.format(new Date());
		
		
		fileXLS = new File(path + name +" - " + formatedDate + ".xls");
		
		try {
			fileXLS.createNewFile();
			WritableWorkbook workbook = Workbook.createWorkbook(fileXLS);
			WritableSheet sheet = workbook.createSheet("Log", 0); 
			
			workLog = workbook;
			workSheet = sheet;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		*The following is used to format the excel file the first thing that needs to be created is
		*a writable font here you can change the font and the size, the font is from a supplied list
		*once the font is created you can change the color again this is from a supplied list
		*after the font is given a color you convert that into a cell format which you can then
		*change the aligment.
		 */
		font = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
		try {
			font.setColour(Colour.WHITE);
			fontColor = new WritableCellFormat(font);
			fontColor.setAlignment(Alignment.CENTRE);
			fontColor.setBackground(Colour.GRAY_25);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
		/*
		*This is used to format the row height and the column width
		*the first argument is the row number the second is the row height which is really
		*the height that you want multiplied by 20
		*for the column the first argument is the column number the second is the height which is 1 to 1 
		 */  
		try {
			workSheet.setRowView(0, 300);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		}
		workSheet.setColumnView(0, 7);
		workSheet.setColumnView(1, 60);
		workSheet.setColumnView(2, 45);
		workSheet.setColumnView(3, 10);
		
		//inputs the information into the cell first variable is the column, second is row, third is text
		Label label = new Label(0, 0, "Line #",fontColor);
		try {
			workSheet.addCell(label);
			
			label = new Label(1, 0, "Description",fontColor); 
			workSheet.addCell(label);
			
			label = new Label(2, 0, "Test Data",fontColor); 
			workSheet.addCell(label);
			
			label = new Label(3, 0, "Pass\\Fail",fontColor); 
			workSheet.addCell(label);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	

	
	public static void createResultXL(String name) {
		// Used to created the xls log file
		failCounter = 0;
		SimpleDateFormat date = new SimpleDateFormat("EEE, MMM d ''yy hh_mm_ss a");
		String formatedDate = date.format(new Date());
		
		fileXLS = new File(relResulXLtDir + name +" - " + formatedDate + ".xls");

		
		
		try {
			fileXLS.createNewFile();
			WritableWorkbook workbook = Workbook.createWorkbook(fileXLS);
			WritableSheet sheet = workbook.createSheet("Summary", 0); 

			workLog = workbook;
			workSheet = sheet;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		*The following is used to format the excel file the first thing that needs to be created is
		*a writable font here you can change the font and the size, the font is from a supplied list
		*once the font is created you can change the color again this is from a supplied list
		*after the font is given a color you convert that into a cell format which you can then
		*change the aligment.
		 */
		font = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
		try {
			font.setColour(Colour.WHITE);
			fontColor = new WritableCellFormat(font);
			fontColor.setAlignment(Alignment.CENTRE);
			fontColor.setBackground(Colour.GRAY_25);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
		/*
		*This is used to format the row height and the column width
		*the first argument is the row number the second is the row height which is really
		*the height that you want multiplied by 20
		*for the column the first argument is the column number the second is the height which is 1 to 1 
		 */  
		try {
			workSheet.setRowView(0, 300);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		}
		workSheet.setColumnView(0, 7);
		workSheet.setColumnView(1, 60);
		workSheet.setColumnView(2, 45);
		workSheet.setColumnView(3, 10);
		
		//inputs the information into the cell first variable is the column, second is row, third is text
		
		try {
			Label label = new Label(0, 0, "Line #",fontColor);
			workSheet.addCell(label);
			

			label = new Label(1, 0, "Test Case Name",fontColor); 
			workSheet.addCell(label);
				
			label = new Label(2, 0, "Pass\\Fail",fontColor); 
			workSheet.addCell(label);
			
			
			label = new Label(3, 0, "Detail Result File",fontColor); 
			workSheet.addCell(label);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for writing to the log file, including a step name and a pass or fail for the step
	 * 
	 * @param description Log item description
	 * @param stepName The test step name 
	 * @param Passed Passing or failing for this test step
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public static void write(String testCaseName,Boolean Passed, String resultLink) 
	{
		WritableFont font = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
		WritableCellFormat defaultCell = new WritableCellFormat(font);
		try{
		font.setColour(Colour.BLACK);
		WritableCellFormat fontColor = new WritableCellFormat(font);
			
		//Sets values for a cell for pass or fail values 
		String PF;

		if(Passed)
		{
			PF = "Pass";
			//sets color for pass/fail cell
			font.setColour(Colour.GREEN);			
			fontColor = new WritableCellFormat(font);
		}else
		{ 	failCounter++;	
			PF = "Fail";
			//sets color for pass/fail cell
			font.setColour(Colour.RED);
			fontColor = new WritableCellFormat(font);
			
		}
		
		//writes to the workbook
		Number number = new Number(0, num, step); 
		workSheet.addCell(number);
		
		Label label = new Label(1, num, testCaseName, defaultCell);
		workSheet.addCell(label);
		

		
		//fontColor added to create different colors for pass and fail
		label = new Label(2, num, PF, fontColor);
		workSheet.addCell(label);
		
		label = new Label(3, num, resultLink, defaultCell);
		workSheet.addCell(label);
		
		//keeps track of rows
		num++;
		step++;
		//sworkLog.write();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		//write image to the log file
//		if(PF.equals("Fail"))
//		{
//			takeSnapshot("C:\\resources", "snapshot");
//		}
	
		
	}
	

	
	public void write(String text) throws IOException, RowsExceededException, WriteException
	{
		write(text, "INDIGO", "WHITE", false);
	}
	/**
	 * Method for writing text to the log file
	 * 
	 * @param text The value being written to the log file
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void write(String text, String textFontColor, String bgColor, boolean bold) throws IOException, RowsExceededException, WriteException
	{	
		WritableFont font;
		if(bold)
		{
			font = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);						
		}
		else
		{			
			font = new WritableFont(WritableFont.ARIAL,10);
		}
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		cellFormat.setAlignment(Alignment.LEFT);

		Class<?> c1;
		Field fontColorField;
		Field bgColorField;
		try{
		c1 = Class.forName("jxl.format.Colour");
		
		fontColorField = c1.getDeclaredField(textFontColor);
		bgColorField = c1.getDeclaredField(bgColor);
		
		font.setColour((Colour)fontColorField.get(font));
		cellFormat.setBackground((Colour)bgColorField.get(cellFormat));
		
		}
		catch (Exception e) {
		      e.printStackTrace();
		} 
		//font.setColour(Colour.INDIGO);
		
		//fontColor added to create different colors for pass and fail
		Number number = new Number(0, num, step); 
		workSheet.addCell(number);
		
		Label label = new Label(1, num, text, cellFormat);
		workSheet.addCell(label);
		workSheet.mergeCells(1, num, 2, num);
		
		// font must be reset to change one variable
		// I havent been able to find a work around yet
		font = new WritableFont(WritableFont.ARIAL,10);
		font.setColour(Colour.BLACK);
		cellFormat = new WritableCellFormat(font);
		cellFormat.setAlignment(Alignment.LEFT);
		label = new Label(3, num, "Info", cellFormat);
		workSheet.addCell(label);
		
		//keeps track of rows
		num++;
		step++;
	}
	
	/**
	 * Method that is called when finished writing the log.
	 * @throws IOException
	 * @throws WriteException
	 */
	public static boolean close() throws IOException, WriteException
	{
		boolean returnStatus=false;
		workLog.write();
		workLog.close();
		num = 1;
		step=1;
		
		if(failCounter==0) returnStatus= true;
		return returnStatus;
		
	}
	
	/**
	 * Finalize method for cleaning up after object is finished
	 */
	public void finalize(){
		try {
			workLog.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workLog.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void takeSnapshot(String filePath, String fileName)
	{
		try{
			
		SimpleDateFormat date = new SimpleDateFormat("EEE, MMM d ''yy hh_mm_ss a");
		String formattedDate = date.format(new Date());			

		Robot robot = new Robot();
		File imgFile = new File(filePath + "\\" + fileName +  "_" + formattedDate + ".png"); 		
		
		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(screenShot, "png", imgFile);
		
		WritableImage wi = new WritableImage(1, num, 2, 30, imgFile);
		workSheet.addImage(wi);
		num = num + 30;
		
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void takeSnapshot(String fileName){
		takeSnapshot(relSnapShotDir, fileName);
		
	}
	
	
	

	
}
