package secProject3;

import static org.junit.Assert.*;

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

}
