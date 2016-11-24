package eventTracer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import java.io.*;

public class Test
{
	JFrame frame;
	JTextPane textPane;
	File file;
	Icon image;

	public Test()
	{
		frame = new JFrame("JTextPane");
		
		textPane = new JTextPane();
		//textPane.setContentType("text/html");
		
		JScrollPane scrollpane = new JScrollPane(textPane);
		//scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		frame.getContentPane().add(scrollpane );
		
		/*file = new File("./classes/test/icon.gif");
		image = new ImageIcon(file.getAbsoluteFile().toString());*/
	}

	public void insert(String str, AttributeSet attrSet)
	{
		Document doc = textPane.getDocument();
		//str = "\n" + str;
		try
		{	
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e)
		{
			System.out.println("BadLocationException: " + e);
		}
	}

	public void setDocs(String str, Color col, boolean bold, int fontSize)
	{
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, col);
		// 颜色
		if (bold == true)
		{
			StyleConstants.setBold(attrSet, true);
		} // 字体类型
		StyleConstants.setFontSize(attrSet, fontSize);
		// 字体大小
		insert(str, attrSet);
	}

	public void gui(String str1,String str2,String str3)
	{
		//textPane.insertIcon(image);
		setDocs(str1, Color.red, true, 12);
		setDocs(str2, Color.BLUE, false, 12);
		setDocs(str3, Color.BLACK, false, 12);
		setDocs("\n", Color.BLUE, false, 12);
		//textPane.setCaretPosition(textPane.getDocument().getLength()); 
		frame.add(new JScrollPane(textPane));
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				//System.exit(0);
			}
		});
		
		frame.setSize(1100, 300);
		frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		Test test = new Test();
		for (int i=0;i<99;i++)
		{
			test.gui("第"+i+"行"," 第二列 "," 第三列 ");
		}
		
	
	}
}
