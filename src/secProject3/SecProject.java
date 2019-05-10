package secProject3;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

/**
 * Launches UI frame and populates a queue for reservation. 
 * performs specific task based on UI activity 
 * 
 */
public class SecProject {

    private JFrame frame,frame2;
	static private Queue<Reservation> queue=new LinkedList<Reservation>();
	public static Queue<Reservation> getQueue() {
		return queue;
	}

	static List<String> emails=new ArrayList<String>(Arrays.asList("student1@buffalo.edu","student2@buffalo.edu","student3@buffalo.edu","student4@buffalo.edu","student5@buffalo.edu"));
	static List<String> questions=new ArrayList<String>(Arrays.asList("question1","","question3?","","question5?"));
	static Long currentTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Queue<Reservation> queue = populateReservationQueue();
					SecProject window = new SecProject(queue);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Fills up random number (between 0 and 4) of reservations in a queue
	 */
	public static Queue<Reservation> populateReservationQueue() {
		Random r= new Random();
		int num=r.nextInt(5);
		List<Long> times=new ArrayList<Long>();
		currentTime=System.currentTimeMillis();
		List<Long> TimeOptions=new ArrayList<Long>(Arrays.asList(currentTime-300000,currentTime-660000,currentTime));
		for(int i=0;i<num;i++) {
			times.add(TimeOptions.get(r.nextInt(3)));
		}

		int j=0;
		Queue<Reservation> queue = new LinkedList<Reservation>();
		for(Long time:times) {
			Reservation res=new Reservation(emails.get(j),questions.get(4-j),time);
			queue.add(res);
			j++;
		}
		return queue;
	}

	/**
	 * Creates the application.
	 */
	public SecProject(Queue<Reservation> queue) {
		SecProject.queue = queue;
		currentTime = System.currentTimeMillis();
		initialize();
	}

	/**
	 * Initializes the contents of the ui frame.
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
				Reservation present=markStudentPresent();
				frame.setVisible(false);
				frame2.setVisible(true);
				String queueString=fetchQueueStatus(present);
				resQueue.setText(queueString);
			}
		});
		btnReportStudentPresent.setBounds(5, 200, 208, 50);
		frame.getContentPane().add(btnReportStudentPresent);
		
		JButton btnReportStudentAbsent = new JButton("Report Student Absent");
		btnReportStudentAbsent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reservation banned=markStudentAbsent();
				frame.setVisible(false);
				frame2.setVisible(true);
				String queueString=fetchQueueStatus(banned);
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
			textPane.setText("Student Email : "+queue.peek().getEmailId()+" \nQuestions : "+queue.peek().getSampleQuestion());
		}
		frame.getContentPane().add(textPane);
		
	}
	
	/**
	 * Retrieves first appointment in queue.
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
	 * 
	 * @return {Reservation} returns the reservation removed from queue
	 */
	public static Reservation markStudentPresent() {
		Reservation present=null;
		if(!queue.isEmpty() ) {
			queue.peek().setStatus("Student Marked Present");
			present=queue.poll();
		}
		return present;
	}
	
	/**
	 * Changes the queue based on when the student is marked absent.
	 * 
	 * @return {Reservation} returns the reservation removed from queue
	 */
	public static Reservation markStudentAbsent() {
		Reservation banned=null;
		long lTime=(currentTime-queue.peek().getTime())/60000;
		if(!queue.isEmpty()) {
			if(lTime<10) {
				queue.peek().setStatus("Student Absent");
				queue.add(queue.poll());
			}
			else {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate localDate = LocalDate.now();
				String banned_Date = "Student Banned on "+String.valueOf(dtf.format(localDate));
				queue.peek().setStatus(banned_Date);
				banned=queue.poll();
			}
		}
		return banned;
	}
	
	/**
	 * Converts the queue to string for displaying it
	 * @param res - Appointment to be displayed after the queue
	 * 
	 * @return {String} returns the composed string from queue
	 */
	public static String fetchQueueStatus(Reservation res) {
		int i=1;
		String queueString="";
		long lTime = 0;
		queueString+="Reservation Queue Status:";
		for(Reservation resv: queue) {
			lTime=(currentTime-resv.getTime())/60000;
			queueString+="\n"+i+": Student Email : "+resv.getEmailId()+" \nQuestions : "+resv.getSampleQuestion()+"\nStudent is late by "+String.valueOf(lTime)+" mins\nStatus : "+resv.getStatus()+"\n";
			i++;	
		}
		queueString += "\nSize of Queue : "+String.valueOf(queue.size())+"\n";
		if (res!=null) {
			lTime=(currentTime-res.getTime())/60000;
			queueString+="---------\nPrevious Reservation:\n"+"Student Email : "+res.getEmailId()+" \nQuestions : "+res.getSampleQuestion()+"\nStudent is late by "+String.valueOf(lTime)+" mins\nStatus : "+res.getStatus()+"\n---------\n";
		}
		return queueString;
		
	}
	
}
