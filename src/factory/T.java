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
	public static UnlimitedBuffer finalBuffer = new UnlimitedBuffer();
	private boolean red;
	private static CyclicBarrier barrier;
	public static int answerSize = 0;

	
	
	public T (boolean oRed, boolean oOrange, int oStage) {
		this.orange = oOrange; 
		this.stage = oStage;
		this.red = oRed;
	}
	
	public void run() {
		if(stage == 1) {
			Message m1 = new Message(orange);
			m1.setId(idAssigner.getId());
			m1.advanceStage(stage);
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
			Message m2 = firstBuffer.exctractFromBuffer(orange);
			m2.advanceStage(stage);
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
			Message m3 = secondBuffer.exctractFromBuffer(orange);
			m3.advanceStage(stage);
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
				Message message = finalBuffer.exctractFromBuffer();
				if (message == null) {
					Thread.yield();
				} else {
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
	    
	    barrier = new CyclicBarrier((3*procesos)+1, ()->System.out.println("El programa termino"));
	    int bInt = rand.nextInt(procesos+1);
	    for(int threadNum = 0; threadNum < procesos; threadNum++) {
	    	for(int stage = 1 ; stage < 4; stage++ ) {
	    		T t = new T(false, threadNum == bInt, stage);
	    		t.start();
	    		
	    	}
	    }
		T r = new T(true,false,0);
		r.start();
		
	    
	}
}

