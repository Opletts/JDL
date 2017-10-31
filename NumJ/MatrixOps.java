package NumJ;

class IncompatibleMatrixException extends Exception {
	public IncompatibleMatrixException(){
		super("The matrices are not compatible!");
	}
}

public class MatrixOps {
	private float[][] matrix1;
	private float[][] matrix2;
	private float[][] result;
	private int rows;
	private int cols;
	private float[] tempRow;
	private Thread[] threadPool;
	

	public float[][] matmul(float[][] mat1, float[][] mat2) throws IncompatibleMatrixException{
		if(mat1[0].length!=mat2.length)
			throw new IncompatibleMatrixException();

		matrix1 = mat1;
		matrix2 = mat2;

		rows = matrix1.length;
		cols = matrix2[0].length;
		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MultiplyThread(i));
			threadPool[i].start();
		}
		
		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			} 
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] add(float[][] mat1, float[][] mat2) throws IncompatibleMatrixException{
		if(mat1.length!=mat2.length || mat1[0].length!=mat2[0].length)
			throw new IncompatibleMatrixException();

		matrix1 = mat1;
		matrix2 = mat2;

		rows = matrix1.length;
		cols = matrix1[0].length;
		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithMatThread(i, 2));
			threadPool[i].start();
		}
		
		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			} 
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] mul(float[][] mat1, float[][] mat2) throws IncompatibleMatrixException{
		if(mat1.length!=mat2.length || mat1[0].length!=mat2[0].length)
			throw new IncompatibleMatrixException();

		matrix1 = mat1;
		matrix2 = mat2;

		rows = matrix1.length;
		cols = matrix1[0].length;
		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithMatThread(i, 0));
			threadPool[i].start();
		}
		
		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			} 
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] div(float[][] mat1, float[][] mat2) throws IncompatibleMatrixException{
		if(mat1.length!=mat2.length || mat1[0].length!=mat2[0].length)
			throw new IncompatibleMatrixException();

		matrix1 = mat1;
		matrix2 = mat2;

		rows = matrix1.length;
		cols = matrix1[0].length;
		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithMatThread(i, 1));
			threadPool[i].start();
		}
		
		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			} 
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] sub(float[][] mat1, float[][] mat2) throws IncompatibleMatrixException{
		if(mat1.length!=mat2.length || mat1[0].length!=mat2[0].length)
			throw new IncompatibleMatrixException();

		matrix1 = mat1;
		matrix2 = mat2;

		rows = matrix1.length;
		cols = matrix1[0].length;
		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithMatThread(i, 3));
			threadPool[i].start();
		}
		
		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			} 
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float dot(float[][] mat1, float[][] mat2) throws IncompatibleMatrixException{
		if(mat1.length!=mat2.length || mat1[0].length!=mat2[0].length)
			throw new IncompatibleMatrixException();

		matrix1 = mat1;
		matrix2 = mat2;

		rows = matrix1.length;
		cols = matrix1[0].length;

		float dot = 0;
		tempRow = new float[rows];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new ScalarThread(i, 1));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			}
		}

		for(int i=0;i<rows;i++)
			dot += tempRow[i];

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Dot product: " + dot);
		System.out.println("Computation took " + time + " milliseconds.");

		return dot;
	}

	public float sum(float[][] mat) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		float s = 0;
		tempRow = new float[rows];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new ScalarThread(i, 0));
			threadPool[i].start();
		}
		
		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			}
		}

		for(int i=0;i<rows;i++)
			s += tempRow[i];

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Sum: " + s);
		System.out.println("Computation took " + time + " milliseconds.");

		return s;
	}

	public float max(float[][] mat) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		float m = -Float.MAX_VALUE;
		tempRow = new float[rows];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new ScalarThread(i, 2));
			threadPool[i].start();
		}
		
		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			}
		}

		for(int i=0;i<rows;i++){
			if(tempRow[i]>m)
				m = tempRow[i];
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Max: " + m);
		System.out.println("Computation took " + time + " milliseconds.");

		return m;
	}

	public int[] argmax(float[][] mat) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		float m = -Float.MAX_VALUE;
		int[] pos = new int[2];
		tempRow = new float[rows];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new ScalarThread(i, 3));
			threadPool[i].start();
		}
		
		for(int i=0; i<rows; i++){	
			try{
				threadPool[i].join();
			}catch (InterruptedException e){
				System.out.println(e);
			}
		}

		for(int i=0;i<rows;i++){
			if(matrix1[i][(int)tempRow[i]]>m){
				m = matrix1[i][(int)tempRow[i]];
				pos[0] = i;
				pos[1] = (int)tempRow[i];
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("(" + pos[0] + ", " + pos[1] + ")");
		System.out.println("Computation took " + time + " milliseconds.");

		return pos;
	}

	public float[][] exp(float[][] mat) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithScalarThread(i, 0, 0));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){
			try{
				threadPool[i].join();
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] add(float[][] mat, float sclr) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithScalarThread(i, 3, sclr));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){
			try{
				threadPool[i].join();
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] mul(float[][] mat, float sclr) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithScalarThread(i, 1, sclr));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){
			try{
				threadPool[i].join();
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] div(float[][] mat, float sclr) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithScalarThread(i, 2, sclr));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){
			try{
				threadPool[i].join();
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] sub(float[][] mat, float sclr) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;
		
		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithScalarThread(i, 4, sclr));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){
			try{
				threadPool[i].join();
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] transpose(float[][] mat) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		result = new float[cols][rows];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithScalarThread(i, 5, 0));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){
			try{
				threadPool[i].join();
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] neg(float[][] mat) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithScalarThread(i, 6, 0));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){
			try{
				threadPool[i].join();
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] reciprocal(float[][] mat) {
		matrix1 = mat;

		rows = matrix1.length;
		cols = matrix1[0].length;

		result = new float[rows][cols];

		threadPool = new Thread[rows];
		long start = System.nanoTime();

		for(int i=0; i<rows; i++){
			threadPool[i] = new Thread(new MatWithScalarThread(i, 7, 0));
			threadPool[i].start();
		}

		for(int i=0; i<rows; i++){
			try{
				threadPool[i].join();
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		long end = System.nanoTime();
		double time = (end-start)/1000000.0;

		System.out.println("Computation took " + time + " milliseconds.");

		return result;
	}

	public float[][] identity(int size){
		float[][] id = new float[size][size];
		for(int i=0; i<size; i++) {
			id[i][i] = 1.0f;
		}
		return id;
	}

	public float[][] ones(int x, int y){
		float[][] id = new float[x][y];
		for(int i=0; i<x; i++) {
			for(int j=0; j<y; j++)
				id[i][j] = 1.0f;
		}
		return id;
	}

	public float[][] ones(int size){
		float[][] id = new float[size][size];
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++)
				id[i][j] = 1.0f;
		}
		return id;
	}

	private class MultiplyThread implements Runnable {
		int index;

		MultiplyThread(int index){
			this.index = index;
		}

		public void run(){
			for(int i=0; i<matrix2[0].length; i++){
				for(int j=0; j<matrix1[0].length; j++){
					result[index][i] += matrix1[index][j] * matrix2[j][i];
				}
			}
		}
	}

	private class ScalarThread implements Runnable {
		int index;
		float temp;
		int type;

		ScalarThread(int index, int type){
			this.index = index;
			if(type==0 || type==1)
				temp = 0;
			else
				temp = -Float.MAX_VALUE;
			this.type = type;
		}

		public void run(){
			int pos = 0;
			for(int i=0; i<cols; i++){
				if(type==0)
					temp += matrix1[index][i];
				else if(type==1)
					temp += matrix1[index][i] * matrix2[index][i];
				else{
					if(matrix1[index][i]>temp){
						temp = matrix1[index][i];
						pos = i;
					}
				}
			}
			if(type==3)
				tempRow[index] = (float) pos;
			else
				tempRow[index] = temp;
		}
	}

	private class MatWithScalarThread implements Runnable {
		int index;
		int type;
		float scalar;

		MatWithScalarThread(int index, int type, float scalar){
			this.index = index;
			this.type = type;
			this.scalar = scalar;
		}

		public void run(){
			for(int i=0; i<cols; i++){
				if(type==0)
					result[index][i] = (float) Math.exp(matrix1[index][i]);
				else if(type==1)
					result[index][i] = matrix1[index][i] * scalar;
				else if(type==2)
					result[index][i] = matrix1[index][i] / scalar;
				else if(type==3)
					result[index][i] = matrix1[index][i] + scalar;
				else if(type==4)
					result[index][i] = matrix1[index][i] - scalar;
				else if(type==5)
					result[i][index] = matrix1[index][i];
				else if(type==6)
					result[index][i] = -matrix1[index][i];
				else
					result[index][i] = 1/matrix1[index][i];
			}
		}
	}

	private class MatWithMatThread implements Runnable {
		int index;
		int type;

		MatWithMatThread(int index, int type){
			this.index = index;
			this.type = type;
		}

		public void run(){
			for(int i=0; i<cols; i++){
				if(type==0)
					result[index][i] = matrix1[index][i] * matrix2[index][i];
				else if(type==1)
					result[index][i] = matrix1[index][i] / matrix2[index][i];
				else if(type==2)
					result[index][i] = matrix1[index][i] + matrix2[index][i];
				else if(type==3)
					result[index][i] = matrix1[index][i] - matrix2[index][i];
			}
		}
	}
}