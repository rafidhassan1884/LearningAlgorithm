//Lazy algorithm using tree

//problems:
//still too expensive :
//1.finding the roots is toilsome. we can end up with a long skinny tree and 
//	have to search to the top for even find operation

//so we started counting the size of the tree to get the smaller to tree
//to stick under the larger trees root.Thus wecan make sure that there are no larger trees.

//ultimately the final nodes of the trees never becomes greater than promised log(2 base)N where N is the number of nodes


public class WeightedUnionFindUF{
	private int[] id;
	private int[] sz;
	public WeightedUnionFindUF(int N){
		id = new int[N];
		sz = new int[N];
		for(int i=0; i < N; i++){
			size[i] = 1;
			id[i] = i;
		}
	}
	private int root(int n){
		while(id[n] != n ){
			id[n] = id[id[n]];
			n = id[n];

		}
		return n;
	}
	public boolean connected(int p, int q){
		return root(p) == root(q)
	}
	public void union(int p, int q){
		int root_p = root(p);
		int root_q = root(q);
		if (root_p == root_q) return;
		if (sz[root_p] > sz[root_q]) {id[root_q] = root_p; sz[root_p] += sz[root_q];}
		else {id[root_p] = root_q; sz[root_q] += sz[root_p];}
	}
}