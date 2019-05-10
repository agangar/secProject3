package secProject3;

/**
 * 
 * Holds the information about a reservation
 *
 */
public class Reservation{
	private String emailId;
	private String sampleQuestion;
	private long time;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Constructor for initializing a reservation
	 * @param emailId EmailId of the student 
	 * @param sampleQuestion Question of the student
	 * @param time Reservation Time
	 * 
	 */
			
	public Reservation(String emailId, String sampleQuestion, long time) {
		super();
		this.emailId = emailId;
		this.sampleQuestion = sampleQuestion;
		this.time = time;
		this.status="Not Modified";
	}
	
	/**
	 * Retrieves student's email for that appointment
	 * @return {String} Returns the email of the student
	 */
	public String getEmailId() {
		return emailId;
	}
	
	/**
	 * updates student's email for that appointment
	 * @param emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	/**
	 * Retrieves student's question for that appointment
	 * @return {String} Returns the question of the student
	 */
	public String getSampleQuestion() {
		return sampleQuestion;
	}
	
	/**
	 * updates student's sampleQuestion for that appointment
	 * @param sampleQuestion
	 */
	public void setSampleQuestion(String sampleQuestion) {
		this.sampleQuestion = sampleQuestion;
	}
	
	/**
	 * Retrieves student's Time for that appointment
	 * @return {String} Returns the time of the Reservation
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * updates time for that appointment
	 * @param sampleQuestion
	 */
	public void setTime(long time) {
		this.time = time;
	}
	 
}
