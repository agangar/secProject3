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
	static Long currentTime;
	

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
		currentTime=System.currentTimeMillis();
		List<Long> TimeOptions=new ArrayList<Long>(Arrays.asList(currentTime-300000,currentTime-660000,currentTime));
		for(int i=0;i<num;i++) {
			times.add(TimeOptions.get(r.nextInt(3)));
		}
//		Collections.sort(times);
		int i=0;
		for(Long time:times) {
			System.out.println((currentTime-time)/60000);
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
		frame2.setBounds(100, 100, 455, 600);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		frame2.setVisible(false);
		JTextPane resQueue = new JTextPane();
		resQueue.setBounds(12, 13, 408, 500);
		resQueue.setEditable(false);
		frame2.getContentPane().add(resQueue);
		
		JButton btnReportStudentPresent = new JButton("Report Student Present");
		btnReportStudentPresent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				markStudentPresent();
				frame.setVisible(false);
				frame2.setVisible(true);
				String queueString="Reservation Queue:";
				int i=1;
				for(Reservation resv: queue) {
					long Ltime=(currentTime-resv.time)/60000;
					queueString+="\n"+i+": Student Email : "+resv.emailId+" \nQuestions : "+resv.sampleQuestion+"\nStudent is late by "+String.valueOf(Ltime)+" mins\nStatus : "+resv.status+"\n";
					i++;
				}
				System.out.println(queueString);
				resQueue.setText(queueString);
			}
		});
		btnReportStudentPresent.setBounds(5, 200, 208, 50);
		frame.getContentPane().add(btnReportStudentPresent);
		
		JButton btnReportStudentAbsent = new JButton("Report Student Absent");
		btnReportStudentAbsent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				markStudentAbsent();
				frame.setVisible(false);
				frame2.setVisible(true);
				int i=1;
				String queueString="Reservation Queue:";
				for(Reservation resv: queue) {
					long Ltime=(currentTime-resv.time)/60000;
					queueString+="\n"+i+": Student Email : "+resv.emailId+" \nQuestions : "+resv.sampleQuestion+"\nStudent is late by "+String.valueOf(Ltime)+" mins\nStatus : "+resv.status+"\n";
					i++;
					
				}
				resQueue.setText(queueString);
			}
		});
		btnReportStudentAbsent.setBounds(218, 200, 208, 50);
		frame.getContentPane().add(btnReportStudentAbsent);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 13, 408, 165);
		textPane.setEditable(false);
		if(queue.size()==0) {
			textPane.setText("No appointments in Queue");
			btnReportStudentAbsent.setEnabled(false);
			btnReportStudentPresent.setEnabled(false);
		}else {
			currRes=queue.peek();
			long Ltime=(currentTime-currRes.time)/60000;
			textPane.setText("Student Email : "+currRes.emailId+" \nQuestions : "+currRes.sampleQuestion);/*+"\nStudent is late by "+String.valueOf(Ltime)+" mins");*/
		}
		frame.getContentPane().add(textPane);
		
	}

	protected void markStudentPresent() {
		currRes.status="Student Marked Present";
	}
	protected void markStudentAbsent() {
		long Ltime=(currentTime-currRes.time)/60000;
		if(Ltime<10) {
			queue.peek().status="Moved to End";
			currRes.status="Moved to End";
			queue.add(queue.poll());
		}else {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
			System.out.println("Banned on"+localDate);
			queue.peek().status="Student Banned on "+String.valueOf(dtf.format(localDate));
			currRes.status="Student Banned on "+String.valueOf(dtf.format(localDate));
		}
	}
}
