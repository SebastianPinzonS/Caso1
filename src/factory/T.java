package factory;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class T extends Thread {
	
	static Random rand = new Random();
	private static IdAssigner idAssigner = new IdAssigner();
	private boolean orange;
	private int stage;
	private static LimitedBuffer firstBuffer;
	private static LimitedBuffer secondBuffer;
	public static UnlimitedBuffer finalBuffer;
	private boolean red;
	private static CyclicBarrier barrier;
	public static int answerSize = 0; 
	private static final int upperBoundRandom = 500;
	private static final int lowerBoundRandom = 50;

	
	public T (boolean oRed, boolean oOrange, int oStage) {
		this.orange = oOrange; 
		this.stage = oStage;
		this.red = oRed;
	}
	
	public void run() {
		if(stage == 1) {
			Message m1 = new Message(orange);
			m1.setId(idAssigner.getId());
		    int delay = (int)Math.floor(Math.random() * (upperBoundRandom - lowerBoundRandom + 1) + lowerBoundRandom);
			m1.advanceStage(stage, delay);	
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			firstBuffer.addToBuffer(m1, orange);
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(stage == 2) {
			Message m2 = firstBuffer.extractFromBuffer(orange);
			int delay = (int)Math.floor(Math.random() * (upperBoundRandom - lowerBoundRandom + 1) + lowerBoundRandom);
			m2.advanceStage(stage, delay);	
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			secondBuffer.addToBuffer(m2, orange);
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(stage == 3) {
			Message m3 = secondBuffer.extractFromBuffer(orange);
			int delay = (int)Math.floor(Math.random() * (upperBoundRandom - lowerBoundRandom + 1) + lowerBoundRandom);
			m3.advanceStage(stage, delay);	
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			finalBuffer.addToBuffer(m3);
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(red) {
			while(answerSize > 0) {
				Message message = finalBuffer.extractFromBuffer();
				if (message != null) {
					System.out.println("Salio el mensaje " + message.getId());
					System.out.println("Su estado es: " + message.getStage());
					answerSize--;
				}
			}
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]){
		
		@SuppressWarnings("resource")
		Scanner myObj = new Scanner(System.in);
	    int procesos;
	    int tamano;
	    

	    System.out.println("Cuantos procesos desea: "); 
	    procesos = Integer.parseInt(myObj.nextLine());
	    answerSize = procesos;
	    
	    System.out.println("Que tan grande desea que sean los buffer "); 
	    tamano = Integer.parseInt(myObj.nextLine());
	    
	    firstBuffer = new LimitedBuffer(tamano);
	    
	    secondBuffer = new LimitedBuffer(tamano);
	    
	    finalBuffer = new UnlimitedBuffer();
	    
	    barrier = new CyclicBarrier((3*procesos)+1, ()->System.out.println("El programa termino"));
	    int bInt = rand.nextInt(procesos);
	    
	    for(int threadNum = 0; threadNum < procesos; threadNum++) {
	    	for(int stage = 1 ; stage < 4; stage++ ) {
	    		T t = new T(false, threadNum==bInt, stage);
	    		t.start();
	    		
	    	}
	    }
		T r = new T(true,false,0);
		r.start();
		
	    
	}
}

