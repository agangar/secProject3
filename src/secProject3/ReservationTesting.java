package secProject3;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

public class ReservationTesting {

/*	@Test
	public void testPopulateResrvationQueue() {
		secProject sec = new secProject();
//		long currentTime=System.currentTimeMillis();
		sec.populateResrvationQueue();
		Queue<Reservation> queue = sec.getQueue();
		if(!queue.isEmpty()) {
			assertTrue(queue.size() >=0 );
			assertTrue(queue.size() < 5); // Assertion Error in this Line, Something is wrong with the condition
			int sampleQuestionCount = 0;
			for(Reservation item : queue){
				sampleQuestionCount++;
				assertNotEquals(item.emailId, null);
			}
			assertTrue(sampleQuestionCount >= queue.size()/2);
		}
	}
	
	@Test
	public void testStudentPresent() {
		secProject sec = new secProject();
	//	sec.populateResrvationQueue();
	//	Queue<Reservation> queue = sec.getQueue();
		sec.markStudentPresent();
		if(sec.queue.peek() != null )
		assertEquals(sec.queue.peek().status, "Student Marked Present");
	}
	
	@Test
	public void testStudentAbsent() {
		secProject sec = new secProject();
		sec.populateResrvationQueue();
		sec.markStudentAbsent();
		if(sec.queue != null) {
			
			Queue<Reservation> localQueue = sec.getQueue();
			if(sec.Ltime < 10 ) {
				if(!localQueue.isEmpty()) 	{
					while(1!=localQueue.size()) {
						localQueue.poll();
					}
				}
				assertEquals(localQueue.peek().status, "Moved to End");
			}
			else {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate localDate = LocalDate.now();
				String banned = "Student Banned on "+String.valueOf(dtf.format(localDate));
				assertEquals(localQueue.peek().status, banned);
			}
		}
	}
	//Report Student Absent
	
	@Test
	public void testfetchFirstAppointment() {
		secProject sec = new secProject();
	//	sec.populateResrvationQueue();
		sec.fetchFirstAppointment();
		if(!sec.queue.isEmpty()) {
			assertNotNull(sec.queue.peek());
		}
	}
	*/
	
	@Test
	public void testStudentAbsentAfterTenMinutes() {
		secProject sec = new secProject();
		sec.populateQueue(11);
		int size = sec.getQueue().size();
		sec.markStudentAbsent();
		assertEquals(size - 1, sec.getQueue().size());
		
	}
	
	@Test
	public void testStudentAbsentBeforeTenMinutes() {
		secProject sec = new secProject();
		sec.populateQueue(5);
		sec.markStudentAbsent();
		Queue<Reservation> localQueue = sec.getQueue();
		if(!localQueue.isEmpty()) 	{
			while(1!=localQueue.size()) {
				localQueue.poll();
			}
		}
		assertEquals(localQueue.peek().status, "Student Marked Absent");
		
	}
	
}
	


