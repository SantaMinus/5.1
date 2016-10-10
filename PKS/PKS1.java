
public class PKS1 {
	static int INF = Integer.MAX_VALUE/2;
	static int N=1, n; 							// количество кластеров, вершин
	static int[][] matrix1, dist; 				// матрица смежности, достижимости
	
	public static void main(String[] args) {
		int [][]basic = {{0,1,0,0,1,0},{1,0,1,1,0,0},{0,1,0,1,0,1},{0,1,1,0,1,0},{1,0,0,1,0,1},{0,0,1,0,1,0}};
		int i, j, a=1, b=2, c=4;
		System.out.println("N | S | D |         SD         |         T         |   C");
		
		for(int N=1; N<=100; N++){
			n = N*6;
			i=0; 
			j=0;
			matrix1 = new int[n][n];
			
			//матрица смежности
			while(i<n-5 && j<n-5){
				for(int k=0; k<6; k++){
					for(int m=0; m<6; m++){
						matrix1[i+k][j+m]=basic[k][m];
					}
				}
				
				if(i+a+10<n && i+b+4<n && i+c+6<n){
					//a link
					matrix1[i+a][i+a+10]=1;
					matrix1[i+a+10][i+a]=1;
					//b link
					matrix1[i+b][i+b+4]=1;
					matrix1[i+b+4][i+b]=1;
					//c link
					matrix1[i+c][i+c+6]=1;
					matrix1[i+c+6][i+c]=1;
				}
				i+=6;
				j+=6;
			}
			matrix1[0][n-4]=1;
			matrix1[n-4][0]=1;
			matrix1[1][n-1]=1;
			matrix1[n-1][1]=1;
			matrix1[4][n-2]=1;
			matrix1[n-2][4]=1;
			
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
