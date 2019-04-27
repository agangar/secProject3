package secProject3;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class secProject {

	private JFrame frame,frame2;
	static Reservation currRes;
	static Queue<Reservation> queue=new LinkedList<Reservation>();
	static List<String> emails=new ArrayList<String>(Arrays.asList("student1@buffalo.edu","student2@buffalo.edu","student3@buffalo.edu","student4@buffalo.edu","student5@buffalo.edu"));
	static List<String> questions=new ArrayList<String>(Arrays.asList("question1","question2?","question3?","question4?","question5?"));
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		populateResrvationQueue();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					secProject window = new secProject();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void populateResrvationQueue() {
		Random r= new Random();
		int num=r.nextInt(5);
		List<Long> times=new ArrayList<Long>();
		Long currTime=System.currentTimeMillis();
		List<Long> TimeOptions=new ArrayList<Long>(Arrays.asList(currTime-300000,currTime-660000,currTime));
		for(int i=0;i<num;i++) {
			times.add(TimeOptions.get(r.nextInt(3)));
		}
		Collections.sort(times);
		int i=0;
		for(Long time:times) {
			System.out.println((currTime-time)/60000);
			Reservation res=new Reservation(emails.get(i),questions.get(4-i),time);
			queue.add(res);
			i++;
		}
	}

	/**
	 * Create the application.
	 */
	public secProject() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 455, 316);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame2=new JFrame();
		frame2.setBounds(100, 100, 455, 316);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		frame2.setVisible(false);
		
		JButton btnReportStudentPresent = new JButton("Report Student Present");
		btnReportStudentPresent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				markStudentPresent();
				frame.setVisible(false);
				frame2.setVisible(true);
			}
		});
		btnReportStudentPresent.setBounds(0, 231, 208, 25);
		frame.getContentPane().add(btnReportStudentPresent);
		
		JButton btnNewButton = new JButton("Report Student Absent");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				markStudentAbsent();			
			}
		});
		btnNewButton.setBounds(213, 231, 224, 25);
		frame.getContentPane().add(btnNewButton);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 13, 408, 165);
		textPane.setEditable(false);
		if(queue.size()==0) {
			textPane.setText("No appointments in Queue");
		}else {
			currRes=queue.poll();
			long Ltime=(System.currentTimeMillis()-currRes.time)/60000;
			textPane.setText("Student Email : "+currRes.emailId+" \nQuestions : "+currRes.sampleQuestion+"\nStudent is late by "+String.valueOf(Ltime)+" mins");
		}
		frame.getContentPane().add(textPane);
		
	}

	protected void markStudentPresent() {
		currRes.status="Student Marked Present";
	}
	protected void markStudentAbsent() {
		long Ltime=(System.currentTimeMillis()-currRes.time)/60000;
		if(Ltime<10) {
			currRes.status="Moved to End";
			queue.add(queue.poll());
		}else {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			currRes.status="Student Banned on "+String.valueOf(dtf.format(localDate));
		}
	}
}
