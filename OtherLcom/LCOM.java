import java.util.*;
import opennlp.tools.tokenize.SimpleTokenizer; 

public class LCOM {
	
	int no_of_methods = 0;
	int no_of_variables = 0;
	
	public LCOM(ArrayList<String> lines) {
		SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
		ArrayList<String> variables = new ArrayList<>();
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		String tokens[];
		
		for(int i = 0;i<lines.size();i++) {
			if(lines.get(i).endsWith("{")) {
				if(lines.get(i).endsWith("){")) {
					int temp_n;
					no_of_methods +=1 ;
					//System.out.println(no_of_methods);
					i += 1;
					String temp = "";
					while(!lines.get(i).endsWith("}")) {
						temp = temp.concat(lines.get(i));
						i += 1;
					}
					
					tokens = simpleTokenizer.tokenize(temp);
					
					ArrayList<Integer> temp_1 = new ArrayList<>();
					for(int j = 0;j<variables.size();j++) {
						
						if(Arrays.asList( tokens ).contains(variables.get(j))) {
							temp_1.add(1);
						}else {
							temp_1.add(0);
						}
					}
					
					graph.add(temp_1);
					
				}else {
					
					tokens = simpleTokenizer.tokenize(lines.get(i));  
				    for(String token : tokens) {
				    	if(token == "class") {
				    		continue;
				    	}  
				    }
				}
			}else if(lines.get(i).endsWith(";")) {
			    tokens = simpleTokenizer.tokenize(lines.get(i));
			    int q = 1;
			    
			    
			    while(q < tokens.length) {
			    	if(tokens[q].equals(",")) {
			    		q += 1;
			    	}else if(tokens[q].equals("=")) {
			    		q += 2;
			    	}else if(tokens[q].equals(";")) {
			    		break;
			    	}else {
			    		variables.add(tokens[q]);
			    		q++;
			    	}
			    	
			    }			    
			    /*
			    for(int j = 0;j<tokens.length;j++) {
			    	
			    	if(!(tokens[j].equals(",") || tokens[j].equals("int") || tokens[j].equals(";"))) {
			    		variables.add(tokens[j]);
			    		var_in_each_m.add(0);
			    	}
			    	
			    }
			    */
			    
			    continue;
			}
			
		}
		System.out.println("Number of Variables= " + variables.size());
		System.out.println("Number of Methods= " + no_of_methods);
				
		int graph_f[][] = new int[graph.size()][graph.get(0).size()];
		
		for(int o = 0;o<graph.size();o++) {
			for(int p = 0;p<graph.get(0).size();p++) {
				graph_f[o][p] = graph.get(o).get(p);
			}
			
		}
		
		/*
		for(int o = 0;o<variables.size();o++) {
			System.out.println(variables.get(o));
		}
		*/
		calculate(graph_f);

	}
	
	public void calculate(int[][] g) {
		int g_t[][] = transpose(g);
		int mult[][] = multiply(g,g_t);
		int score = combination(no_of_methods);
		/*
		for(int i = 0;i<mult.length;i++) {
			for(int j = 0;j<mult[0].length;j++) {
				System.out.print(mult[i][j]);
			}
			System.out.println("");
		}
		*/
		for(int i = 0;i<mult.length;i++) {
			for(int j = i+1;j<mult[0].length;j++) {
				if(mult[i][j] != 0) {
					score -= 1;
				}
			}
		}
		
		System.out.println("LCOM1= " + score);
	}
	
	public int[][] transpose(int mat[][]){
		
		int tra[][] = new int[mat[0].length][mat.length];
		
		for(int i = 0;i<mat[0].length;i++) {
			for(int j = 0;j<mat.length;j++) {
				tra[i][j] = mat[j][i];
			}
		}
		
		return tra;
		
	}
	
	public int[][] multiply(int a[][],int b[][]){
		int c[][] = new int[a.length][b[0].length];
		
		for(int i=0;i<c.length;i++){  
			for(int j=0;j<c[0].length;j++){  
				c[i][j]=0;    
				for(int k=0;k<b.length;k++){    
					c[i][j]+=a[i][k]*b[k][j];    
				}
			}
		}  

		
		return c;
		
	}
	
	public int combination(int n) {
		return (n*(n-1))/2 ;
	}
	
}