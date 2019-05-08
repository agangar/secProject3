package secProject3;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

public class ReservationTesting {

	@Test
	public void testPopulateResrvationQueue() {
		//fail("Not yet implemented");
		long currentTime=System.currentTimeMillis();
		secProject.populateResrvationQueue();
		Queue<Reservation> queue = secProject.getQueue();
		assertTrue(queue.size() >=0 && queue.size() < 5);
		int sampleQuestionCount = 0;
		for(Reservation item : queue){
			sampleQuestionCount++;
			assertNotEquals(item.emailId, null);
		}
		//TODO To add check for time   
		assertTrue(sampleQuestionCount >= queue.size()/2);
		
	}
	
	@Test
	public void testStudentPresent() {
		
		secProject sec = new secProject();
		sec.populateResrvationQueue();
		Queue<Reservation> queue = sec.getQueue();
		sec.markStudentPresent();
		if(sec.currRes != null )
		assertEquals(sec.currRes.status, "Student Marked Present");
		
		
	}
	
	@Test
	public void testStudentAbsent() {
		secProject sec = new secProject();
		sec.populateResrvationQueue();
		Queue<Reservation> queue = sec.getQueue();
		if(sec.currRes != null) {
			sec.markStudentAbsent();
			
			if(sec.Ltime < 10 ) {
				//assertEquals(sec.queue.peek().status, "Moved to End");
				assertEquals(sec.currRes.status, "Moved to End");
			}
			else {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate localDate = LocalDate.now();
				String banned = "Student Banned on "+String.valueOf(dtf.format(localDate));
				assertEquals(sec.queue.peek().status, banned);
				assertEquals(sec.currRes.status, banned);
			}
		}
		
	}
	//Report Student Absent

}
