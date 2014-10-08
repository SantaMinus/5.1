
public class Main {
	static int INF = Integer.MAX_VALUE/2;
	static int N=1, n; 							// количество кластеров, вершин
	static int[][] matrix1, dist; 				// матрица смежности, достижимости
	
	public static void main(String[] args) {
		int [][]basic = {{0,1,0,1,0},{1,0,1,0,0},{0,1,0,0,1},{1,1,0,0,1},{0,0,1,1,0}};
		int i, j, a=1, b=0;
		System.out.println("N | S | D |         SD         |         T         |   C");
		
		for(int N=1; N<=50; N++){
			n = N*N*5;
			i=0; 
			j=0;
			matrix1 = new int[n][n];
			
			//матрица смежности
			while(i<n-4 && j<n-4){
				for(int k=0; k<5; k++){
					for(int m=0; m<5; m++){
						matrix1[i+k][j+m]=basic[k][m];
					}
				}

				//a-round
				for(int q=0; q<n-5*N+2; q+=5*N){
					matrix1[q+a][q+a+5*(N-1)]=1;
					matrix1[q+a+5*(N-1)][q+a]=1;
				}
				
				//b-round
				for(int q=0; q<5*N; q+=5){
					matrix1[q+2][n-5*N+q]=1;
					matrix1[n-5*N+q][q+2]=1;
				}
				
				if(i+a+5<n && i+b+2+5*N<n){
					matrix1[b][b+2+5*N]=1;
					matrix1[b+2+5*N][b]=1;
					if(i%(5*N)!=5){
						//a-straight
						matrix1[i+a][i+a+5]=1;
						matrix1[i+a+5][i+a]=1;
						//b-straight
						matrix1[i+b][i+b+2+5*N]=1;
						matrix1[i+b+2+5*N][i+b]=1;
					}
				}
				
				i+=5;
				j+=5;
			}
			
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
				
			//print(matrix1);
			System.out.println("_______________________________________________________\n"+N+" | "+S+" | "+D+" | "+SD+" | "+T+" | "+C);
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
		for (int k=0; k<n; k++)
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
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
			for(int j=0; j<n; j++){
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
}
