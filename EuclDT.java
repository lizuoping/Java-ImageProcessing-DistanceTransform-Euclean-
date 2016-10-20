import java.io.*;
import java.util.Scanner;

public class EuclDT{
	static int [][]zeroFramedAry;
	static int[] neighborAry=new int[8];
	static int numRows,	numCols, minVal, maxVal, newMin=maxVal, newMax=0;
	
	public static void main(String[] args){
		EucleanDistanceTransform(args);
		System.out.println("All work done!");
	}
	
	private static void EucleanDistanceTransform(String[] args) {
		initial(args[0]);
		try{
			BufferedWriter outFile = new BufferedWriter(new FileWriter(args[2]));
			firstPassEucleanDistance();
			outFile.write("The result of Pass-1:");
			outFile.newLine();
			PrettyPrintDistance(outFile);
			outFile.newLine();
			outFile.write("--------------------------------------------------------------------");
			outFile.newLine();
			secondPassEucleanDistance();
			outFile.write("The result of Pass-2:");
			outFile.newLine();
			PrettyPrintDistance(outFile);
			outFile.close();
		}
		catch(Exception e){System.out.println(e);}
		try{
			BufferedWriter outFile = new BufferedWriter(new FileWriter(args[1]));
			for(int i=1; i<numRows+1; i++)
				for(int j=1; j<numCols+1; j++) {
					newMin=(zeroFramedAry[i][j]<newMin? zeroFramedAry[i][j]:newMin);
					newMax=(zeroFramedAry[i][j]>newMax? zeroFramedAry[i][j]:newMax);
				}
			outFile.write(numRows+" "+numCols+" "+newMin+" "+newMax);
			outFile.newLine();
			PrettyPrintDistance(outFile);
			outFile.close();
		}
		catch(Exception e){System.out.println(e);}
	}
	
	private static void initial(String fileName) {
		int row=1, col=1, order=0;
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(fileName));
			while(inFile.hasNext()){
				order++;
				if(order==1) numRows=inFile.nextInt();
				else if(order==2){
					numCols=inFile.nextInt();
					zeroFramedAry = new int[numRows+2][numCols+2];
					for(int i=0; i<numRows+2; i++) {
						zeroFramedAry[i][0] = 0;
						zeroFramedAry[i][numCols+1] = 0;
					}
					for(int j=0; j<numCols+2; j++) {
						zeroFramedAry[0][j] = 0;
						zeroFramedAry[numRows+1][j] = 0;
					}
				}
				else if(order==3) minVal=inFile.nextInt();
				else if(order==4) maxVal=inFile.nextInt();
				else{
					zeroFramedAry[row][col++] = inFile.nextInt();
					if(col>numCols) {
						row++;
						col=1;
					}
				}
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void firstPassEucleanDistance(){
		for(int i=1; i<numRows+1; i++) 
			for(int j=1; j<numCols+1; j++) {
				loadNeighbors(i,j);
				if(zeroFramedAry[i][j]>0)
					zeroFramedAry[i][j]=findMinNeighbor(i,j,1);
			}
	}
	
	private static void secondPassEucleanDistance(){
		for(int i=numRows; i>0; i--)
			for(int j=numCols; j>0; j--) {
				loadNeighbors(i,j);
				if(zeroFramedAry[i][j]>0)
					zeroFramedAry[i][j]=findMinNeighbor(i,j,2);
			}
	}
	
	public static void PrettyPrintDistance(BufferedWriter outFile) throws IOException{
		for (int i = 1; i < numRows+1; i++) {
			for (int j = 1; j < numCols+1; j++) {
				if (zeroFramedAry[i][j] >0)
					outFile.write((int)(zeroFramedAry[i][j]+0.5)+" ");
				else
					outFile.write("  ");
			}
			outFile.newLine();
		}
	}
	
	public static void loadNeighbors(int row, int col){
		neighborAry[0]=(int) (zeroFramedAry[row-1][col-1]+Math.sqrt(2));
		neighborAry[1]=zeroFramedAry[row-1][col]+1;
		neighborAry[2]=(int) (zeroFramedAry[row-1][col+1]+Math.sqrt(2));
		neighborAry[3]=zeroFramedAry[row][col-1]+1;
		neighborAry[4]=zeroFramedAry[row][col+1]+1;
		neighborAry[5]=(int) (zeroFramedAry[row+1][col-1]+Math.sqrt(2));
		neighborAry[6]=zeroFramedAry[row+1][col]+1;
		neighborAry[7]=(int) (zeroFramedAry[row+1][col+1]+Math.sqrt(2));
	}
	
	public static int findMinNeighbor(int row, int col, int pass){
		int temp = 0;
		if(pass==1){
			temp=neighborAry[0];
			for(int i=1; i<4; i++)
				temp=(neighborAry[i]<temp? neighborAry[i]:temp);
		}
		if(pass==2) {
			temp=zeroFramedAry[row][col];
			for(int i=4; i<8; i++)
				temp=(neighborAry[i]<temp? neighborAry[i]:temp);
		}
		return temp;
	}
}





