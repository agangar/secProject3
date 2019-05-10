package secProject3;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

public class ReservationTesting {
	
	@Test
	/**
	 * Tests markStudentAbsent functionality when student is marked absent after 10 min of reservation time
	 */
	public void testStudentAbsentAfterTenMinutes() {
		long currentTime=System.currentTimeMillis();
		 List<String> emails=new ArrayList<String>(Arrays.asList("student1@buffalo.edu","student2@buffalo.edu","student3@buffalo.edu","student4@buffalo.edu","student5@buffalo.edu"));
		 List<String> questions=new ArrayList<String>(Arrays.asList("question1","question2?","question3?","question4?","question5?"));
		
		Reservation res=new Reservation(emails.get(0),questions.get(0),currentTime-(11* 60000));
		Queue< Reservation> queue = new LinkedList<Reservation>();
		queue.add(res);
		SecProject sec = new SecProject(queue);
		int size = sec.getQueue().size();
		Reservation bannedRes = sec.markStudentAbsent();
		assertNotNull(bannedRes);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		assertEquals("Student Banned on "+String.valueOf(dtf.format(localDate)), bannedRes.getStatus());
		assertEquals(size - 1, sec.getQueue().size());
		
	}
	
	@Test
	/**
	 * Tests markStudentAbsent functionality when student is marked absent before 10 min of reservation time
	 */
	public void testStudentAbsentBeforeTenMinutes() {
		long currentTime=System.currentTimeMillis();
		 List<String> emails=new ArrayList<String>(Arrays.asList("student1@buffalo.edu","student2@buffalo.edu","student3@buffalo.edu","student4@buffalo.edu","student5@buffalo.edu"));
		 List<String> questions=new ArrayList<String>(Arrays.asList("question1","question2?","question3?","question4?","question5?"));
		
		Reservation res=new Reservation(emails.get(0),questions.get(0),currentTime-(5* 60000));
		Queue< Reservation> queue = new LinkedList<Reservation>();
		queue.add(res);
		SecProject sec = new SecProject(queue);
		sec.markStudentAbsent();
		Queue<Reservation> localQueue = sec.getQueue();
		if(!localQueue.isEmpty()) 	{
			while(1!=localQueue.size()) {
				localQueue.poll();
			}
		}
		assertEquals(localQueue.peek().getStatus(), "Student Absent");
		
	}
	
	 @Test
	 /**
	  * Tests whether reservation queue is populated correctly
	  */
	public void testPopulateReservationQueue() {
		Queue<Reservation> queue = SecProject.populateReservationQueue();
		if(!queue.isEmpty()) {
			assertTrue(queue.size() >=0 );
			assertTrue(queue.size() < 5); // Assertion Error in this Line, Something is wrong with the condition
			int sampleQuestionCount = 0;
			for(Reservation item : queue){
				sampleQuestionCount++;
				assertNotEquals(item.getEmailId(), null);
			}
			assertTrue(sampleQuestionCount >= queue.size()/2);
		}
	}
	
	@Test
	/**
	 * Tests whether marking student attendance is done correctly
	 */
	public void testStudentPresent() {
		SecProject sec = new SecProject(SecProject.populateReservationQueue());
		if(!sec.getQueue().isEmpty() )
		{
			Reservation res = sec.markStudentPresent();
			assertEquals(res.getStatus(), "Student Marked Present");
		}
		
	}
	
	@Test
	/**
	 * Tests whether first reservation  is fetched correctly
	 */
	public void testfetchFirstAppointment() {
		SecProject sec = new SecProject(SecProject.populateReservationQueue());
		if(!sec.getQueue().isEmpty()) {
			assertNotNull(sec.fetchFirstAppointment());
		}
	}
	
}
	


