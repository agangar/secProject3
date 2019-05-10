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

/**
 * Launches ui frame and populates a queue for reservation. 
 * performs specific task based on UI activity 
 * 
 */
public class secProject {

	private JFrame frame,frame2;
	static Queue<Reservation> queue=new LinkedList<Reservation>();
	public static Queue<Reservation> getQueue() {
		return queue;
	}
	static List<String> emails=new ArrayList<String>(Arrays.asList("student1@buffalo.edu","student2@buffalo.edu","student3@buffalo.edu","student4@buffalo.edu","student5@buffalo.edu"));
	static List<String> questions=new ArrayList<String>(Arrays.asList("question1","question2?","question3?","question4?","question5?"));
	static Long currentTime;
	static Long Ltime;
	static Reservation banned=null;
	

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
	
	/**
	 * Fills up random number of reservations in a queue
	 */
	public static void populateResrvationQueue() {
		Random r= new Random();
		int num=r.nextInt(5);
		List<Long> times=new ArrayList<Long>();
		currentTime=System.currentTimeMillis();
		List<Long> TimeOptions=new ArrayList<Long>(Arrays.asList(currentTime-300000,currentTime-660000,currentTime));
		for(int i=0;i<num;i++) {
			times.add(TimeOptions.get(r.nextInt(3)));
		}

		int j=0;
		for(Long time:times) {
			System.out.println((currentTime-time)/60000);
			Reservation res=new Reservation(emails.get(j),questions.get(4-j),time);
			queue.add(res);
			j++;
		}
	}

	/**
	 * Create the application.
	 */
	public secProject() {
		initialize();
	}

	/**
	 * Initialize the contents of the ui frame.
	 * Holds onClick Listeners for UI components.
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
				String queueString=fetchQueueStatus();
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
				String queueString=fetchQueueStatus();
				resQueue.setText(queueString);
			}
		});
		btnReportStudentAbsent.setBounds(218, 200, 208, 50);
		frame.getContentPane().add(btnReportStudentAbsent);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 13, 408, 165);
		textPane.setEditable(false);
		Reservation res = fetchFirstAppointment();
		if(res==null) {
			btnReportStudentAbsent.setEnabled(false);
			btnReportStudentPresent.setEnabled(false);
			textPane.setText("No appointments in Queue");
		}else {
			textPane.setText("Student Email : "+queue.peek().emailId+" \nQuestions : "+queue.peek().sampleQuestion);
		}
		frame.getContentPane().add(textPane);
		
	}
	
	/**
	 * Retreives first appointment in queue.
	 * If no appointment in queue returns null.
	 * 
	 * @returns {Reservation} returns first object of type Reservation from queue
	 */
	public Reservation fetchFirstAppointment(){
		Reservation res=null;
		if(!queue.isEmpty()) {
			res=queue.peek();
		}
		return res;
	}
	
	/**
	 * Changes the status of first reservation in queue to present
	 */
	public static void markStudentPresent() {
		if(!queue.isEmpty() )
		queue.peek().status="Student Marked Present";
	}
	
	
	/**
	 * Changes the queue based on when the student is marked absent.
	 */
	public static void markStudentAbsent() {
		Ltime=(currentTime-queue.peek().time)/60000;
		if(!queue.isEmpty()) {
			if(Ltime<10) {
				queue.peek().status="Student Absent";
				queue.add(queue.poll());
			}
			else {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate localDate = LocalDate.now();
				System.out.println("Banned on"+localDate);
				String banned_Date = "Student Banned on "+String.valueOf(dtf.format(localDate));
				queue.peek().status=banned_Date;
				banned=queue.poll();
			}
		}
	}
	
	/**
	 * Converts the queue to string for displaying it
	 * 
	 * @return {String} returns the composed string from queue
	 */
	public static String fetchQueueStatus() {
		int i=1;
		String queueString="";
		
		queueString+="Reservation Queue Status:";
		for(Reservation resv: queue) {
			long Ltime=(currentTime-resv.time)/60000;
			queueString+="\n"+i+": Student Email : "+resv.emailId+" \nQuestions : "+resv.sampleQuestion+"\nStudent is late by "+String.valueOf(Ltime)+" mins\nStatus : "+resv.status+"\n";
			i++;	
		}
		queueString += "\nSize of Queue : "+String.valueOf(queue.size())+"\n";
		if (banned!=null)
			queueString+="---------\n"+"Student Email : "+banned.emailId+" \nQuestions : "+banned.sampleQuestion+"\nStudent is late by "+String.valueOf(Ltime)+" mins\nStatus : "+banned.status+"\n---------\n";
		return queueString;
		
	}
	
}

