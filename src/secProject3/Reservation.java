package secProject3;

public class Reservation implements Comparable<Reservation> {
	String emailId;
	String sampleQuestion;
	long time;
	String status;
	
	
	public Reservation(String emailId, String sampleQuestion, long time) {
		super();
		this.emailId = emailId;
		this.sampleQuestion = sampleQuestion;
		this.time = time;
		this.status="Not Modified";
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getSampleQuestion() {
		return sampleQuestion;
	}
	public void setSampleQuestion(String sampleQuestion) {
		this.sampleQuestion = sampleQuestion;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	 
	@Override
	public int compareTo(Reservation o) {
		return (int)(this.getTime()-o.getTime());
	}
}
