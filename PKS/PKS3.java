import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

//3 лаба, дерево
public class Main {
	static int INF = Integer.MAX_VALUE/2;
	static int N=1, n=7, nBuf; 							// количество кластеров, вершин
	static int[][] matrix1, dist, buffer; 				// матрица смежности, достижимости
	
	public static void main(String[] args) {
		int [][]basic = {{0,1,0,0,0,0,1},{1,0,0,0,0,0,1},{0,0,0,0,0,0,1},{0,0,0,0,1,0,1},{0,0,0,1,0,0,1}, {0,0,0,0,0,0,1}, {1,1,1,1,1,1,0}};
		int i, j, b=0, c=10;
		double delta=7;
		DecimalFormat df = new DecimalFormat("0.00",DecimalFormatSymbols.getInstance(Locale.US));
		System.out.println("n | S | D |  SD   |  T  | C");
		
		for(int N=1; N<=10; N++){
			i=0; 
			j=0;
			matrix1 = new int[n][n];
			
			if(buffer!=null) 
				for(int s=0; s<nBuf; s++)
					System.arraycopy(buffer[s], 0, matrix1[s], 0, nBuf);
			
			//матрица смежности
			while(i<n-6 && j<n-6){
				for(int k=0; k<7; k++){
					for(int m=0; m<7; m++){
						matrix1[i+k][j+m]=basic[k][m];
					}
				}
				i+=7;
				j+=7;
			}
			
			int d=6, row=1;
			for(int q=11; q<matrix1.length-6; q+=7){
				if(q>d){
					d+=7*Math.pow(2, row);
					row++;
				}
				if(q!=d-2){
					//red1
					matrix1[q+1][q+5]=1;
					matrix1[q+5][q+1]=1;
					//red2
					matrix1[q][q+7]=1;
					matrix1[q+7][q]=1;
				}
			}
			
			//blue1
			c=10;
			for(int a=0; a<matrix1.length-delta && a+c<n; a+=7, c+=7){
				matrix1[a][a+c]=1;
				matrix1[a+c][a]=1;
			}
			
			//blue2
			b=15;
			for(int a=4; a<matrix1.length-delta; a+=7, b+=14){
				matrix1[a][b]=1;
				matrix1[b][a]=1;
			}
			
			//print(matrix1);
			//матрица достижимости
			floydWarshall();
			
			//S, D, T, C
			int S=findS(matrix1), D=max(dist);
			//<D>
			double SD=0;
			for (int i1=0; i1<n; i1++)
				for (int j1=0; j1<n; j1++)
					SD+=dist[i1][j1];
			SD/=n*(n-1);
			int C=D*n*S;
			double T=2*SD/S;
				
			System.out.println("____________________________________\n"+n+" | "+S+" | "+D+" | "+df.format(SD)+" | "+df.format(T)+" | "+C);
			
			//подготовка к следующему шагу
			nBuf=n;
			delta=7*Math.pow(2, N);
			n += delta;
			buffer=matrix1;
			
		}
	}
	
	/* Алгоритм Флойда-Уоршелла за O(V^3) */
	static void floydWarshall() {
		dist = new int [n][n]; 						// dist[i][j] = минимальное_расстояние(i, j)
		for (int i=0; i<n; i++) 
			System.arraycopy(matrix1[i], 0, dist[i], 0, n);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(dist[i][j]==0 && i!=j)
					dist[i][j]=INF;
			}
		}
		//print (dist);
		for(int q=0; q<2; q++) 
		for (int k=0; k<n; k++){
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
		}
	}
	
	static int min(int a, int b){
		if(a<b) return a;
		else return b;
	}
	
	static int max(int [][]arr){
		int max=0;
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				if(arr[i][j]>max)
					max=arr[i][j];
		return max;
	}
	
	static int findS(int [][]arr){
		int s=0, sum=0;	
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				sum+=arr[i][j];
			}
			if(sum>s)
				s=sum;
			sum=0;
		}
		return s;
	}
	
	static void print(int [][]arr){
		for(int i=0; i<n; i++){
			if(i%10==0) System.out.println();
			for(int j=0; j<n; j++){
				if(j%10==0) System.out.print("   ");
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
}
